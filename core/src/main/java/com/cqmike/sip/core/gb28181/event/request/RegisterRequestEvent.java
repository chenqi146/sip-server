package com.cqmike.sip.core.gb28181.event.request;

import cn.hutool.core.util.StrUtil;
import com.cqmike.sip.core.gb28181.annotation.ReqEventHandler;
import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.base.AbstractRequestEvent;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.clientauthutils.DigestServerAuthenticationHelper;
import gov.nist.javax.sip.header.Expires;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.sip.header.AuthorizationHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * 设备请求注册
 *
 * @author cqmike
 **/
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
@ReqEventHandler(method = SipMethod.REGISTER)
@ToString(callSuper = true)
public class RegisterRequestEvent extends AbstractRequestEvent {

    private AuthorizationHeader authHeader;

    private String deviceId;

    private String host;

    private Integer port;

    private String transport;

    private Integer expires = 0;

    private String remoteIp;

    private Integer remotePort;

    /**
     * 各子类实现
     */
    @Override
    public void parse() {
        Request request = requestEvent.getRequest();
        this.authHeader = (AuthorizationHeader) request.getHeader(AuthorizationHeader.NAME);
        FromHeader fromHeader = (FromHeader) request.getHeader(FromHeader.NAME);
        ViaHeader viaHeader = (ViaHeader) request.getHeader(ViaHeader.NAME);
        SipUri uri = (SipUri) fromHeader.getAddress().getURI();
        this.deviceId = uri.getUser();
        this.host = viaHeader.getHost();
        this.port = viaHeader.getPort();
        this.transport = viaHeader.getTransport();
        String received = viaHeader.getReceived();
        int rPort = viaHeader.getRPort();
        this.remotePort = rPort;
        this.remoteIp = received;
        if (StrUtil.isNotEmpty(received) || rPort == -1) {
            this.remotePort = viaHeader.getPort();
            this.remoteIp = viaHeader.getHost();
        }

        ExpiresHeader expiresHeader = (ExpiresHeader) request.getHeader(Expires.NAME);
        this.expires = Optional.of(expiresHeader.getExpires()).orElse(1);
    }

    public boolean doAuthenticatePlainTextPassword(String password)   {
        Request request = requestEvent.getRequest();
        try {
            return new DigestServerAuthenticationHelper().doAuthenticatePlainTextPassword(request, password);
        } catch (NoSuchAlgorithmException e) {
            log.error("设备({}-{})校验密码({})异常. ", host, deviceId, password, e);
            return false;
        }
    }
}
