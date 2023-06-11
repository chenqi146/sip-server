package com.cqmike.sip.common.exceptions;

import com.cqmike.sip.common.enums.IEnum;
import org.springframework.http.HttpStatus;

/**
 * 业务错误码
 *
 * @author cqmike
 **/
public enum BizErrorCode implements IEnum<String>, ErrorCodeInterface {
    NON_AUTH_ERROR("401", "未授权"),
    BIZ_ERROR("402", "业务异常"),
    PARAMETER_ERROR("403", "参数错误"),
    SIP_BEAN_INIT_ERROR("SIP101", "sip的bean初始化错误"),
    SIP_PARSE_ERROR("SIP102", "sip协议解析异常"),
    SIP_REQ_BUILD_ERROR("SIP103", "sip请求构建异常"),
    SIP_ANY_NOT_EXIST_ERROR("SIP104", "sip请求资源不存在异常"),
    ;

    private final String errorCode;
    private final String message;
    private final Integer httpStatus;

    BizErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = HttpStatus.OK.value();
    }

    BizErrorCode(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus.value();
    }

    /**
     * 获取http状态码
     *
     * @return
     */
    @Override
    public Integer getHttpStatus() {
        return httpStatus;
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

    /**
     * 获取错误码
     *
     * @return
     */
    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
