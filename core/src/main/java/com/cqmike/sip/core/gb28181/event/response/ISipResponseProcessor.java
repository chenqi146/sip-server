package com.cqmike.sip.core.gb28181.event.response;

import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.ISipProcessor;

import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;

/**
 * sip响应处理接口
 *
 * @author cqmike
 **/
public interface ISipResponseProcessor extends ISipProcessor {

    void processEvent(ResponseEvent event);

}
