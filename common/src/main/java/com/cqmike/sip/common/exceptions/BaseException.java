package com.cqmike.sip.common.exceptions;


import cn.hutool.core.util.StrUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * BaseException
 *
 * @author chen qi
 **/
public abstract class BaseException extends RuntimeException {
    private static final long serialVersionUID = -4832701923668643399L;

    private final ErrorCodeInterface error;
    /**
     * 自定义数据
     */
    private Map<String, Object> data;

    public BaseException(ErrorCodeInterface error) {
        super(error.getMessage());
        this.error = error;
    }
    public BaseException(ErrorCodeInterface error, Throwable throwable) {
        super(error.getMessage(), throwable);
        this.error = error;
    }

    public BaseException(ErrorCodeInterface error, String message) {
        super(StrUtil.isEmpty(message) ? error.getMessage() : message);
        this.error = error;
    }

    public BaseException(ErrorCodeInterface error, String message, Throwable throwable) {
        super(StrUtil.isEmpty(message) ? error.getMessage() : message, throwable);
        this.error = error;
    }

    public BaseException(ErrorCodeInterface error, Map<String, Object> data) {
        super(error.getMessage());
        this.error = error;
        Optional.ofNullable(data).ifPresent(map -> this.data = data);
    }

    public BaseException(ErrorCodeInterface error, String message, Map<String, Object> data) {
        super(StrUtil.isEmpty(message) ? error.getMessage() : message);
        this.error = error;
        Optional.ofNullable(data).ifPresent(map -> this.data = data);
    }


    public BaseException(ErrorCodeInterface error, String message, Map<String, Object> data, Throwable throwable) {
        super(StrUtil.isEmpty(message) ? error.getMessage() : message, throwable);
        this.error = error;
        Optional.ofNullable(data).ifPresent(map -> this.data = data);
    }

    public BaseException(ErrorCodeInterface error, Map<String, Object> data, Throwable throwable) {
        super(error.getMessage(), throwable);
        this.error = error;
        Optional.ofNullable(data).ifPresent(map -> this.data = data);
    }

    public ErrorCodeInterface getError() {
        return error;
    }

    public Map<String, Object> getData() {
        if (Objects.isNull(data)) {
            return Collections.emptyMap();
        }
        return data;
    }
}
