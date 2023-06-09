package com.cqmike.sip.core.gb28181.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sip.*;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.util.Objects;

/**
 * ServerTransactionFactory
 *
 * @author cqmike
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerTransactionFactory {

    private final SipProvider sipUdpProvider;
    private final SipProvider sipTcpProvider;

    private boolean isTCP(Request request) {
        return isTCP((ViaHeader) request.getHeader(ViaHeader.NAME));
    }

    private boolean isTCP(Response response) {
        return isTCP((ViaHeader) response.getHeader(ViaHeader.NAME));
    }

    private boolean isTCP(ViaHeader viaHeader) {
        String protocol = viaHeader.getProtocol();
        return protocol.equals("TCP");
    }

    public ServerTransaction getServerTransaction(Request request) throws SipException {
        return (isTCP(request) ? sipTcpProvider : sipUdpProvider).getNewServerTransaction(request);
    }

    public ClientTransaction getClientTransaction(Request request) throws SipException {
        return (isTCP(request) ? sipTcpProvider : sipUdpProvider).getNewClientTransaction(request);
    }

    public ServerTransaction getServerTransaction(RequestEvent request) throws SipException {
        ServerTransaction serverTransaction = request.getServerTransaction();
        if (Objects.nonNull(serverTransaction)) {
            return serverTransaction;
        }
        return (isTCP(request.getRequest()) ? sipTcpProvider : sipUdpProvider).getNewServerTransaction(request.getRequest());
    }


    public void sendResponse(RequestEvent requestEvent, Response response) {
        String reqEventStr = requestEvent.toString();
        try {
            ServerTransaction serverTransaction = getServerTransaction(requestEvent);
            serverTransaction.sendResponse(response);
            log.info("发送响应消息 >>> {}", response);
        } catch (Exception e) {
            log.error("发送响应信息失败: reqEventStr: {}, response: {}, error: ", reqEventStr, response.toString(), e);
        }
    }

    public void sendRequest(Request request) {
        try {
            ClientTransaction clientTransaction = (isTCP(request) ? sipTcpProvider : sipUdpProvider).getNewClientTransaction(request);
            clientTransaction.sendRequest();
            log.info("发送请求消息 >>> {}", request);
        } catch (Exception e) {
            log.error("发送响应信息失败: reqStr: {}, error: ", request, e);
        }
    }


}
