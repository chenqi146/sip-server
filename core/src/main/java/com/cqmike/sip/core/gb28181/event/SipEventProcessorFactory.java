package com.cqmike.sip.core.gb28181.event;

import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.request.ISipRequestProcessor;
import com.cqmike.sip.core.gb28181.event.response.ISipResponseProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sip事件处理器工厂
 *
 * @author cqmike
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class SipEventProcessorFactory implements InitializingBean {

    private final static Map<SipMethod, ISipRequestProcessor> requestProcessorMap = new ConcurrentHashMap<>();
    private final static Map<SipMethod, ISipResponseProcessor> responseProcessorMap = new ConcurrentHashMap<>();

    private final ApplicationContext applicationContext;

    /**
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties
     * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an
     *                   essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 请求处理器初始化
        Map<String, ISipRequestProcessor> reqProcessorMap = applicationContext.getBeansOfType(ISipRequestProcessor.class);
        reqProcessorMap.forEach((k, v) -> requestProcessorMap.put(v.method(), v));

        // 响应处理器初始化
        Map<String, ISipResponseProcessor> resProcessorMap = applicationContext.getBeansOfType(ISipResponseProcessor.class);
        resProcessorMap.forEach((k, v) -> responseProcessorMap.put(v.method(), v));
    }
}
