package com.cqmike.sip.core.gb28181.event.base;

import lombok.Getter;
import lombok.ToString;

import javax.sip.RequestEvent;
import javax.sip.message.Request;
import java.io.UnsupportedEncodingException;

/**
 * 响应请求消息抽象类
 * server --req--> client --res--> server
 * @author <a href="mailto:cqmike0315@gmail.com">chenqi</a>
 * @version 1.0
 */
@Getter
@ToString
public abstract class AbstractMessageRequestEvent {

    protected RequestEvent requestEvent;

    public String content;

    public AbstractMessageRequestEvent abstractMessageRequestEvent;
    /**
     * 各子类实现
     */
    public abstract void parse() throws Exception;

    public void init(RequestEvent requestEvent) throws Exception {
        Request request = requestEvent.getRequest();
        this.content = new String(request.getRawContent(),"GBK");
        this.parse();
    }
}
