package com.cqmike.sip.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeviceStream
 *
 * @author cqmike
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceStream {

    private String sipDeviceId;

    /**
     * 通道id
     */
    private String channelId;

    /**
     * 流编码
     */
    private String streamCode;
}
