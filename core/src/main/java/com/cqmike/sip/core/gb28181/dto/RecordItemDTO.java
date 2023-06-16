package com.cqmike.sip.core.gb28181.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;

/**
 * RecordItemDTO
 *
 * @author cqmike
 **/
@Data
public class RecordItemDTO {

    @XmlElement(name = "FilePath")
    private String filePath;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Address")
    private String address;

    @XmlElement(name = "RecorderID")
    private String channelId;

    @XmlElement(name = "StartTime")
    private LocalDateTime startTime;

    @XmlElement(name = "EndTime")
    private LocalDateTime endTime;

    /**
     * 录像产生类型(可选)time 或alarm 或 manual 或all
     */
    @XmlElement(name = "Type")
    private String type;

    /**
     * 保密属性 可选  0-不涉密, 1-涉密
     */
    @XmlElement(name = "Secrecy")
    private String secrecy;
}
