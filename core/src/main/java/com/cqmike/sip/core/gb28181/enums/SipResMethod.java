package com.cqmike.sip.core.gb28181.enums;

import com.cqmike.sip.common.enums.IEnum;


/**
 * SipMethod
 *
 * @author cqmike
 **/
public enum SipResMethod implements IEnum<String> {
    REGISTER("注册"),
    MESSAGE("消息"),
    ACK("ACK"),
    BYE("BYE"),
    CANCEL("取消"),
    INVITE("邀请"),
    OPTIONS("OPTIONS"),
    NOTIFY("通知"),
    SUBSCRIBE("订阅"),
    REFER("REFER"),
    INFO("INFO"),
    PRACK("PRACK"),
    UPDATE("UPDATE"),
    PUBLISH("发布"),
    ;


    private final String message;

    SipResMethod(String message) {
        this.message = message;
    }

    /**
     * 获取枚举标识, 用于controller转换
     *
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public String getCode() {
        return name();
    }

    /**
     * 枚举描述
     *
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public String getMessage() {
        return message;
    }
}
