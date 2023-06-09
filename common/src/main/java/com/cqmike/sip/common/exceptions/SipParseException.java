package com.cqmike.sip.common.exceptions;

import java.util.Map;

/**
 * 参数错误
 *
 * @author cqmike
 **/
public class SipParseException extends BaseException {

    private static final long serialVersionUID = 6921826359366065416L;

    public SipParseException() {
        super(BizErrorCode.SIP_PARSE_ERROR);
    }

    public SipParseException(Throwable throwable) {
        super(BizErrorCode.SIP_PARSE_ERROR, throwable);
    }

    public SipParseException(String message) {
        super(BizErrorCode.SIP_PARSE_ERROR, message);
    }

    public SipParseException(String message, Throwable throwable) {
        super(BizErrorCode.SIP_PARSE_ERROR, message, throwable);
    }

    public SipParseException(Map<String, Object> data) {
        super(BizErrorCode.SIP_PARSE_ERROR, data);
    }

    public SipParseException(Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.SIP_PARSE_ERROR, data, throwable);
    }

    public SipParseException(String message, Map<String, Object> data) {
        super(BizErrorCode.SIP_PARSE_ERROR, message, data);
    }

    public SipParseException(String message, Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.SIP_PARSE_ERROR, message, data, throwable);
    }

}
