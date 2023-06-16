package com.cqmike.sip.core.gb28181.cmd.dto;

import com.cqmike.sip.core.entity.SipDevice;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 邀请推流参数
 *
 * @author cqmike
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteHistoryStreamCmd extends InviteStreamCmd {

    private LocalDateTime startTime;

    private LocalDateTime endTime;


}
