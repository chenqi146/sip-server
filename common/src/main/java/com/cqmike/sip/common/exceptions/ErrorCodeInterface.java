package com.cqmike.sip.common.exceptions;


/**
 * ErrorCodeInterface
 *
 * @author chen qi
 **/
public interface ErrorCodeInterface {

    /**
     * 获取错误码信息
     * @return
     */
    String getMessage();

    /**
     *  获取错误码
     * @return
     */
    String getErrorCode();

    /**
     * 获取http状态码
     * @return
     */
    default Integer getHttpStatus() {
        return 200;
    }

}
