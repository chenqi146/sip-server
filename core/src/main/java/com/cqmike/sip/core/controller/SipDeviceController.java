package com.cqmike.sip.core.controller;

import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.service.SipDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;
import java.util.Optional;

/**
 * TODO
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

    @GetMapping("/test")
    public void test(String id) throws InvalidArgumentException, ParseException, SipException {
        Optional<SipDevice> optional = sipDeviceService.findBySipDeviceId(id);
        if (optional.isPresent()) {
            sipCommander.catalogQuery(optional.get());
        }

    }
}
