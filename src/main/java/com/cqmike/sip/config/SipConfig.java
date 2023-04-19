package com.cqmike.sip.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;

/**
 * sip服务配置
 *
 * @author cqmike
 * @since 1.0.0
 * @return
 */
@Data
@ConfigurationProperties(prefix = "srs.sip", ignoreInvalidFields = true)
@EnableAutoConfiguration
public class SipConfig {

    /**
     * SIP服务名称
     */
    private String name;
    /**
     * # SIP server ID(SIP服务器ID).
     * # 设备端配置编号需要与该值一致，否则无法注册
     */
    @NotEmpty(message = "serial 不能为空")
    private String serial;

    /**
     * # SIP server domain(SIP服务器域)
     */
    @NotEmpty(message = "realm 不能为空")
    private String realm;

    /**
     * SIP 服务器监听网卡IP
     */
    private String ip;

    /**
     * sip监听udp端口
     */
    private Integer port = 55060;

    /**
     * 国标设备注册密码
     */
    private String password;

    /**
     * # 服务端发送ack后，接收回应的超时时间，单位为秒
     * # 如果指定时间没有回应，认为失败
     */
    private Integer ackTimeout = 30;

    /**
     * # 设备心跳维持时间，如果指定时间内(秒）没有接收一个心跳
     * # 认为设备离线
     */
    private Integer keepaliveTimeout = 120;

    /**
     * # 注册之后是否自动给设备端发送invite
     * # on: 是  off 不是，需要通过api控制
     */
    private boolean autoPlay = false;

    /**
     * # 设备将流发送的端口，是否固定
     * # on 发送流到多路复用端口 如9000
     * # off 自动从rtp_mix_port - rtp_max_port 之间的值中
     * # 选一个可以用的端口
     */
    private boolean invitePortFixed = true;

    /**
     * 向设备或下级域查询设备列表的间隔，单位(秒)
     * 默认60秒
     */
    private Integer queryCatalogInterval = 60;

    /**
     * sip日志级别：
     * NONE
     * MESSAGE
     * EXCEPTION
     * DEBUG
     */
    private String traceLevel = "NONE";

    /**
     * 是否开启SIP消息内容的日志
     */
    private boolean logMessageContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTraceLevel() {
        switch (traceLevel) {
            case "NONE":
                return "0";
            case "MESSAGE":
                return "16";
            case "EXCEPTION":
                return "17";
            case "DEBUG":
                return "32";
        }
        return "0";
    }

    public void setTraceLevel(String traceLevel) {
        this.traceLevel = traceLevel;
    }

    public String getLogMessageContent() {
        return logMessageContent ? "true" : "false";
    }

    public void setLogMessageContent(boolean logMessageContent) {
        this.logMessageContent = logMessageContent;
    }
}
