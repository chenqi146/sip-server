package com.cqmike.sip.common.exceptions;

import java.util.Map;

/**
 * 参数错误
 *
 * @author cqmike
 **/
public class SipBeanInitException extends BaseException {

    private static final long serialVersionUID = 6921826359366065416L;

    public SipBeanInitException() {
        super(BizErrorCode.SIP_BEAN_INIT_ERROR);
    }

    public SipBeanInitException(Throwable throwable) {
        super(BizErrorCode.SIP_BEAN_INIT_ERROR, throwable);
    }

    public SipBeanInitException(String message) {
        super(BizErrorCode.SIP_BEAN_INIT_ERROR, message);
    }

    public SipBeanInitException(Map<String, Object> data) {
        super(BizErrorCode.SIP_BEAN_INIT_ERROR, data);
    }

    public SipBeanInitException(String message, Map<String, Object> data) {
        super(BizErrorCode.SIP_BEAN_INIT_ERROR, message, data);
    }

    public SipBeanInitException(Map<String, Object> data, Throwable throwable) {
        super(BizErrorCode.SIP_BEAN_INIT_ERROR, data, throwable);
    }
}
