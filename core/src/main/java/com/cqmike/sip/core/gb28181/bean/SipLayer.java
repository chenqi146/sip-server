package com.cqmike.sip.core.gb28181.bean;

import com.cqmike.sip.common.exceptions.SipBeanInitException;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.listeners.base.SipServerDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import javax.sip.*;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;
import java.util.Properties;
import java.util.TooManyListenersException;

/**
 * @author <a href="mailto:cqmike0315@gmail.com">chenqi</a>
 * @version 1.0
 */
@Slf4j
@Configuration
public class SipLayer {

    @Resource
    private SipConfig sipConfig;

    @Resource
    private SipServerDispatcher sipServerListener;

    @Bean
    public SipFactory sipFactory() {
        SipFactory sipFactory = SipFactory.getInstance();
        sipFactory.setPathName("gov.nist");
        return sipFactory;
    }

    @Bean
    @DependsOn({"sipFactory"})
    public HeaderFactory headerFactory(SipFactory sipFactory) throws PeerUnavailableException {
        return sipFactory.createHeaderFactory();
    }

    @Bean
    public AddressFactory addressFactory(SipFactory sipFactory) throws PeerUnavailableException {
        return sipFactory.createAddressFactory();
    }

    @Bean
    public MessageFactory messageFactory(SipFactory sipFactory) throws PeerUnavailableException {
        return sipFactory.createMessageFactory();
    }

    @Bean
    @DependsOn({"sipFactory"})
    public SipStack sipStack(SipFactory sipFactory) throws PeerUnavailableException {
        Properties properties = new Properties();
        properties.setProperty("javax.sip.STACK_NAME", sipConfig.getName());
        properties.setProperty("javax.sip.IP_ADDRESS", sipConfig.getIp());
        properties.setProperty("gov.nist.javax.sip.LOG_MESSAGE_CONTENT", sipConfig.getLogMessageContent());
        properties.setProperty("gov.nist.javax.sip.TRACE_LEVEL", sipConfig.getTraceLevel().getCode());
        properties.setProperty("gov.nist.javax.sip.SERVER_LOG", sipConfig.getName() + "_server_log");
        properties.setProperty("gov.nist.javax.sip.DEBUG_LOG", sipConfig.getName() + "_debug_log");
        return sipFactory.createSipStack(properties);
    }

    @Bean
    @DependsOn("sipStack")
    public SipProvider sipTcpProvider(SipStack sipStack) {
        ListeningPoint listeningPoint;
        SipProvider sipProvider;
        try {
            listeningPoint = sipStack.createListeningPoint(sipConfig.getIp(), sipConfig.getPort(), ListeningPoint.TCP);
            sipProvider = sipStack.createSipProvider(listeningPoint);
            sipProvider.addSipListener(sipServerListener);
            log.info("tcp server {} is running on port {}.", listeningPoint.getIPAddress(), listeningPoint.getPort());
        } catch (Exception e) {
            throw new SipBeanInitException(e);
        }
        return sipProvider;
    }

    @Bean
    @DependsOn("sipStack")
    public SipProvider sipUdpProvider(SipStack sipStack) {
        ListeningPoint listeningPoint;
        SipProvider sipProvider;
        try {
            listeningPoint = sipStack.createListeningPoint(sipConfig.getIp(), sipConfig.getPort(), ListeningPoint.UDP);
            sipProvider = sipStack.createSipProvider(listeningPoint);
            sipProvider.addSipListener(sipServerListener);
            log.info("udp server {} is running on port {}.", listeningPoint.getIPAddress(), listeningPoint.getPort());
        } catch (TransportNotSupportedException | InvalidArgumentException | ObjectInUseException |
                 TooManyListenersException e) {
            throw new SipBeanInitException(e);
        }
        return sipProvider;
    }
}
