package com.cqmike.sip.core.gb28181.event;

import com.cqmike.sip.core.gb28181.enums.CallBackEnum;
import com.cqmike.sip.core.gb28181.enums.SipMethod;

import java.util.Optional;

/**
 * sip处理器接口
 *
 * @author cqmike
 **/
public interface ISipProcessor {

    /**
     * 表明sip方法
     *
     * @author cqmike
     * @since 1.0.0
     * @return
     */
    SipMethod method();

    /**
     * 回调类型
     *
     * @author cqmike
     * @since 1.0.0
     * @return
     */
    default Optional<CallBackEnum> callbackOp() {
        return Optional.empty();
    }

    /**
     * 回调的field, 各类型不一样
     *
     * @author cqmike
     * @since 1.0.0
     * @return
     */
    default Optional<String> callbackFieldOp() {
        return Optional.empty();
    }
}
