package com.cqmike.sip.core.gb28181.event.response;

import cn.hutool.core.util.StrUtil;
import com.cqmike.sip.core.gb28181.annotation.ResEventHandler;
import com.cqmike.sip.core.gb28181.enums.SipResMethod;
import com.cqmike.sip.core.gb28181.event.base.AbstractResponseEvent;
import gov.nist.javax.sip.header.AddressParametersHeader;
import lombok.extern.slf4j.Slf4j;

import javax.sip.address.SipURI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Description ossrs.net.srssip.gb28181.event.response
 * @ Author StormBirds
 * @ Email xbaojun@gmail.com
 * @ Date 24/2/2022 上午1:01
 */
@Slf4j
@ResEventHandler(method = SipResMethod.INVITE)
public class InviteResponseEvent extends AbstractResponseEvent {

    private String deviceId;

    private String channelId;

    private String streamCode;

    @Override
    public void process() {
        if (isSuccess()) {

            responseAck();
            log.info("接收到拉流响应： {}", getContent());
            AddressParametersHeader header = (AddressParametersHeader) response.getHeader("To");
            SipURI sipURI = (SipURI) header.getAddress().getURI();
            this.channelId = sipURI.getUser();
            Map<String, String> contentMap = convert();
            if (contentMap != null && contentMap.size() > 0) {
                String streamField = "y";
                this.streamCode = contentMap.get(streamField);
                String deviceField = "o";
                this.deviceId = contentMap.get(deviceField).substring(0, 20);
            }
            log.info("目标客户端 {}", this.channelId);
        }

    }

    public Map<String, String> convert() {
        if (StrUtil.isEmpty(content)) {
            return Collections.emptyMap();
        }

        Map<String, String> data = new HashMap<>();
        String[] values = content.split(StrUtil.CRLF);
        for (String value : values) {
            String[] fields = value.split("=");
            if (fields.length != 2) {
                continue;
            }
            data.put(fields[0], fields[1]);
        }
        return data;
    }

    @Override
    public boolean isSuccess() {
        return super.isSuccess();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getStreamCode() {
        return streamCode;
    }
}
