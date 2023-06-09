package com.cqmike.sip.core.gb28181.cmd.impl;

import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.helper.SipContentHelper;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sip.InvalidArgumentException;
import javax.sip.message.Request;
import java.text.ParseException;

/**
 * SipCommanderImpl
 *
 * @author cqmike
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SipCommanderImpl implements SipCommander {

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
    public void catalogQuery(SipDevice sipDevice) throws InvalidArgumentException, ParseException {
        Request request = sipProtocolFactory.buildRequest(sipDevice, Request.MESSAGE);
        String catalogContentXml = SipContentHelper.buildCatalogContentXml(sipDevice.getSipDeviceId());
        sipProtocolFactory.setContent(request, catalogContentXml);
        serverTransactionFactory.sendRequest(request);
        log.info("下发catalog查询消息: {}", sipDevice.getSipDeviceId());

    }

}
