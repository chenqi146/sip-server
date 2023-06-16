package com.cqmike.sip.core.gb28181.event.request.message.notify;

import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.event.request.message.AbstractMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.message.Response;
import java.util.Optional;

/**
 * TODO
 *
 * @author cqmike
 **/
@Component
@RequiredArgsConstructor
public class KeepLiveMessageProcessor extends AbstractMessageHandler<KeepLiveMessageEvent> {
    /**
     * message中的类型
     *
     * @return
     */
    @Override
    public SipMessageType type() {
        return SipMessageType.NOTIFY_KEEPALIVE;
    }

    /**
     * 处理设备信息
     *
     * @param event
     * @param sipDevice
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    protected void handleDeviceMessage(KeepLiveMessageEvent event, SipDevice sipDevice) {
        RequestEvent requestEvent = event.getRequestEvent();
        String deviceId = event.getDeviceId();
        Optional<SipDevice> optional = sipDeviceService.findBySipDeviceId(deviceId);
        if (optional.isPresent()) {
            sendOk(requestEvent);
            sipDeviceService.updateKeepLive(deviceId);
            return;
        }
        send(requestEvent, Response.DOES_NOT_EXIST_ANYWHERE);
    }
}
