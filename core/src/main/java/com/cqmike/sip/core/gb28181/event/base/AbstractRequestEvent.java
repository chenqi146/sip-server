package com.cqmike.sip.core.gb28181.event.base;

import lombok.Getter;
import lombok.ToString;

import javax.sip.RequestEvent;

/**
 * @author <a href="mailto:cqmike0315@gmail.com">chenqi</a>
 * @version 1.0
 */
@Getter
@ToString
public abstract class AbstractRequestEvent {

    protected RequestEvent requestEvent;

    /**
     * 各子类实现
     */
    public abstract void parse() throws Exception;

    public void init(RequestEvent requestEvent) throws Exception  {
        this.requestEvent = requestEvent;
        this.parse();
    }
}
