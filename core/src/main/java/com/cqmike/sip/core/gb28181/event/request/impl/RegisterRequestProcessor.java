package com.cqmike.sip.core.gb28181.event.request.impl;

import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.request.AbstractSipRequestProcessor;
import com.cqmike.sip.core.service.SipDeviceService;
import gov.nist.javax.sip.clientauthutils.DigestServerAuthenticationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.ContactHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * 注册处理器
 *
 * @author cqmike
 **/
@Component
@RequiredArgsConstructor
public class RegisterRequestProcessor extends AbstractSipRequestProcessor<RegisterRequestEvent> {

    private final SipDeviceService sipDeviceService;

    @Override
    public SipMethod method() {
        return SipMethod.REGISTER;
    }

    @Override
    public void process(RegisterRequestEvent event) {

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
            device.setIp(event.getHost());
            device.setPort(event.getPort());
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
            if (event.getExpires() <= 0) {
                // 注销设备
                device.setOnline(false);
                sipDeviceService.updateOnline(device);
                return;
            }

            // 下发查询catalog
            sipCommander.catalogQuery(device);
        } catch (Exception e) {
            log.error("设备注册处理异常, event: {}, error: ", event, e);
        }
    }

}
