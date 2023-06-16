package com.cqmike.sip.core.service;

import com.cqmike.sip.core.entity.DeviceChannel;
import com.cqmike.sip.core.entity.RecordInfo;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.event.response.impl.InviteResponseEvent;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * SipDeviceService
 *
 * @author cqmike
 **/
public interface SipDeviceService {
    /**
     * 注册或更新设备
     *
     * @author cqmike
     * @param sipDevice
     * @since 1.0.0
     * @return
     */
    void registerDevice(SipDevice sipDevice);

    /**
     * 查询设备
     *
     * @author cqmike
     * @param sipDeviceId
     * @since 1.0.0
     * @return
     */
    Optional<SipDevice> findBySipDeviceId(String sipDeviceId);

    /**
     * 根据id更新在线状态
     *
     * @author cqmike
     * @param device
     * @since 1.0.0
     * @return
     */
    void updateOnline(SipDevice device);

    void updateKeepLive(String sipDeviceId);

    /**
     * 保存或更新设备通道
     *
     * @author cqmike
     * @param sipDeviceId
     * @param deviceChannels
     * @since 1.0.0
     * @return
     */
    void saveOrUpdateDeviceChannel(String sipDeviceId, Collection<DeviceChannel> deviceChannels);

    /**
     * 保存邀请流响应
     *
     * @author cqmike
     * @param deviceId
     * @param channelId
     * @param event
     * @since 1.0.0
     * @return
     */
    void saveStreamResponse(String deviceId, String channelId, InviteResponseEvent event);

    Optional<InviteResponseEvent> getStreamResponse(String deviceId, String channelId);

    void removeStreamResponse(String deviceId, String channelId);

    void saveRecordInfoList(String deviceId, List<RecordInfo> recordInfos);

    List<RecordInfo> listRecordInfo(String deviceId);
}
