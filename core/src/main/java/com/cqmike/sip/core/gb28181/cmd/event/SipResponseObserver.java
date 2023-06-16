package com.cqmike.sip.core.gb28181.cmd.event;

/**
 * sip响应观察者
 *
 * @author cqmike
 **/
@FunctionalInterface
public interface SipResponseObserver {

    void processResponse(SipEventResult eventResult);

}
