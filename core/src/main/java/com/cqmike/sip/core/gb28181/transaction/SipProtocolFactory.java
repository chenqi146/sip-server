package com.cqmike.sip.core.gb28181.transaction;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.cqmike.sip.common.exceptions.SipParseException;
import com.cqmike.sip.common.exceptions.SipRequestBuildException;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sip.InvalidArgumentException;
import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.SipProvider;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.header.*;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static javax.sip.ListeningPoint.TCP;

/**
 * SipProtocolFactory
 *
 * @author cqmike
 **/
@Slf4j
@Component
public class SipProtocolFactory {

    private final SipFactory sipFactory;
    private final SipConfig sipConfig;
    private final SipProvider sipUdpProvider;
    private final SipProvider sipTcpProvider;

    private AddressFactory addressFactory;
    private HeaderFactory headerFactory;
    private MessageFactory messageFactory;

    public SipProtocolFactory(SipFactory sipFactory, SipConfig sipConfig, SipProvider sipUdpProvider, SipProvider sipTcpProvider) throws PeerUnavailableException {
        this.sipFactory = sipFactory;
        this.sipConfig = sipConfig;
        this.sipUdpProvider = sipUdpProvider;
        this.sipTcpProvider = sipTcpProvider;
        this.addressFactory = sipFactory.createAddressFactory();
        this.headerFactory = sipFactory.createHeaderFactory();
        this.messageFactory = sipFactory.createMessageFactory();
    }

    public String buildRealSsrc() {
        String real = "0";
        String serial = sipConfig.getSerial();
        String domain = StrUtil.sub(serial, 3, 8);
        String random = RandomUtil.randomNumbers(4);
        return real + domain + random;
    }


    public String buildHistorySsrc() {
        String history = "1";
        String serial = sipConfig.getSerial();
        String domain = StrUtil.sub(serial, 3, 8);
        String random = RandomUtil.randomNumbers(4);
        return history + domain + random;
    }

    public Response buildResponse(int responseStatus, Request request) throws ParseException {
        return messageFactory.createResponse(responseStatus, request);
    }

    public Request buildRequest(SipDevice sipDevice, String method) {
        return buildRequest(sipDevice, null, method, null, null, null);
    }

    public Request buildXmlRequest(SipDevice sipDevice, String method, String content) {
        return buildRequest(sipDevice, null, method, "MANSCDP+xml", content, null);
    }

    /**
     * 发送sdp  invite
     *
     * @author cqmike
     * @param sipDevice
     * @param method
     * @param content 内容
     * @param subject 媒体流发送者设备编码:发送端媒体流序列号,媒体流接收者设备编码:接收端媒体流序列号
     *                通道id:ssrc,sip服务器id,0
     * @since 1.0.0
     * @return
     */
    public Request buildSdpRequest(SipDevice sipDevice, String channelId, String method, String content, String subject) {
        return buildRequest(sipDevice, channelId, method, "SDP", content, subject);
    }


    public Request buildRequest(SipDevice sipDevice, String channelId, String method, String contentType, String content, String subject) {
        try {
            String tm = Long.toString(System.currentTimeMillis());
            String sipDeviceId = sipDevice.getSipDeviceId();
            String id = StrUtil.emptyToDefault(channelId, sipDeviceId);
            //请求行
            SipURI requestLine = addressFactory.createSipURI(id, StrUtil.format("{}:{}", sipDevice.getIp(), sipDevice.getPort()));
            //Via头
            List<ViaHeader> viaHeaderList = new ArrayList<>();
            ViaHeader viaHeader = headerFactory.createViaHeader(sipConfig.getIp(), sipConfig.getPort(), sipDevice.getMediaTransport(), "z9hG4bK" + tm);
            viaHeader.setRPort();
            viaHeaderList.add(viaHeader);

            //To头   设备域和服务器的域一样
            SipURI toAddress = addressFactory.createSipURI(id, sipConfig.getRealm());
            Address toNameAddress = addressFactory.createAddress(toAddress);
            ToHeader toHeader = headerFactory.createToHeader(toNameAddress, "FromSip" + tm);

            //From头
            SipURI from = addressFactory.createSipURI(sipConfig.getSerial(), sipConfig.getRealm());
            Address fromNameAddress = addressFactory.createAddress(from);
            FromHeader fromHeader = headerFactory.createFromHeader(fromNameAddress, "FromSip" + tm);


            //callId
            CallIdHeader callIdHeader = sipDevice.getMediaTransport().equals(TCP) ? sipTcpProvider.getNewCallId() : sipUdpProvider.getNewCallId();

            //Cseq
            CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1L, method);

            //Max_forward
            MaxForwardsHeader maxForwardsHeader = headerFactory.createMaxForwardsHeader(70);

            Request request = messageFactory.createRequest(requestLine, method, callIdHeader, cSeqHeader, fromHeader, toHeader,
                    viaHeaderList, maxForwardsHeader);

            if (StrUtil.isNotEmpty(contentType) && StrUtil.isNotEmpty(content)) {
                ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("Application", contentType);
                request.addHeader(contentTypeHeader);
                request.setContent(content, contentTypeHeader);
            }

            if (StrUtil.isNotEmpty(subject)) {
                SubjectHeader subjectHeader = sipFactory
                        .createHeaderFactory()
                        .createSubjectHeader(subject);
                request.addHeader(subjectHeader);
            }

            return request;
        } catch (Exception e) {
            throw new SipRequestBuildException(e);
        }
    }

}
