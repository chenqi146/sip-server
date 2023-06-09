package com.cqmike.sip.core.gb28181.listeners.base;

import com.alibaba.fastjson.JSON;
import com.cqmike.sip.common.enums.IEnum;
import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.enums.SipResMethod;
import com.cqmike.sip.core.gb28181.event.base.AbstractMessageRequestEvent;
import com.cqmike.sip.core.gb28181.event.base.AbstractRequestEvent;
import com.cqmike.sip.core.gb28181.event.base.AbstractResponseEvent;
import com.cqmike.sip.core.gb28181.event.message.MessageDispatchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.sip.*;
import javax.sip.header.CSeqHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Optional;

/**
 * 接收sip信息然后转内部事件分发处理
 *
 * @author <a href="mailto:cqmike0315@gmail.com">chenqi</a>
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SipServerDispatcher implements SipListener {

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void processRequest(RequestEvent requestEvent) {
        try {
            Request request = requestEvent.getRequest();
            log.info("收到sip请求消息: {}", request.toString());
            // 从sip端口接收到sip客户端请求信息, 然后根据类型去获取继承抽象子类事件发布
            // MESSAGE的请求全部发布到MessageEvent事件处理
            String method = request.getMethod();
            Optional<SipMethod> form = IEnum.form(SipMethod.class, method);
            form.ifPresent(m -> {
                AbstractRequestEvent abstractRequestEvent = MessageEventFactory.getRequestEvent(m, requestEvent);
                if (Objects.nonNull(abstractRequestEvent)) {
                    applicationEventPublisher.publishEvent(abstractRequestEvent);
                }
            });
        } catch (Exception e) {
            log.error("收到sip请求消息处理异常: {}, error: ", requestEvent, e);
        }
    }

    /**
     * 消息监听, 统一在processRequest解包了一次
     * processRequest --> processMessage
     *
     * @param messageDispatchEvent message类型分发事件
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @EventListener
    public void processMessage(MessageDispatchEvent messageDispatchEvent) {
        try {
            log.info("收到sip请求message消息: {}", messageDispatchEvent);
            SipMessageType sipMessageType = messageDispatchEvent.getSipMessageType();
            AbstractMessageRequestEvent event = MessageEventFactory.getMessageRequestEvent(sipMessageType, messageDispatchEvent.getRequestEvent());
            if (Objects.isNull(event)) {
                return;
            }
            applicationEventPublisher.publishEvent(event);
        } catch (Exception e) {
            log.error("收到sip请求message消息: {}, error: ", messageDispatchEvent, e);
        }
    }


    @Override
    public void processResponse(ResponseEvent responseEvent) {
        Response response = responseEvent.getResponse();
        try {
            log.info("收到sip响应消息: {}", response.toString());
            int status = response.getStatusCode();
            if (status >= HttpStatus.OK.value() && status <= HttpStatus.MULTIPLE_CHOICES.value()) {
                CSeqHeader cseqHeader = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
                String method = cseqHeader.getMethod();
                // 从sip端口接收到sip客户端响应信息, 然后根据类型去获取继承抽象子类事件发布
                Optional<SipResMethod> form = IEnum.form(SipResMethod.class, method);
                form.ifPresent(m -> {
                    AbstractResponseEvent abstractResponseEvent = MessageEventFactory.getResponseEvent(m, responseEvent);
                    if (Objects.isNull(abstractResponseEvent)) {
                        return;
                    }
                    applicationEventPublisher.publishEvent(abstractResponseEvent);
                });
            }
        } catch (Exception e) {
            log.error("收到sip响应时处理异常: {}, error: ", responseEvent, e);
        }
    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {
        log.error(timeoutEvent.toString());
    }

    @Override
    public void processIOException(IOExceptionEvent exceptionEvent) {
        log.error(exceptionEvent.toString());
    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
        log.error(JSON.toJSONString(transactionTerminatedEvent));
    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
        log.error(dialogTerminatedEvent.toString());
    }
}
