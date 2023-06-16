package com.cqmike.sip.core.gb28181.event.request;

import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.ISipProcessor;

import javax.sip.RequestEvent;

/**
 * sip请求处理接口
 *
 * @author cqmike
 **/
public interface ISipRequestProcessor extends ISipProcessor {

    void processEvent(RequestEvent event);

}
