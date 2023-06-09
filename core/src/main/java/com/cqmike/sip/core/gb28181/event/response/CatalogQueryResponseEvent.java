package com.cqmike.sip.core.gb28181.event.response;

import com.cqmike.sip.core.gb28181.dto.DeviceChannelDTO;
import com.cqmike.sip.core.gb28181.event.base.AbstractMessageRequestEvent;
import com.cqmike.sip.core.gb28181.helper.XmlHelper;
import lombok.*;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
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
public class CatalogQueryResponseEvent extends AbstractMessageRequestEvent {

    @XmlElement(name = "DeviceID")
    private String deviceId;

    @XmlElement(name = "SumNum")
    private Integer sumNum;

    @XmlElement(name = "Sn")
    private String sn;

    private List<DeviceChannelDTO> deviceChannel;


    @Override
    public void parse() throws JAXBException {
        String content = this.content;
        CatalogQueryResponseEvent catalogMessageResponse = XmlHelper.xmlToBean(content, CatalogQueryResponseEvent.class);
        Optional.ofNullable(catalogMessageResponse).ifPresent(res -> this.abstractMessageRequestEvent = catalogMessageResponse);
    }
}
