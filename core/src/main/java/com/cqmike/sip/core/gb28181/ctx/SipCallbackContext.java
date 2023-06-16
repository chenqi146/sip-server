package com.cqmike.sip.core.gb28181.ctx;

import com.cqmike.sip.core.gb28181.callback.AbstractSipFuture;
import com.cqmike.sip.core.gb28181.enums.CallBackEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sip异步回调管理¬
 *
 * @author cqmike
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class SipCallbackContext {


    private static final Map<CallBackEnum, Map<String, AbstractSipFuture<?>>> MAP = new ConcurrentHashMap<>();

    /**
     * 增加回调事件
     *
     * @param future
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public AbstractSipFuture add(AbstractSipFuture<?> future) {
        return addAndDo(future, null);
    }

    public AbstractSipFuture addAndDo(AbstractSipFuture<?> future, Runnable runnable) {
        Map<String, AbstractSipFuture<?>> futureMap = MAP.get(future.getKey());
        if (futureMap == null) {
            futureMap = new ConcurrentHashMap<>();
            MAP.put(future.getKey(), futureMap);
        }
        futureMap.put(future.getField(), future);
        if (Objects.nonNull(runnable)) {
            try {
                runnable.run();
            } catch (Exception e) {
                remove(future.getKey(), future.getField());
                throw e;
            }
        }
        return future;
    }

    private void remove(CallBackEnum key, String field) {
        Map<String, AbstractSipFuture<?>> futureMap = MAP.get(key);
        futureMap.remove(field);
        if (futureMap.size() == 0) {
            MAP.remove(key);
        }
    }

    /**
     * 获取回调
     *
     * @param key   回调前缀+设备id
     * @param field 各回调取值不一样
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public Optional<AbstractSipFuture> get(CallBackEnum key, String field) {
        return Optional.ofNullable(MAP.get(key)).map(m -> m.get(field));
    }

    /**
     * 完成回调
     *
     * @param key   回调前缀+设备id
     * @param field 各回调取值不一样
     * @param value
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public void complete(CallBackEnum key, String field, Object value) {
        get(key, field).ifPresent(future -> {
            future.complete(value);
            remove(key, field);
        });
    }

}
