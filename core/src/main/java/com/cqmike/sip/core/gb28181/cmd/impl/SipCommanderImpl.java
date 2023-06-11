package com.cqmike.sip.core.gb28181.cmd.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.config.RtcConfig;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.dto.InviteStreamDTO;
import com.cqmike.sip.core.gb28181.helper.SipContentHelper;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sip.message.Request;

/**
 * SipCommanderImpl
 *
 * @author cqmike
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SipCommanderImpl implements SipCommander {

    private final SipConfig sipConfig;
    private final RtcConfig rtcConfig;
    private final SipProtocolFactory sipProtocolFactory;
    private final ServerTransactionFactory serverTransactionFactory;

    /**
     * 查询设备信息
     *
     * @param sipDevice
     * @return
     * @author cqmike 2
     * @since 1.0.0
     */
    @Override
    public void catalogQuery(SipDevice sipDevice) {
        String catalogContentXml = SipContentHelper.buildCatalogContentXml(sipDevice.getSipDeviceId());
        Request request = sipProtocolFactory.buildXmlRequest(sipDevice, Request.MESSAGE, catalogContentXml);
        serverTransactionFactory.sendRequest(request);
    }

    /**
     * 邀请设备推流
     *
     * @param dto
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public void inviteStream(InviteStreamDTO dto) {
        SipDevice sipDevice = dto.getSipDevice();
        String ssrc = StrUtil.isEmpty(dto.getSsrc()) ? IdUtil.fastSimpleUUID() : dto.getSsrc();
        String sipDeviceChannelId = dto.getSipDeviceChannelId();
        String content = SipContentHelper.buildRealTimeMediaStreamInviteContent(sipDeviceChannelId, "192.168.54.141", rtcConfig.getMuxPort(), ssrc);
        String subject = StrUtil.format("{}:{},{}:{}", sipDeviceChannelId, ssrc, sipConfig.getSerial(), 0);
        Request request = sipProtocolFactory.buildSdpRequest(sipDevice, sipDeviceChannelId, Request.INVITE, content, subject);
        serverTransactionFactory.sendRequest(request);
    }
}
