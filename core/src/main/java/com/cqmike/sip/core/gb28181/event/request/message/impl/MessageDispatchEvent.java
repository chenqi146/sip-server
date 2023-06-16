package com.cqmike.sip.core.gb28181.event.request.message.impl;

import com.cqmike.sip.common.exceptions.SipParseException;
import com.cqmike.sip.core.gb28181.annotation.ReqEventHandler;
import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.base.AbstractRequestEvent;
import com.cqmike.sip.core.gb28181.helper.XmlHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.dom4j.Element;

/**
 * message基础分发事件类, request收到的message消息全部由此类发布spring事件进行处理
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@ReqEventHandler(method = SipMethod.MESSAGE)
public class MessageDispatchEvent extends AbstractRequestEvent {

    private final static String CMD_TYPE = "CmdType";
    private SipMessageType sipMessageType;

    /**
     * 各子类实现
     */
    @Override
    public void parse() throws Exception {
        Element element = XmlHelper.getRootElement(requestEvent.getRequest().getRawContent());
        this.sipMessageType = SipMessageType.of(element.getName(), XmlHelper.getText(element, CMD_TYPE)).orElseThrow(SipParseException::new);
    }
}
