package com.cqmike.sip.core.gb28181.event.request.message.query;

import com.cqmike.sip.core.gb28181.annotation.MessageEventHandler;
import com.cqmike.sip.core.gb28181.dto.DeviceChannelDTO;
import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.event.base.AbstractMessageRequestEvent;
import com.cqmike.sip.core.gb28181.helper.XmlHelper;
import lombok.*;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * 设备信息查询响应
 *
 * @author cqmike
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
@MessageEventHandler(type = SipMessageType.QUERY_CATALOG)
public class CatalogQueryMessageEvent extends AbstractMessageRequestEvent {

    @XmlElement(name = "DeviceID")
    private String deviceId;

    @XmlElement(name = "SumNum")
    private Integer sumNum;

    @XmlElement(name = "SN")
    private String sn;

    @XmlElement(name = "DeviceList")
    private DeviceListDTO deviceListDTO;

    private List<DeviceChannelDTO> deviceChannel;


    @Override
    public void parse() throws JAXBException {
        String content = this.content;
        CatalogQueryMessageEvent catalogMessageResponse = XmlHelper.xmlToBean(content, CatalogQueryMessageEvent.class);
        Optional.ofNullable(catalogMessageResponse).ifPresent(res -> {
            this.abstractMessageRequestEvent = catalogMessageResponse;
            this.deviceId = catalogMessageResponse.getDeviceId();
            this.sumNum = catalogMessageResponse.getSumNum();
            this.sn = catalogMessageResponse.getSn();
            this.deviceChannel = catalogMessageResponse.getDeviceListDTO().getItem();
        });
    }


    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    static class DeviceListDTO {

        @XmlAttribute(name = "Num")
        private Integer num;

        @XmlElement(name = "Item")
        private List<DeviceChannelDTO> item;

    }
}
