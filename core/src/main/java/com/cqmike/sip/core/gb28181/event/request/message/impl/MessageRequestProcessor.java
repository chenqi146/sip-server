package com.cqmike.sip.core.gb28181.event.request.message.impl;

import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.request.AbstractSipRequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Message处理器
 *
 * @author cqmike
 **/
@Component
@Slf4j
public class MessageRequestProcessor extends AbstractSipRequestProcessor<MessageDispatchEvent> {

    @Override
    public SipMethod method() {
        return SipMethod.MESSAGE;
    }

    @Override
    public void process(MessageDispatchEvent event) {
        // TODO: 2023/6/15 分发各种子处理器
    }

}
