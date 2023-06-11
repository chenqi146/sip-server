package com.cqmike.sip.core.gb28181.listeners;

import cn.hutool.core.bean.BeanUtil;
import com.cqmike.sip.core.entity.DeviceChannel;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.event.message.notify.KeepLiveMessageRequestEvent;
import com.cqmike.sip.core.gb28181.event.response.CatalogQueryResponseEvent;
import com.cqmike.sip.core.gb28181.event.response.InviteResponseEvent;
import com.cqmike.sip.core.gb28181.listeners.base.AbstractSipListener;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import com.cqmike.sip.core.service.SipDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.header.HeaderFactory;
import javax.sip.message.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 设备请求监听处理器
 *
 * @author cqmike
 **/
@Slf4j
@Component
public class ResponseListener extends AbstractSipListener {

    protected final SipDeviceService sipDeviceService;

    public ResponseListener(SipConfig sipConfig, ServerTransactionFactory serverTransactionFactory,
                            SipProtocolFactory sipProtocolFactory, HeaderFactory headerFactory,
                            SipCommander sipCommander, SipDeviceService sipDeviceService) {
        super(sipConfig, serverTransactionFactory, sipProtocolFactory, headerFactory, sipCommander);
        this.sipDeviceService = sipDeviceService;
    }


    /**
     * 接收catalog查询并响应
     *
     * @author cqmike
     * @param event
     * @since 1.0.0
     * @return
     */
    @EventListener
    public void catalogQuery(CatalogQueryResponseEvent event) {
        sendOk(event.getRequestEvent());
        // 保存入库
        String deviceId = event.getDeviceId();
        List<DeviceChannel> channelList = event.getDeviceChannel().stream().map(dc -> {
            DeviceChannel deviceChannel = new DeviceChannel();
            BeanUtil.copyProperties(dc, deviceChannel);
            return deviceChannel;
        }).collect(Collectors.toList());
        sipDeviceService.saveOrUpdateDeviceChannel(deviceId, channelList);
    }


    /**
     * sip设备心跳响应
     *
     * @author cqmike
     * @param event
     * @since 1.0.0
     * @return
     */
    @EventListener
    public void keepLive(KeepLiveMessageRequestEvent event) {
        RequestEvent requestEvent = event.getRequestEvent();
        String deviceId = event.getDeviceId();
        Optional<SipDevice> optional = sipDeviceService.findBySipDeviceId(deviceId);
        if (optional.isPresent()) {
            sendOk(requestEvent);
            sipDeviceService.updateKeepLive(deviceId);
            return;
        }
        send(requestEvent, Response.DOES_NOT_EXIST_ANYWHERE);
    }


    @EventListener
    public void inviteResponse(InviteResponseEvent event) {
        ResponseEvent responseEvent = event.getResponseEvent();
        log.info("邀请推流响应: {}", responseEvent.getResponse());
    }

}
