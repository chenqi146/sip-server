package com.cqmike.sip.gb28181.event.base;

import lombok.Getter;

import javax.sip.RequestEvent;

/**
 * @author <a href="mailto:cqmike0315@gmail.com">chenqi</a>
 * @version 1.0
 */
@Getter
public abstract class AbstractRequestEvent {

    protected RequestEvent requestEvent;

    /**
     * 各子类实现
     */
    public abstract void process();

    public void handle(RequestEvent requestEvent) {
        this.requestEvent = requestEvent;
        this.process();
    }
}
