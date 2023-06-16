package com.cqmike.sip.core.gb28181.event.request.message;

import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.ctx.SipCallbackContext;
import com.cqmike.sip.core.gb28181.dto.Platform;
import com.cqmike.sip.core.gb28181.event.base.AbstractMessageRequestEvent;
import com.cqmike.sip.core.gb28181.event.request.AbstractSipRequestProcessor;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import com.cqmike.sip.core.service.SipDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.sip.RequestEvent;
import javax.sip.header.HeaderFactory;
import javax.sip.message.Response;

/**
 * message消息处理, 处理设备的和上级平台的
 *
 * @author cqmike
 **/
public abstract class AbstractMessageHandler<T extends AbstractMessageRequestEvent> implements IMessageHandler<T> {
    protected static final Logger log = LoggerFactory.getLogger(AbstractSipRequestProcessor.class);

    @Resource
    protected SipConfig sipConfig;

    @Resource
    protected ServerTransactionFactory serverTransactionFactory;

    @Resource
    protected SipProtocolFactory sipProtocolFactory;
    @Resource
    protected SipDeviceService sipDeviceService;

    @Resource
    protected HeaderFactory headerFactory;

    @Resource
    protected SipCommander sipCommander;

    @Resource
    protected SipCallbackContext callbackContext;

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


    @Override
    public void handle(T event) {
        // TODO: 2023/6/15 区分平台和设备
    }

    /**
     * 处理设备信息
     *
     * @author cqmike
     * @param event
     * @param sipDevice
     * @since 1.0.0
     * @return
     */
    protected void handleDeviceMessage(T event, SipDevice sipDevice) {

    }

    /**
     * 处理平台信息
     *
     * @author cqmike
     * @param event
     * @param platform
     * @since 1.0.0
     * @return
     */
    protected void handlePlatformMessage(T event, Platform platform) {

    }
}
