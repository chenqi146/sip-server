package com.cqmike.sip.core.gb28181.cmd;

import com.cqmike.sip.core.entity.SipDevice;
import com.cqmike.sip.core.gb28181.cmd.dto.InviteHistoryStreamCmd;
import com.cqmike.sip.core.gb28181.cmd.dto.RecordInfoQueryCmd;
import com.cqmike.sip.core.gb28181.cmd.dto.InviteStreamCmd;
import com.cqmike.sip.core.gb28181.event.response.impl.InviteResponseEvent;

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
    void inviteStream(InviteStreamCmd dto);

    /**
     * 录播播放
     *
     * @author cqmike
     * @param dto
     * @since 1.0.0
     * @return
     */
    void inviteHistoryStream(InviteHistoryStreamCmd dto);
    /**
     * 发送bye消息
     *
     * @author cqmike
     * @param inviteResponseEvent
     * @since 1.0.0
     * @return
     */
    void byeStream(InviteResponseEvent inviteResponseEvent);

    /**
     * 录像查询
     *
     * @author cqmike
     * @param cmd
     * @since 1.0.0
     * @return
     */
    void recordInfoQuery(RecordInfoQueryCmd cmd);


}
