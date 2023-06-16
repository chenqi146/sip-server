package com.cqmike.sip.core.gb28181.cmd.dto;

import com.cqmike.sip.core.entity.SipDevice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 邀请推流参数
 *
 * @author cqmike
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteStreamCmd {

    protected SipDevice sipDevice;

    protected String sipDeviceChannelId;

    /**
     * 流标识
     */
    protected String ssrc;

}
