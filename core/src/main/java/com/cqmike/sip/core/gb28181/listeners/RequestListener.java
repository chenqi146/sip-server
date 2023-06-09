package com.cqmike.sip.core.gb28181.listeners;

import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.event.request.RegisterRequestEvent;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import com.cqmike.sip.core.service.SipDeviceService;
import gov.nist.javax.sip.clientauthutils.DigestServerAuthenticationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * 设备请求监听处理器
 *
 * @author cqmike
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class RequestListener {

    private final SipConfig sipConfig;

    private final ServerTransactionFactory serverTransactionFactory;
    private final SipProtocolFactory sipProtocolFactory;
    private final HeaderFactory headerFactory;

    private final SipDeviceService sipDeviceService;
    private final SipCommander sipCommander;

    @EventListener
    public void registerEvent(RegisterRequestEvent event) {

        try {
            RequestEvent requestEvent = event.getRequestEvent();
            AuthorizationHeader authHeader = event.getAuthHeader();
            Request request = requestEvent.getRequest();
            // 鉴权
            if (Objects.nonNull(authHeader) && event.doAuthenticatePlainTextPassword(sipConfig.getPassword())) {
                Response response = sipProtocolFactory.buildResponse(Response.UNAUTHORIZED, request);
                new DigestServerAuthenticationHelper().generateChallenge(headerFactory, response, sipConfig.getRealm());
                serverTransactionFactory.sendResponse(requestEvent, response);
                return;
            }

            SipDevice device = new SipDevice();
            device.setSipDeviceId(event.getDeviceId());
            device.setHost(event.getHost());
            device.setRemotePort(event.getRemotePort());
            device.setRemoteIp(event.getRemoteIp());
            device.setMediaTransport(event.getTransport());
            device.setCommandTransport(event.getTransport());
            device.setOnline(event.getExpires() > 0);
            device.setPassword(sipConfig.getPassword());
            // 1. 设备存在更新状态. 不存在则新建
            sipDeviceService.registerDevice(device);

            // 2. 响应sip此请求成功
            Response response = sipProtocolFactory.buildResponse(Response.OK, requestEvent.getRequest());
            response.addHeader(headerFactory.createDateHeader(Calendar.getInstance(Locale.ENGLISH)));
            response.addHeader(requestEvent.getRequest().getHeader(ContactHeader.NAME));
            response.addHeader(requestEvent.getRequest().getExpires());
            serverTransactionFactory.sendResponse(requestEvent, response);
            log.info("接收到注册请求 {}", event);
            if (event.getExpires() <= 0) {
                // 注销设备
                device.setOnline(false);
                sipDeviceService.updateOnline(device);
                return;
            }

            sipCommander.catalogQuery(device);
        } catch (Exception e) {
            log.error("设备注册处理异常, event: {}, error: ", event, e);
        }
    }


}
