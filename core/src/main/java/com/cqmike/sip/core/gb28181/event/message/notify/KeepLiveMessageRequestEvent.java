package com.cqmike.sip.core.gb28181.event.message.notify;

import com.cqmike.sip.core.gb28181.annotation.MessageEventHandler;
import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.event.base.AbstractMessageRequestEvent;
import com.cqmike.sip.core.gb28181.helper.XmlHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * 心跳消息
 *
 * @author cqmike
 * @return
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@XmlRootElement(name = "Notify")
@XmlAccessorType(XmlAccessType.FIELD)
@MessageEventHandler(type = SipMessageType.NOTIFY_KEEPALIVE)
public class KeepLiveMessageRequestEvent extends AbstractMessageRequestEvent {
    @XmlElement(name = "CmdType")
    private String cmdType;

    @XmlElement(name = "SN")
    private String serialNumber;

    @XmlElement(name = "DeviceID")
    private String deviceId;

    @XmlElement(name = "Status")
    private String status;

    /**
     * 各子类实现
     */
    @Override
    public void parse() throws Exception {
        String content = this.content;
        KeepLiveMessageRequestEvent bean = XmlHelper.xmlToBean(content, KeepLiveMessageRequestEvent.class);
        if (Objects.nonNull(bean)) {
            this.abstractMessageRequestEvent = bean;
            this.cmdType = bean.getCmdType();
            this.status = bean.getStatus();
            this.deviceId = bean.getDeviceId();
            this.serialNumber = bean.getSerialNumber();
        }
    }


}
