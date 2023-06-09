package com.cqmike.sip.core.gb28181.transaction;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
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

    public Response buildResponse(int responseStatus, Request request) throws ParseException {
        return messageFactory.createResponse(responseStatus, request);
    }

    public Request buildRequest(SipDevice sipDevice, String method) throws ParseException, InvalidArgumentException {
        String tm = Long.toString(System.currentTimeMillis());
        //请求行
        SipURI requestLine = addressFactory.createSipURI(sipConfig.getSerial(), StrUtil.format("{}:{}", sipDevice.getIp(), sipDevice.getPort()));
        //Via头
        List<ViaHeader> viaHeaderList = new ArrayList<>();
        ViaHeader viaHeader = headerFactory.createViaHeader(sipConfig.getIp(), sipConfig.getPort(), sipDevice.getMediaTransport(), "FromSip" + tm);
        viaHeader.setReceived(sipConfig.getIp());
        viaHeader.setParameter("rport", sipConfig.getPort().toString());
        viaHeaderList.add(viaHeader);

        //To头   设备域和服务器的域一样
        SipURI toAddress = addressFactory.createSipURI(sipDevice.getSipDeviceId(), sipConfig.getRealm());
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

        return messageFactory.createRequest(requestLine, method, callIdHeader, cSeqHeader, fromHeader, toHeader,
                viaHeaderList, maxForwardsHeader);
    }

    public void setContent(Request request, String catalogContent) throws ParseException {

        ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("Application", "MANSCDP+xml");
        request.setContent(catalogContent, contentTypeHeader);
        request.addHeader(contentTypeHeader);
    }


}
