package com.cqmike.sip.core.gb28181.listeners;

import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.event.request.RegisterRequestEvent;
import com.cqmike.sip.core.gb28181.event.response.CatalogQueryResponseEvent;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import com.cqmike.sip.core.service.SipDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.header.HeaderFactory;
import javax.sip.message.Response;

/**
 * 设备请求监听处理器
 *
 * @author cqmike
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseListener {

    private final SipConfig sipConfig;

    private final ServerTransactionFactory serverTransactionFactory;
    private final SipProtocolFactory sipProtocolFactory;

    private final HeaderFactory headerFactory;

    private final SipDeviceService sipDeviceService;
    private final SipCommander sipCommander;


    @EventListener
    public void catalogQuery(CatalogQueryResponseEvent event) {

        try {
            // 响应Ok
            RequestEvent requestEvent = event.getRequestEvent();
            Response response = sipProtocolFactory.buildResponse(Response.OK, requestEvent.getRequest());
            serverTransactionFactory.sendResponse(requestEvent, response);

            // 保存入库
            String deviceId = event.getDeviceId();
            sipDeviceService.findBySipDeviceId(deviceId).ifPresent(device -> {

            });
        } catch (Exception e) {

        }

    }

}
