package com.cqmike.sip.core.gb28181.event.response.impl;

import cn.hutool.core.util.StrUtil;
import com.cqmike.sip.core.entity.DeviceStream;
import com.cqmike.sip.core.gb28181.callback.AbstractSipFuture;
import com.cqmike.sip.core.gb28181.callback.InviteStreamFuture;
import com.cqmike.sip.core.gb28181.enums.CallBackEnum;
import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.response.AbstractSipResponseProcessor;
import com.cqmike.sip.core.service.SipDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sip.ResponseEvent;
import java.util.Objects;

/**
 * 邀请推流响应
 *
 * @author cqmike
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class InviteResponseEventProcessor extends AbstractSipResponseProcessor<InviteResponseEvent> {

    private final SipDeviceService sipDeviceService;

    @Override
    public SipMethod method() {
        return SipMethod.INVITE;
    }

    /**
     * 处理解析后的事件数据
     *
     * @param event
     */
    @Override
    public void process(InviteResponseEvent event) {
        if (!event.isSuccess()) {
            return;
        }
        ResponseEvent responseEvent = event.getResponseEvent();
        log.info("邀请推流响应: {}", responseEvent.getResponse());
        String deviceId = event.getDeviceId();
        String channelId = event.getChannelId();
        // 响应web消息
        sipFutureOp().ifPresent(future -> {
            InviteStreamFuture inviteStreamFuture = (InviteStreamFuture) future;
            String streamCode = event.getStreamCode();
            DeviceStream deviceStream = DeviceStream.builder()
                    .sipDeviceId(deviceId)
                    .channelId(channelId)
                    .streamCode(streamCode).build();
            inviteStreamFuture.complete(deviceStream);
        });
        if (Objects.isNull(responseEvent.getClientTransaction())) {
            return;
        }
        // 需要存储sip信息用于sendBye
        // TODO: 2023/6/15 后续不在内存中保存状态, 自己构建, 通过构建protocol来处理
        sipDeviceService.saveStreamResponse(deviceId, channelId, event);
    }

}
