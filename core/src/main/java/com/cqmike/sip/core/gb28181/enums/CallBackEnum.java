package com.cqmike.sip.core.gb28181.enums;

import com.cqmike.sip.common.enums.IEnum;
import lombok.Getter;

/**
 * CallBackEnum
 *
 * @author cqmike
 **/
@Getter
public enum CallBackEnum implements IEnum<String> {
    CALLBACK_CATALOG("设备信息回调: key=前缀+设备id, field=xml中的sn"),
    CALLBACK_PLAY("播放: key=前缀+设备id, field=设备通道id"),
    CALLBACK_STOP("停止播放"),
    ;

    private final String message;

    CallBackEnum(String message) {
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
