package com.cqmike.sip.core.gb28181.annotation;

import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.enums.SipMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * MessageEventHandler
 *
 * @author cqmike
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageEventHandler {
    /**
     * 类型
     *
     * @author cqmike
     * @since 1.0.0
     * @return
     */
    SipMessageType type();

}
