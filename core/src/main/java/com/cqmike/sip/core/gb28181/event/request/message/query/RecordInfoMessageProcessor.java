package com.cqmike.sip.core.gb28181.event.request.message.query;

import com.cqmike.sip.core.entity.RecordInfo;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.event.request.message.AbstractMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 查询录像信息
 *
 * @author cqmike
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class RecordInfoMessageProcessor extends AbstractMessageHandler<RecordInfoMessageEvent> {

    /**
     * message中的类型
     *
     * @return
     */
    @Override
    public SipMessageType type() {
        return SipMessageType.QUERY_RECORD_INFO;
    }

    /**
     * 处理录像信息
     *
     * @param event
     * @param sipDevice
     * @retur
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    protected void handleDeviceMessage(RecordInfoMessageEvent event, SipDevice sipDevice) {

        String deviceId = event.getDeviceId();
        String sn = event.getSn();
        List<RecordInfo> recordInfoList = event.getRecordInfoList();
    }
}
