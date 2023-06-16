package com.cqmike.sip.core.controller;

import com.cqmike.sip.core.entity.DeviceStream;
import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.helper.AsyncHelper;
import com.cqmike.sip.core.service.SipDeviceService;
import com.cqmike.sip.core.service.action.StreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * SipDeviceController
 *
 * @author cqmike
 **/
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sip/device")
public class SipDeviceController {

    private final SipCommander sipCommander;
    private final SipDeviceService sipDeviceService;
    private final StreamService streamService;

    @GetMapping("/test")
    public CompletableFuture<SipDevice> test(String id) {
        Optional<SipDevice> optional = sipDeviceService.findBySipDeviceId(id);
        if (optional.isPresent()) {
            sipCommander.catalogQuery(optional.get());
        }
        return null;
    }


    @GetMapping("/testInvite")
    public CompletableFuture<DeviceStream> testInvite(String id, String channel) {
        return AsyncHelper
                .within(streamService.inviteStream(id, channel), 15, TimeUnit.SECONDS)
                .exceptionally(e -> {
                    DeviceStream deviceStream = new DeviceStream();
                    deviceStream.setStreamCode("");
                    return deviceStream;
                });
    }


}
