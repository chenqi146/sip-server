package com.cqmike.sip.core.gb28181.helper;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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

    /**
     * 邀请推流
     *
     * @param sessionId
     * @param ip
     * @param port
     * @param isTcp
     * @param isActive
     * @param sessionName
     * @param ssrc
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public static String buildRealTimeMediaStreamInviteContent(String sessionId, String ip, int port, boolean isTcp, boolean isActive, String sessionName, String ssrc) {
        StringBuilder content = new StringBuilder(200);
        content.append("v=0\r\n");
        content.append("o=").append(sessionId).append(" 0 0 IN IP4 ").append(ip).append("\r\n");
        content.append("s=").append(sessionName).append("\r\n");
        content.append("c=IN IP4 ").append(ip).append("\r\n");
        content.append("t=0 0\r\n");
        content.append("m=video ").append(port).append(" ").append(isTcp ? "TCP/" : "").append("RTP/AVP 96 98 97\r\n");
        content.append("a=sendrecv\r\n");
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
