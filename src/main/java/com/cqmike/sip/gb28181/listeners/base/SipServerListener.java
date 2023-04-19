package com.cqmike.sip.gb28181.listeners.base;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.sip.*;
import javax.sip.header.CSeqHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.io.UnsupportedEncodingException;

/**
 * @author <a href="mailto:cqmike0315@gmail.com">chenqi</a>
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SipServerListener implements SipListener {

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void processRequest(RequestEvent requestEvent) {
        Request request = requestEvent.getRequest();
        log.info("RequestEvent {}",request.toString());
        // TODO: 2023-04-20 根据类型来发布事件
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        Response response = responseEvent.getResponse();
        try {
            log.info("ResponseEvent {}",response.toString() );
            log.info("ResponseContent {}",new String(response.getRawContent(),"gbk") );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int status = response.getStatusCode();
        if (status >= HttpStatus.OK.value() && status <= HttpStatus.MULTIPLE_CHOICES.value()) {
            CSeqHeader cseqHeader = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
            String method = cseqHeader.getMethod();
            // TODO: 2023-04-20 根据类型来发布事件
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
