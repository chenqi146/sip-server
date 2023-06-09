package com.cqmike.sip.core.gb28181.annotation;

import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.enums.SipResMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TODO
 *
 * @author cqmike
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface ResEventHandler {

    SipResMethod method();

}
