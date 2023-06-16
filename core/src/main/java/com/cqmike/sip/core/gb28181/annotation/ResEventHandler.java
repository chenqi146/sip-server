package com.cqmike.sip.core.gb28181.annotation;

import com.cqmike.sip.core.gb28181.enums.SipMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TODO
 *
 * @author cqmike
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface ResEventHandler {

    SipMethod method();

}
