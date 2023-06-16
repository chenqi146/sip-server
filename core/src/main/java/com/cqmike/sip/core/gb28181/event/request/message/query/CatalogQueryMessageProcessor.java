package com.cqmike.sip.core.gb28181.event.request.message.query;

import cn.hutool.core.bean.BeanUtil;
import com.cqmike.sip.core.entity.DeviceChannel;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.event.request.message.AbstractMessageHandler;
import com.cqmike.sip.core.service.SipDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询设备信息
 *
 * @author cqmike
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogQueryMessageProcessor extends AbstractMessageHandler<CatalogQueryMessageEvent> {

    /**
     * message中的类型
     *
     * @return
     */
    @Override
    public SipMessageType type() {
        return SipMessageType.QUERY_CATALOG;
    }

    /**
     * 处理设备信息
     *
     * @param event
     * @param sipDevice
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    protected void handleDeviceMessage(CatalogQueryMessageEvent event, SipDevice sipDevice) {
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
}
