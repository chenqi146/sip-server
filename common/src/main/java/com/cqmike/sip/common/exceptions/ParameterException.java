package com.cqmike.sip.common.exceptions;

import java.util.Map;

/**
 * 参数错误
 *
 * @author cqmike
 **/
public class ParameterException extends BaseException {
    private static final long serialVersionUID = -136756142313939176L;

    public ParameterException() {
        super(BizErrorCode.PARAMETER_ERROR);
    }


    public ParameterException(String message) {
        super(BizErrorCode.PARAMETER_ERROR, message);
    }

    public ParameterException(Map<String, Object> data) {
        super(BizErrorCode.PARAMETER_ERROR, data);
    }

    public ParameterException(String message, Map<String, Object> data) {
        super(BizErrorCode.PARAMETER_ERROR, message, data);
    }

    public ParameterException(Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.PARAMETER_ERROR, data, throwable);
    }
}
