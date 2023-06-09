package com.cqmike.sip.common.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 枚举接口
 *
 * @author chen qi
 **/
public interface IEnum<T> {

    /**
     * 获取枚举标识, 用于controller转换
     *
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    T getCode();

    /**
     * 枚举描述
     *
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    String getMessage();

    /**
     * 枚举转换方法
     *
     * @author cqmike
     * @param clazz
     * @param code
     * @since 1.0.0
     * @return
     */
    static <T extends IEnum<?>> Optional<T> form(Class<T> clazz, Object code) {
        if (Objects.isNull(code)) {
            return Optional.empty();
        }
        T[] elements = clazz.getEnumConstants();
        return Arrays.stream(elements)
                .filter(e -> Objects.equals(e.getCode(), code))
                .findFirst();
    }


}
