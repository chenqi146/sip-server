package com.cqmike.sip.core.gb28181.cmd.event;

import com.cqmike.sip.common.enums.IEnum;

/**
 * sip响应类型
 *
 * @author cqmike
 **/
public enum EventResultType implements IEnum<String> {
    timeout("超时"),
    response("回复"),
    transactionTerminated("事务已结束"),
    dialogTerminated("会话已结束"),
    deviceNotFoundEvent("设备未找到"),
    cmdSendFailEvent("设备未找到");

    private final String message;

    EventResultType(String message) {
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
