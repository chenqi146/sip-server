package com.cqmike.sip.core.gb28181.dto;

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
public class InviteStreamDTO {

    private SipDevice sipDevice;

    private String sipDeviceChannelId;

    /**
     * 流标识
     */
    private String ssrc;

}
