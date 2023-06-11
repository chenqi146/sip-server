package com.cqmike.sip.common.exceptions;

import java.util.Map;

/**
 * 参数错误
 *
 * @author cqmike
 **/
public class SipAnyNotExistException extends BaseException {

    private static final long serialVersionUID = 6921826359366065416L;

    public SipAnyNotExistException() {
        super(BizErrorCode.SIP_ANY_NOT_EXIST_ERROR);
    }

    public SipAnyNotExistException(Throwable throwable) {
        super(BizErrorCode.SIP_ANY_NOT_EXIST_ERROR, throwable);
    }

    public SipAnyNotExistException(String message) {
        super(BizErrorCode.SIP_ANY_NOT_EXIST_ERROR, message);
    }

    public SipAnyNotExistException(String message, Throwable throwable) {
        super(BizErrorCode.SIP_ANY_NOT_EXIST_ERROR, message, throwable);
    }

    public SipAnyNotExistException(Map<String, Object> data) {
        super(BizErrorCode.SIP_ANY_NOT_EXIST_ERROR, data);
    }

    public SipAnyNotExistException(Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.SIP_ANY_NOT_EXIST_ERROR, data, throwable);
    }

    public SipAnyNotExistException(String message, Map<String, Object> data) {
        super(BizErrorCode.SIP_ANY_NOT_EXIST_ERROR, message, data);
    }

    public SipAnyNotExistException(String message, Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.SIP_ANY_NOT_EXIST_ERROR, message, data, throwable);
    }

}
