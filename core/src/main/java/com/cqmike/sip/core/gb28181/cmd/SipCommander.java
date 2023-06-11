package com.cqmike.sip.core.gb28181.cmd;

import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.dto.InviteStreamDTO;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
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
     * @param sipDevice
     * @return
     * @author cqmike 2
     * @since 1.0.0
     */
    void catalogQuery(SipDevice sipDevice);

    /**
     * 邀请设备推流
     *
     * @param dto
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    void inviteStream(InviteStreamDTO dto);
}
