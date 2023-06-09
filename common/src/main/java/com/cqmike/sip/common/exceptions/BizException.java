package com.cqmike.sip.common.exceptions;

import java.util.Map;

/**
 * 参数错误
 *
 * @author cqmike
 **/
public class BizException extends BaseException {

    private static final long serialVersionUID = 6921826359366065416L;

    public BizException() {
        super(BizErrorCode.BIZ_ERROR);
    }

    public BizException(Throwable throwable) {
        super(BizErrorCode.BIZ_ERROR, throwable);
    }

    public BizException(String message) {
        super(BizErrorCode.BIZ_ERROR, message);
    }

    public BizException(String message, Throwable throwable) {
        super(BizErrorCode.BIZ_ERROR, message, throwable);
    }

    public BizException(Map<String, Object> data) {
        super(BizErrorCode.BIZ_ERROR, data);
    }

    public BizException(Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.BIZ_ERROR, data, throwable);
    }

    public BizException(String message, Map<String, Object> data) {
        super(BizErrorCode.BIZ_ERROR, message, data);
    }

    public BizException(String message, Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.BIZ_ERROR, message, data, throwable);
    }

}
