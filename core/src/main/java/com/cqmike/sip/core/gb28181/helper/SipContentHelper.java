package com.cqmike.sip.core.gb28181.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * SipContentHelper
 *
 * @author cqmike
 **/
public final class SipContentHelper {

    /**
     * 查询设备信息
     *
     * @param deviceId
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public static String buildCatalogContentXml(String deviceId) {
        StringBuilder catalogXml = new StringBuilder(200);
        catalogXml.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\r\n");
        catalogXml.append("<Query>\r\n");
        catalogXml.append("<CmdType>Catalog</CmdType>\r\n");
        catalogXml.append("<SN>")
                .append("1")
                .append("</SN>\r\n");
        catalogXml.append("<DeviceID>")
                .append(deviceId)
                .append("</DeviceID>\r\n");
        catalogXml.append("</Query>\r\n");
        return catalogXml.toString();
//
//        Document document = DocumentHelper.createDocument();
//        Element query = document.addElement("Query");
//        query.addElement("CmdType").addText("Catalog");
//        query.addElement("SN").addText("1");
//        query.addElement("DeviceID").addText(deviceId);
//        return document.asXML();
    }

    public static long getTimeStamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String buildHistoryTimeMediaStreamInviteContent(String channelId, String ip, int rtpPort, String ssrc,
                                                                  LocalDateTime startTime, LocalDateTime endTime) {
        return buildHistoryTimeMediaStreamInviteContent(channelId, ip, rtpPort, ssrc, false, false, startTime, endTime);
    }

    public static String buildHistoryTimeMediaStreamInviteContent(String channelId, String ip, int rtpPort,
                                                                  String ssrc, boolean isTcp, boolean isActive,
                                                                  LocalDateTime startTime, LocalDateTime endTime) {
        StringBuilder content = new StringBuilder(200);
        content.append("v=0\r\n");
        content.append("o=").append(channelId).append(" 0 0 IN IP4 ").append(ip).append("\r\n");
        content.append("s=Playback\r\n");
        content.append("c=IN IP4 ").append(ip).append("\r\n");
        content.append("t=").append(getTimeStamp(startTime)).append(" ").append(getTimeStamp(endTime)).append("\r\n");
        content.append("m=video ").append(rtpPort).append(" ").append(isTcp ? "TCP/" : "").append("RTP/AVP 96 98 97\r\n");
        content.append("a=recvonly\r\n");
        content.append("a=rtpmap:96 PS/90000\r\n");
        content.append("a=rtpmap:98 H264/90000\r\n");
        content.append("a=rtpmap:97 MPEG4/90000\r\n");
        if (isTcp) {
            content.append("a=setup:").append(isActive ? "active" : "passive").append("\r\n");
            content.append("a=connection:new\r\n");
        }
        content.append("y=").append(ssrc).append("\r\n");
        return content.toString();

    }

    public static String buildRealTimeMediaStreamInviteContent(String channelId, String ip, int rtpPort, String ssrc) {
        return buildRealTimeMediaStreamInviteContent(channelId, ip, rtpPort, false, false, ssrc);
    }


    /**
     * 邀请推流
     *
     * @param channelId 国标通道id
     * @param ip        媒体服务器ip
     * @param rtpPort   媒体端口
     * @param isTcp     是否为tcp
     * @param isActive
     * @param ssrc      流标识
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public static String buildRealTimeMediaStreamInviteContent(String channelId, String ip, int rtpPort, boolean isTcp, boolean isActive, String ssrc) {
        StringBuilder content = new StringBuilder(200);
        content.append("v=0\r\n");
        content.append("o=").append(channelId).append(" 0 0 IN IP4 ").append(ip).append("\r\n");
        content.append("s=Play\r\n");
        content.append("c=IN IP4 ").append(ip).append("\r\n");
        content.append("t=0 0\r\n");
        content.append("m=video ").append(rtpPort).append(" ").append(isTcp ? "TCP/" : "").append("RTP/AVP 96 98 97\r\n");
        content.append("a=recvonly\r\n");
        content.append("a=rtpmap:96 PS/90000\r\n");
        content.append("a=rtpmap:98 H264/90000\r\n");
        content.append("a=rtpmap:97 MPEG4/90000\r\n");
        if (isTcp) {
            content.append("a=setup:").append(isActive ? "active" : "passive").append("\r\n");
            content.append("a=connection:new\r\n");
        }
        content.append("y=").append(ssrc).append("\r\n");
        return content.toString();
    }

}
