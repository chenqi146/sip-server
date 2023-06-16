package com.cqmike.sip.core.gb28181.event.request.message;

import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.event.base.AbstractMessageRequestEvent;

/**
 * message消息处理, 处理设备的和上级平台的
 *
 * @author cqmike
 **/
public interface IMessageHandler<T extends AbstractMessageRequestEvent> {

    /**
     * message中的类型
     * @return
     */
    SipMessageType type();

    /**
     * 处理设备或平台的消息, 在抽象类中区分两者
     *
     * @author cqmike
     * @param event
     * @since 1.0.0
     * @return
     */
    void handle(T event);
}
