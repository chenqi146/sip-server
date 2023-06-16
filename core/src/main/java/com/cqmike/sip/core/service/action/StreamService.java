package com.cqmike.sip.core.service.action;

import com.cqmike.sip.core.entity.DeviceStream;

import java.util.concurrent.CompletableFuture;

/**
 * 流相关操作
 *
 * @author cqmike
 **/
public interface StreamService {

    /**
     * 邀请推流
     *
     * @author cqmike
     * @param sipDeviceId
     * @param channel
     * @since 1.0.0
     * @return
     */
    CompletableFuture<DeviceStream> inviteStream(String sipDeviceId, String channel);

    void byeStream(String sipDeviceId, String channel);

}
