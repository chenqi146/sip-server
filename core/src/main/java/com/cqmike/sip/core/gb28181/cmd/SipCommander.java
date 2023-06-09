package com.cqmike.sip.core.gb28181.cmd;

import com.cqmike.sip.core.entity.SipDevice;

import javax.sip.InvalidArgumentException;
import java.text.ParseException;

/**
 * sip 命令下发  异步
 *
 * @author cqmike
 **/
public interface SipCommander {

    /**
     * 查询设备信息
     *
     * @author cqmike 2
     * @param sipDevice
     * @since 1.0.0
     * @return
     */
    void catalogQuery(SipDevice sipDevice) throws InvalidArgumentException, ParseException;
}
