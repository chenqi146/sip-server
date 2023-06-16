package com.cqmike.sip.core.gb28181.dispatcher;

import com.alibaba.fastjson.JSON;
import com.cqmike.sip.core.gb28181.event.request.message.impl.MessageDispatchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sip.*;

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


    /**
     * 接收sip下级消息
     *
     * @author cqmike
     * @param requestEvent
     * @since 1.0.0
     * @return
     */
    @Override
    public void processRequest(RequestEvent requestEvent) {
//        try {
//            Request request = requestEvent.getRequest();
//            log.info("收到sip请求消息: {}", request.toString());
//            // 从sip端口接收到sip客户端请求信息, 然后根据类型去获取继承抽象子类事件发布
//            // MESSAGE的请求全部发布到MessageEvent事件处理
//            String method = request.getMethod();
//            Optional<SipMethod> form = IEnum.form(SipMethod.class, method);
//            form.ifPresent(m -> {
//                AbstractRequestEvent abstractRequestEvent = SipEventFactory.getRequestEvent(m, requestEvent);
//                if (Objects.nonNull(abstractRequestEvent)) {
//                    applicationEventPublisher.publishEvent(abstractRequestEvent);
//                }
//            });
//        } catch (Exception e) {
//            log.error("收到sip请求消息处理异常: {}, error: ", requestEvent, e);
//        }
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
//        try {
//            RequestEvent requestEvent = messageDispatchEvent.getRequestEvent();
//            SipMessageType sipMessageType = messageDispatchEvent.getSipMessageType();
//            AbstractMessageRequestEvent event = SipEventFactory.getMessageRequestEvent(sipMessageType, requestEvent);
//            if (Objects.isNull(event)) {
//                return;
//            }
//            applicationEventPublisher.publishEvent(event);
//        } catch (Exception e) {
//            log.error("收到sip请求message消息: {}, error: ", messageDispatchEvent, e);
//        }
    }


    @Override
    public void processResponse(ResponseEvent responseEvent) {
        // TODO: 2023/6/15 增加callId回调处理
//        Response response = responseEvent.getResponse();
//        try {
//            log.info("收到sip响应消息: {}", response.toString());
//            int status = response.getStatusCode();
//            if (status >= HttpStatus.OK.value() && status <= HttpStatus.MULTIPLE_CHOICES.value()) {
//                CSeqHeader cseqHeader = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
//                String method = cseqHeader.getMethod();
//                // 从sip端口接收到sip客户端响应信息, 然后根据类型去获取继承抽象子类事件发布
//                Optional<SipResMethod> form = IEnum.form(SipResMethod.class, method);
//                form.ifPresent(m -> {
//                    AbstractResponseEvent abstractResponseEvent = SipEventFactory.getResponseEvent(m, responseEvent);
//                    if (Objects.isNull(abstractResponseEvent)) {
//                        return;
//                    }
//                    applicationEventPublisher.publishEvent(abstractResponseEvent);
//                });
//            }
//        } catch (Exception e) {
//            log.error("收到sip响应时处理异常: {}, error: ", responseEvent, e);
//        }
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
