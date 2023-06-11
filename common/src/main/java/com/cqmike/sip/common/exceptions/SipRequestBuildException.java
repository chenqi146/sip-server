package com.cqmike.sip.common.exceptions;

import java.util.Map;

/**
 * sip请求构建错误
 *
 * @author cqmike
 **/
public class SipRequestBuildException extends BaseException {

    private static final long serialVersionUID = 6921826359366065416L;

    public SipRequestBuildException() {
        super(BizErrorCode.SIP_REQ_BUILD_ERROR);
    }

    public SipRequestBuildException(Throwable throwable) {
        super(BizErrorCode.SIP_REQ_BUILD_ERROR, throwable);
    }

    public SipRequestBuildException(String message) {
        super(BizErrorCode.SIP_REQ_BUILD_ERROR, message);
    }

    public SipRequestBuildException(String message, Throwable throwable) {
        super(BizErrorCode.SIP_REQ_BUILD_ERROR, message, throwable);
    }

    public SipRequestBuildException(Map<String, Object> data) {
        super(BizErrorCode.SIP_REQ_BUILD_ERROR, data);
    }

    public SipRequestBuildException(Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.SIP_REQ_BUILD_ERROR, data, throwable);
    }

    public SipRequestBuildException(String message, Map<String, Object> data) {
        super(BizErrorCode.SIP_REQ_BUILD_ERROR, message, data);
    }

    public SipRequestBuildException(String message, Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.SIP_REQ_BUILD_ERROR, message, data, throwable);
    }

}
