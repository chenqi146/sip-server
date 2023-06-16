package com.cqmike.sip.core.gb28181.cmd.dto;

import com.cqmike.sip.core.entity.SipDevice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 录像查询
 *
 * @author cqmike
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordInfoQueryCmd {

    private SipDevice sipDevice;

    private String channelId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /**
     * 命令序列号, 消息标识
     */
    private String sn;

    /**
     * 保密属性 可选  0-不涉密, 1-涉密
     */
    private Integer secrecy;

    /**
     * 录像产生类型(可选)time 或alarm 或 manual 或all
     */
    private String type;
}
