package com.cqmike.sip.core.gb28181.ctx;

import com.cqmike.sip.core.gb28181.enums.CallBackEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sip异步回调管理¬
 *
 * @author cqmike
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class SipCallbackContext {


    private static final Map<String, Map<String, CompletableFuture<?>>> MAP = new ConcurrentHashMap<>();





}
