package com.cqmike.sip.core.gb28181.listeners.base;

import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sip.RequestEvent;
import javax.sip.header.HeaderFactory;
import javax.sip.message.Response;

/**
 * AbstractSipListener
 *
 * @author cqmike
 **/
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractSipListener {

    protected final SipConfig sipConfig;

    protected final ServerTransactionFactory serverTransactionFactory;
    protected final SipProtocolFactory sipProtocolFactory;

    protected final HeaderFactory headerFactory;

    protected final SipCommander sipCommander;

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
}
