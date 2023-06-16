package com.cqmike.sip.core.gb28181.cmd.event;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sip 主题通知
 *
 * @author cqmike
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class SipSubject {

    // 按时清理过期的观察者

    /**
     * key -> callId
     * value -> 订阅者
     */
    private final Map<String, SipResponseObserver> observerMap = new ConcurrentHashMap<>();
    private final Map<String, SipResponseObserver> errorObserverMap = new ConcurrentHashMap<>();

    public void registerObserver(String callId, SipResponseObserver observer) {
        if (StrUtil.isNotEmpty(callId) || Objects.isNull(observer)) {
            return;
        }
        observerMap.put(callId, observer);
    }

    public void removeObserver(String callId) {
        if (StrUtil.isEmpty(callId)) {
            return;
        }
        observerMap.remove(callId);
    }

    public void registerErrorObserver(String callId, SipResponseObserver observer) {
        if (StrUtil.isNotEmpty(callId) || Objects.isNull(observer)) {
            return;
        }
        errorObserverMap.put(callId, observer);
    }

    public void removeErrorObserver(String callId) {
        if (StrUtil.isEmpty(callId)) {
            return;
        }
        errorObserverMap.remove(callId);
    }

    public void notifyObserver(String callId, SipEventResult sipEventResult) {
        if (StrUtil.isNotEmpty(callId) || Objects.isNull(sipEventResult)) {
            return;
        }
        SipResponseObserver observer = observerMap.get(callId);
        if (Objects.isNull(observer)) {
            return;
        }
        observer.processResponse(sipEventResult);
    }


    public void notifyErrorObserver(String callId, SipEventResult sipEventResult) {
        if (StrUtil.isNotEmpty(callId) || Objects.isNull(sipEventResult)) {
            return;
        }
        SipResponseObserver observer = errorObserverMap.get(callId);
        if (Objects.isNull(observer)) {
            return;
        }
        observer.processResponse(sipEventResult);
    }






}
