package com.cqmike.sip.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.cqmike.sip.common.exceptions.SipAnyNotExistException;
import com.cqmike.sip.core.entity.DeviceChannel;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.dto.InviteStreamDTO;
import com.cqmike.sip.core.service.SipDeviceService;
import com.cqmike.sip.core.service.StreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void inviteStream(String sipDeviceId, Integer channel) {
        // TODO: 2023/6/11 异步保存事件
        SipDevice device = sipDeviceService.findBySipDeviceId(sipDeviceId).orElseThrow(SipAnyNotExistException::new);
        List<DeviceChannel> deviceChannels = device.getDeviceChannels();
        if (CollUtil.isEmpty(deviceChannels)) {
            // TODO: 2023/6/11 无通道 抛异常
            return;
        }
        int index = (channel - 1) % deviceChannels.size();
        DeviceChannel deviceChannel = deviceChannels.get(index);

        String numbers = RandomUtil.randomNumbers(10);
        InviteStreamDTO build = InviteStreamDTO.builder()
                .sipDeviceChannelId(deviceChannel.getChannelId())
                .sipDevice(device)
                .ssrc(numbers).build();
        sipCommander.inviteStream(build);
    }
}
