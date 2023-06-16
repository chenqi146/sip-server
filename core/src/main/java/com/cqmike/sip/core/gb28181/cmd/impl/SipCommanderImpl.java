package com.cqmike.sip.core.gb28181.cmd.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.cqmike.sip.common.exceptions.SipRequestBuildException;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.cmd.dto.InviteHistoryStreamCmd;
import com.cqmike.sip.core.gb28181.cmd.dto.RecordInfoQueryCmd;
import com.cqmike.sip.core.gb28181.config.RtcConfig;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.cmd.dto.InviteStreamCmd;
import com.cqmike.sip.core.gb28181.event.response.impl.InviteResponseEvent;
import com.cqmike.sip.core.gb28181.helper.SipContentHelper;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.ResponseEvent;
import javax.sip.SipException;
import javax.sip.message.Request;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
        log.info("[发送catalogQuery消息] deviceId: {}", sipDevice.getSipDeviceId());
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
    public void inviteStream(InviteStreamCmd dto) {
        SipDevice sipDevice = dto.getSipDevice();
        String ssrc = StrUtil.isEmpty(dto.getSsrc()) ? IdUtil.fastSimpleUUID() : dto.getSsrc();
        String sipDeviceChannelId = dto.getSipDeviceChannelId();
        String content = SipContentHelper.buildRealTimeMediaStreamInviteContent(sipDeviceChannelId, "192.168.54.141", rtcConfig.getMuxPort(), ssrc);
        String subject = StrUtil.format("{}:{},{}:{}", sipDeviceChannelId, ssrc, sipConfig.getSerial(), 0);
        Request request = sipProtocolFactory.buildSdpRequest(sipDevice, sipDeviceChannelId, Request.INVITE, content, subject);
        serverTransactionFactory.sendRequest(request);
        log.info("[发送invite直播消息] deviceId: {}, channelId: {}, ssrc: {}", sipDevice.getSipDeviceId(), sipDeviceChannelId, ssrc);
    }


    @Override
    public void byeStream(InviteResponseEvent streamResponse) {
        ResponseEvent responseEvent = streamResponse.getResponseEvent();
        ClientTransaction clientTransaction = responseEvent.getClientTransaction();
        if (Objects.isNull(clientTransaction)) {
            return;
        }
        Dialog dialog = clientTransaction.getDialog();
        if (Objects.isNull(dialog)) {
            return;
        }
        try {
            Request dialogRequest = dialog.createRequest(Request.BYE);
            ClientTransaction transaction = serverTransactionFactory.getClientTransaction(dialogRequest);
            dialog.sendRequest(transaction);
            log.info("[发送bye消息] deviceId: {}, channelId: {}", streamResponse.getDeviceId(), streamResponse.getChannelId());
        } catch (SipException e) {
            throw new SipRequestBuildException(e);
        }
    }

    /**
     * 录播播放
     *
     * @param dto
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public void inviteHistoryStream(InviteHistoryStreamCmd dto) {
        SipDevice sipDevice = dto.getSipDevice();
        String sipDeviceChannelId = dto.getSipDeviceChannelId();
        String ssrc = dto.getSsrc();
        String content = SipContentHelper.buildHistoryTimeMediaStreamInviteContent(
                sipDeviceChannelId, "192.168.54.141", rtcConfig.getMuxPort(),
                ssrc, dto.getStartTime(), dto.getEndTime());

        String subject = StrUtil.format("{}:{},{}:{}", sipDeviceChannelId, ssrc, sipConfig.getSerial(), 0);
        Request request = sipProtocolFactory.buildSdpRequest(sipDevice, sipDeviceChannelId, Request.INVITE, content, subject);
        serverTransactionFactory.sendRequest(request);
        log.info("[发送invite录播消息] deviceId: {}, channelId: {}, ssrc: {}", sipDevice.getSipDeviceId(), sipDeviceChannelId, ssrc);
    }

    /**
     * 录像查询
     *
     * @param cmd
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public void recordInfoQuery(RecordInfoQueryCmd cmd) {
        SipDevice sipDevice = cmd.getSipDevice();
        Document document = DocumentHelper.createDocument();
        Element query = document.addElement("Query");
        query.addElement("CmdType").addText("RecordInfo");
        query.addElement("SN").addText(cmd.getSn());
        query.addElement("DeviceID").addText(cmd.getChannelId());
        query.addElement("StartTime").addText(cmd.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        query.addElement("EndTime").addText(cmd.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        if (StrUtil.isNotEmpty(cmd.getType())) {
            query.addElement("Type").addText(cmd.getType());
        }
        if (Objects.nonNull(cmd.getSecrecy())) {
            query.addElement("Secrecy").addText(cmd.getSecrecy().toString());
        }
        String content = query.asXML();
        Request xmlRequest = sipProtocolFactory.buildXmlRequest(sipDevice, Request.MESSAGE, content);
        serverTransactionFactory.sendRequest(xmlRequest);
        log.info("[发送recordInfoQuery消息] deviceId: {}, channelId: {}, sn: {}", sipDevice.getSipDeviceId(), cmd.getChannelId(), cmd.getSn());
    }
}
