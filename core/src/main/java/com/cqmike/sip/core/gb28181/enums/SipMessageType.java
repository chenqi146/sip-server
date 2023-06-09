package com.cqmike.sip.core.gb28181.enums;

import com.cqmike.sip.common.enums.IEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * SipMethodFunction
 *
 * @author cqmike
 **/
@Getter
public enum SipMessageType implements IEnum<String> {
    QUERY_CATALOG("Query", "Catalog", "查询设备信息");

    private final String type;
    private final String cmdType;
    private final String message;

    SipMessageType(String type, String cmdType, String message) {
        this.type = type;
        this.cmdType = cmdType;
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

    public static Optional<SipMessageType> of(String type, String cmdType) {
        return Arrays.stream(values()).filter(v -> Objects.equals(v.type, type) && Objects.equals(cmdType, v.cmdType)).findFirst();
    }
}
