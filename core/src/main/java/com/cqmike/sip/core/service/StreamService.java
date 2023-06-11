package com.cqmike.sip.core.service;

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
    void inviteStream(String sipDeviceId, Integer channel);

}
