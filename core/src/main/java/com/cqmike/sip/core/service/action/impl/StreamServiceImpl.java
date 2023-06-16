package com.cqmike.sip.core.service.action.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.cqmike.sip.common.exceptions.SipAnyNotExistException;
import com.cqmike.sip.core.entity.DeviceChannel;
import com.cqmike.sip.core.entity.DeviceStream;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.callback.InviteStreamFuture;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.ctx.SipCallbackContext;
import com.cqmike.sip.core.gb28181.cmd.dto.InviteStreamCmd;
import com.cqmike.sip.core.gb28181.enums.CallBackEnum;
import com.cqmike.sip.core.gb28181.event.response.impl.InviteResponseEvent;
import com.cqmike.sip.core.service.SipDeviceService;
import com.cqmike.sip.core.service.action.StreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * StreamServiceImpl
 *
 * @author cqmike
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class StreamServiceImpl implements StreamService {


    private final SipCommander sipCommander;
    private final SipDeviceService sipDeviceService;
    private final SipCallbackContext callbackContext;

    /**
     * 邀请推流
     *
     * @param sipDeviceId
     * @param channel
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public CompletableFuture<DeviceStream> inviteStream(String sipDeviceId, String channel) {
        SipDevice device = sipDeviceService.findBySipDeviceId(sipDeviceId).orElseThrow(SipAnyNotExistException::new);
        List<DeviceChannel> deviceChannels = device.getDeviceChannels();
        if (CollUtil.isEmpty(deviceChannels)) {
            // TODO: 2023/6/11 无通道 抛异常
            return null;
        }
        Optional<DeviceChannel> channelOptional = deviceChannels.stream().filter(dc -> Objects.equals(dc.getChannelId(), channel)).findFirst();
        if (!channelOptional.isPresent()) {
            return null;
        }

        DeviceChannel deviceChannel = channelOptional.get();
        // todo ssrc的生成管理, 需要在当前域下不重复
        String numbers = RandomUtil.randomNumbers(10);
        InviteStreamCmd build = InviteStreamCmd.builder()
                .sipDeviceChannelId(deviceChannel.getChannelId())
                .sipDevice(device)
                .ssrc(numbers).build();

        //  2023/6/11 异步保存事件
        InviteStreamFuture future = new InviteStreamFuture();
        future.setKey(CallBackEnum.CALLBACK_PLAY);
        future.setField(sipDeviceId + StrUtil.AT + deviceChannel.getChannelId());
        return callbackContext.addAndDo(future, () -> sipCommander.inviteStream(build));
    }

    @Override
    public void byeStream(String sipDeviceId, String channel) {
        Optional<InviteResponseEvent> optional = sipDeviceService.getStreamResponse(sipDeviceId, channel);
        optional.ifPresent(sipCommander::byeStream);
    }
}
