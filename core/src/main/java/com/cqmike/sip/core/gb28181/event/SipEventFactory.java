package com.cqmike.sip.core.gb28181.event;

import com.cqmike.sip.core.gb28181.annotation.MessageEventHandler;
import com.cqmike.sip.core.gb28181.annotation.ReqEventHandler;
import com.cqmike.sip.core.gb28181.annotation.ResEventHandler;
import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.base.AbstractMessageRequestEvent;
import com.cqmike.sip.core.gb28181.event.base.AbstractRequestEvent;
import com.cqmike.sip.core.gb28181.event.base.AbstractResponseEvent;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根据sip方法不同, 构造不同的事件然后分发
 * 请求事件和响应事件的工厂
 *
 * @author cqmike
 **/
@Slf4j
public class SipEventFactory {

    private static final Map<SipMethod, AbstractRequestEvent> REQUEST_EVENT_MAP = new ConcurrentHashMap<>();
    private static final Map<SipMessageType, AbstractMessageRequestEvent> MESSAGE_REQUEST_EVENT_MAP = new ConcurrentHashMap<>();

    private static final Map<SipMethod, AbstractResponseEvent> RESPONSE_EVENT_MAP = new ConcurrentHashMap<>();

    static {
        Reflections reflections = new Reflections("com.cqmike.sip.core.gb28181.*");
        initMessageRequestEventHandler(reflections);
        initRequestEventHandler(reflections);
        initResponseHandler(reflections);
    }


    private static void initMessageRequestEventHandler(Reflections reflections) {
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MessageEventHandler.class);
        classes.forEach(aClass -> {
            try {
                Object o = aClass.newInstance();
                if (o instanceof AbstractMessageRequestEvent) {
                    MessageEventHandler handler = aClass.getAnnotation(MessageEventHandler.class);
                    MESSAGE_REQUEST_EVENT_MAP.put(handler.type(), (AbstractMessageRequestEvent) o);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("init message request event handler failed");
            }
        });
    }

    private static void initRequestEventHandler(Reflections reflections) {
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ReqEventHandler.class);
        classes.forEach(aClass -> {
            try {
                Object o = aClass.newInstance();
                if (o instanceof AbstractRequestEvent) {
                    ReqEventHandler handler = aClass.getAnnotation(ReqEventHandler.class);
                    REQUEST_EVENT_MAP.put(handler.method(), (AbstractRequestEvent) o);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("init request event handler failed");
            }
        });
    }

    private static void initResponseHandler(Reflections reflections) {
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ResEventHandler.class);
        classes.forEach(aClass -> {
            try {
                Object o = aClass.newInstance();
                if (o instanceof AbstractResponseEvent) {
                    ResEventHandler handler = aClass.getAnnotation(ResEventHandler.class);
                    RESPONSE_EVENT_MAP.put(handler.method(), (AbstractResponseEvent) o);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("init response event handler failed");
            }
        });
    }


    public static AbstractMessageRequestEvent getMessageRequestEvent(SipMessageType method, RequestEvent requestEvent) {
        AbstractMessageRequestEvent event = MESSAGE_REQUEST_EVENT_MAP.get(method);
        if (Objects.isNull(event)) {
            return null;
        }
        try {
            event.init(requestEvent);
        } catch (Exception e) {
            log.error("sip消息请求初始化和解析失败: method: {}, event: {}, error: ", method, requestEvent, e);
            return null;
        }
        return event;
    }

    public static AbstractRequestEvent getRequestEvent(SipMethod method, RequestEvent requestEvent) {
        AbstractRequestEvent event = REQUEST_EVENT_MAP.get(method);
        if (Objects.isNull(event)) {
            return null;
        }
        try {
            event.init(requestEvent);
        } catch (Exception e) {
            log.error("sip请求初始化和解析失败: method: {}, event: {}, error: ", method, requestEvent, e);
            return null;
        }
        return event;
    }


    public static AbstractResponseEvent getResponseEvent(SipMethod method, ResponseEvent responseEvent) {
        AbstractResponseEvent event = RESPONSE_EVENT_MAP.get(method);
        if (Objects.isNull(event)) {
            return null;
        }
        try {
            event.init(responseEvent);
        } catch (Exception e) {
            log.error("sip响应初始化和解析失败: method: {}, event: {}, error: ", method, responseEvent, e);
            return null;
        }
        return event;
    }
}
