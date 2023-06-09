package com.cqmike.sip.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.cqmike.sip.core.entity.DeviceChannel;
import com.cqmike.sip.core.entity.RecordInfo;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.event.response.impl.InviteResponseEvent;
import com.cqmike.sip.core.service.SipDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sip设备接口
 *
 * @author cqmike
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SipDeviceServiceImpl implements SipDeviceService {

    public static final Map<String, SipDevice> map = new HashMap<>();
    public static final Map<String, List<RecordInfo>> recordListMap = new HashMap<>();
    public static final Map<String, Map<String, InviteResponseEvent>> inviteStreamResponseMap = new HashMap<>();

    /**
     * 注册或更新设备
     *
     * @param registerDevice
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public void registerDevice(SipDevice registerDevice) {
        log.info("注册设备: {}", registerDevice);
        Optional<SipDevice> op = this.findBySipDeviceId(registerDevice.getSipDeviceId());
        // 注册
        LocalDateTime now = LocalDateTime.now();
        SipDevice sipDevice = op.orElse(new SipDevice());
        if (op.isPresent()) {
            // 更新状态
            sipDevice.setOnline(true);
            sipDevice.setLastRegisterAt(now);
            sipDevice.setLastKeepaliveAt(now);
            sipDevice.setCreatedAt(now);
            sipDevice.setUpdatedAt(now);
            map.put(sipDevice.getSipDeviceId(), sipDevice);
        } else {
            BeanUtil.copyProperties(registerDevice, sipDevice);
            sipDevice.setSipDeviceId(registerDevice.getSipDeviceId());
            sipDevice.setType("GB");
            sipDevice.setSubscribeInterval(0);
            sipDevice.setAlarmSubscribe(false);
            sipDevice.setCatalogSubscribe(false);
            sipDevice.setPositionSubscribe(false);
            sipDevice.setCatalogInterval(3600);
            sipDevice.setPassword(sipDevice.getPassword());
            sipDevice.setOnline(true);
            sipDevice.setLastRegisterAt(now);
            sipDevice.setLastKeepaliveAt(now);
            sipDevice.setCreatedAt(now);
            sipDevice.setUpdatedAt(now);
            map.put(sipDevice.getSipDeviceId(), sipDevice);
        }

    }

    /**
     * 查询设备
     *
     * @param sipDeviceId
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public Optional<SipDevice> findBySipDeviceId(String sipDeviceId) {
        return Optional.ofNullable(map.get(sipDeviceId));
    }

    @Override
    public void updateOnline(SipDevice device) {
        this.findBySipDeviceId(device.getSipDeviceId()).ifPresent(dev -> {
            dev.setOnline(device.isOnline());
            dev.setUpdatedAt(LocalDateTime.now());
            map.put(dev.getSipDeviceId(), dev);
        });
    }

    @Override
    public void updateKeepLive(String sipDeviceId) {
        this.findBySipDeviceId(sipDeviceId).ifPresent(dev -> {
            dev.setOnline(true);
            dev.setUpdatedAt(LocalDateTime.now());
            dev.setLastKeepaliveAt(LocalDateTime.now());
            map.put(dev.getSipDeviceId(), dev);
        });
    }

    /**
     * 保存或更新设备通道
     *
     * @param sipDeviceId
     * @param deviceChannels
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public void saveOrUpdateDeviceChannel(String sipDeviceId, Collection<DeviceChannel> deviceChannels) {
        this.findBySipDeviceId(sipDeviceId).ifPresent(dev -> {
            dev.setDeviceChannels(CollUtil.newArrayList(deviceChannels));
            dev.setChannelCount(deviceChannels.size());
        });
    }

    /**
     * 保存邀请流响应
     *
     * @param deviceId
     * @param channelId
     * @param event
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Override
    public void saveStreamResponse(String deviceId, String channelId, InviteResponseEvent event) {
        inviteStreamResponseMap.putIfAbsent(deviceId, new ConcurrentHashMap<>());
        inviteStreamResponseMap.get(deviceId).put(channelId, event);
    }

    @Override
    public Optional<InviteResponseEvent> getStreamResponse(String deviceId, String channelId) {
        Map<String, InviteResponseEvent> eventMap = inviteStreamResponseMap.get(deviceId);
        if (CollUtil.isEmpty(eventMap)) {
            return Optional.empty();
        }
        return Optional.ofNullable(eventMap.get(channelId));
    }

    @Override
    public void removeStreamResponse(String deviceId, String channelId) {
        Map<String, InviteResponseEvent> eventMap = inviteStreamResponseMap.get(deviceId);
        if (CollUtil.isEmpty(eventMap)) {
            return;
        }
        eventMap.remove(channelId);
        if (eventMap.size() == 0) {
            inviteStreamResponseMap.remove(deviceId);
        }
    }

    @Override
    public void saveRecordInfoList(String deviceId, List<RecordInfo> recordInfos) {
        if (StrUtil.isEmpty(deviceId) || CollUtil.isEmpty(recordInfos)) {
            return;
        }
        recordListMap.put(deviceId, recordInfos);
    }

    @Override
    public List<RecordInfo> listRecordInfo(String deviceId) {
        if (StrUtil.isEmpty(deviceId)) {
            return Collections.emptyList();
        }
        return recordListMap.getOrDefault(deviceId, Collections.emptyList());
    }
}
