package com.cqmike.sip.gb28181.event.base;

import gov.nist.javax.sip.ResponseEventExt;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sip.Dialog;
import javax.sip.InvalidArgumentException;
import javax.sip.ResponseEvent;
import javax.sip.SipException;
import javax.sip.address.SipURI;
import javax.sip.header.CSeqHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

/**
 * @author <a href="mailto:cqmike0315@gmail.com">chenqi</a>
 * @version 1.0
 */
@Getter
@Slf4j
public abstract class AbstractResponseEvent {

    protected ResponseEvent responseEvent;

    // 从responseEvent解析出来的
    protected Dialog dialog;
    protected Request reqAck;
    protected String content;
    protected Response response;

    public abstract void process();

    public void handle(ResponseEvent responseEvent) {
        this.responseEvent = responseEvent;
        this.dialog = responseEvent.getDialog();
        this.response = responseEvent.getResponse();
        CSeqHeader cseq = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
        ResponseEventExt event = (ResponseEventExt)responseEvent;

        try {
            this.content = new String(response.getRawContent(), "GBK");
            this.reqAck = dialog.createAck(cseq.getSeqNumber());
            SipURI requestURI = (SipURI) reqAck.getRequestURI();
            requestURI.setHost(event.getRemoteIpAddress());
            requestURI.setPort(event.getRemotePort());
            reqAck.setRequestURI(requestURI);
        } catch (UnsupportedEncodingException | ParseException | InvalidArgumentException | SipException e) {
            // TODO: 2023-04-20 异常处理
        }
    }


    protected boolean isError() {
        return response.getStatusCode() / 100 >= 4;
    }

    protected boolean isProvisional() {
        return response.getStatusCode() / 100 == 1;
    }

    protected boolean isFinal() {
        return response.getStatusCode() >= 200;
    }

    protected boolean isSuccess(){
        return response.getStatusCode()/ 100 == 2;
    }

    protected boolean isRedirect() {
        return response.getStatusCode() / 100 == 3;
    }

    protected boolean isClientError() {
        return response.getStatusCode() / 100 == 4;
    }

    protected boolean isServerError() {
        return response.getStatusCode() / 100 == 5;
    }

    protected boolean isGlobalError() {
        return response.getStatusCode() / 100 == 6;
    }

    protected boolean is100Trying() {
        return response.getStatusCode() == 100;
    }

    protected boolean isRinging() {
        return response.getStatusCode() == 180 || response.getStatusCode() == 183;
    }
}
