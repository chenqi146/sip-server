package com.cqmike.sip.core.gb28181.callback;

import com.cqmike.sip.core.entity.DeviceStream;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 邀请推流web回调
 *
 * @author cqmike
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class InviteStreamFuture extends AbstractSipFuture<DeviceStream> {

    /**
     * 设备流信息
     */
    private DeviceStream deviceStream;




}
