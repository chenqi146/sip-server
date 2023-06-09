package com.cqmike.sip.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.service.SipDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    /**
     * 注册或更新设备
     *
     * @param event
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
}
