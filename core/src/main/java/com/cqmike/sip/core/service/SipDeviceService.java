package com.cqmike.sip.core.service;

import com.cqmike.sip.core.entity.DeviceChannel;
import com.cqmike.sip.core.entity.SipDevice;

import java.util.Collection;
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

    void updateOnline(SipDevice device);

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
}
