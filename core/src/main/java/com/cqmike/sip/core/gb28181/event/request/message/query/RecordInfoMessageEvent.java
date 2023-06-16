package com.cqmike.sip.core.gb28181.event.request.message.query;

import cn.hutool.core.bean.BeanUtil;
import com.cqmike.sip.common.convert.BeanConvertUtil;
import com.cqmike.sip.core.entity.RecordInfo;
import com.cqmike.sip.core.gb28181.annotation.MessageEventHandler;
import com.cqmike.sip.core.gb28181.dto.RecordItemDTO;
import com.cqmike.sip.core.gb28181.enums.SipMessageType;
import com.cqmike.sip.core.gb28181.event.base.AbstractMessageRequestEvent;
import com.cqmike.sip.core.gb28181.helper.XmlHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Optional;

/**
 * 录像列表查询响应
 *
 * @author cqmike
 * @since 1.0.0
 * @return
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@MessageEventHandler(type = SipMessageType.QUERY_RECORD_INFO)
public class RecordInfoMessageEvent extends AbstractMessageRequestEvent {

    @XmlElement(name = "DeviceID")
    private String deviceId;

    @XmlElement(name = "SumNum")
    private Integer sumNum;

    @XmlElement(name = "SN")
    private String sn;

    @XmlElement(name = "RecordList")
    private RecordXmlList itemDto;

    private List<RecordInfo> recordInfoList;

    @Override
    public void parse() throws JAXBException {
        RecordInfoMessageEvent recordInfoMessageEvent = XmlHelper.xmlToBean(content, RecordInfoMessageEvent.class);
        Optional.ofNullable(recordInfoMessageEvent).ifPresent(response -> {
            this.abstractMessageRequestEvent = response;
            BeanUtil.copyProperties(response, this);
            List<RecordItemDTO> itemList = itemDto.getItemList();
            this.recordInfoList = BeanConvertUtil.convertList(itemList, RecordInfo::new);
        });
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class RecordXmlList {

        @XmlAttribute(name = "Num")
        private Integer num;


        @XmlElement(name = "Item")
        private List<RecordItemDTO> itemList;
    }

}
