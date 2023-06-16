package com.cqmike.sip.core.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * RecordInfo
 *
 * @author cqmike
 **/
@Data
public class RecordInfo {

    private String deviceId;

    private String channelId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String filePath;

    /**
     * <pre>
     * 保密属性, 缺省为0, 0-不涉密, 1-涉密
     * 允许值: 0, 1
     * </pre>
     */
    private Integer secrecy;

    /**
     * 录像产生类型(可选)time 或alarm 或 manual 或all
     */
    private String type;

}
