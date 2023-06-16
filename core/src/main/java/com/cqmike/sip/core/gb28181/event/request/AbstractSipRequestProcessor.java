package com.cqmike.sip.core.gb28181.event.request;

import com.cqmike.sip.common.enums.IEnum;
import com.cqmike.sip.common.exceptions.SipParseException;
import com.cqmike.sip.core.gb28181.callback.AbstractSipFuture;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.ctx.SipCallbackContext;
import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.SipEventFactory;
import com.cqmike.sip.core.gb28181.event.base.AbstractRequestEvent;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.sip.RequestEvent;
import javax.sip.header.HeaderFactory;
import javax.sip.message.Response;
import java.util.Optional;

/**
 * sip请求处理接口
 *
 * @author cqmike
 **/
public abstract class AbstractSipRequestProcessor<T extends AbstractRequestEvent> implements ISipRequestProcessor {
    protected static final Logger log = LoggerFactory.getLogger(AbstractSipRequestProcessor.class);

    @Resource
    protected SipConfig sipConfig;

    @Resource
    protected ServerTransactionFactory serverTransactionFactory;

    @Resource
    protected SipProtocolFactory sipProtocolFactory;

    @Resource
    protected HeaderFactory headerFactory;

    @Resource
    protected SipCommander sipCommander;

    @Resource
    protected SipCallbackContext callbackContext;

    protected void sendOk(RequestEvent requestEvent) {

        try {
            // 响应Ok
            Response response = sipProtocolFactory.buildResponse(Response.OK, requestEvent.getRequest());
            serverTransactionFactory.sendResponse(requestEvent, response);
        } catch (Exception e) {
            log.error("sip响应ok失败: {} , error: ", requestEvent, e);
        }

    }

    protected void send(RequestEvent requestEvent, int responseStatus) {

        try {
            // 响应Ok
            Response response = sipProtocolFactory.buildResponse(responseStatus, requestEvent.getRequest());
            serverTransactionFactory.sendResponse(requestEvent, response);
        } catch (Exception e) {
            log.error("sip响应{}失败: {} , error: ", responseStatus, requestEvent, e);
        }
    }


    /**
     * 响应web消息的回调
     *
     * @author cqmike
     * @since 1.0.0
     * @return
     */
    protected Optional<AbstractSipFuture> sipFutureOp() {
        return callbackOp().flatMap(callBackEnum -> callbackFieldOp().flatMap(field -> callbackContext.get(callBackEnum, field)));
    }

    @Override
    public void processEvent(RequestEvent event) {
        try {
            T parse = parse(event);
            process(parse);
        } catch (Exception e) {
            // TODO: 2023/6/15 处理异常
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析xml
     * @param requestEvent
     * @return
     */
    protected T parse(RequestEvent requestEvent) throws Exception {
        String method = requestEvent.getRequest().getMethod();
        Optional<SipMethod> form = IEnum.form(SipMethod.class, method);
        SipMethod sipMethod = form.orElseThrow(() -> new SipParseException("此方法暂不支持: " + method));
        return (T) SipEventFactory.getRequestEvent(sipMethod, requestEvent);
    }

    /**
     * 处理解析后的事件数据
     * @param event
     */
    public abstract void process(T event);
}
