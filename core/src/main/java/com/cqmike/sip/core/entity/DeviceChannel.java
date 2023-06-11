package com.cqmike.sip.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * 设备通道信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeviceChannel implements Serializable {

    private static final long serialVersionUID = 1529923940089910630L;

    /**
     * <pre>
     *
     * </pre>
     */
    private String channelId;

    /**
     * <pre>
     * 通道名称
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * 设备厂家
     * </pre>
     */
    private String manufacturer;

    /**
     * <pre>
     * 设备型号
     * </pre>
     */
    private String model;

    /**
     * <pre>
     * 设备归属
     * </pre>
     */
    private String owner;

    /**
     * <pre>
     * 行政区号
     * </pre>
     */
    private String civilCode;

    /**
     * <pre>
     * 警区
     * </pre>
     */
    private String block;

    /**
     * <pre>
     * 安装地址
     * </pre>
     */
    private String Address;

    /**
     * <pre>
     *  当为设备时, 是否有子设备, 1-有,0-没有
     * </pre>
     */
    private Integer parental;

    /**
     * <pre>
     *  父设备/区域/系统ID
     * </pre>
     */
    private String parentId;

    /**
     * <pre>
     *  安全通道 0否 1是
     * </pre>
     */
    private Integer safetyWay;

    /**
     * <pre>
     * 注册方式, 缺省为1, 1-IETF RFC3261, 2-基于口令的双向认证, 3-基于数字证书的双向认证
     * 允许值: 1, 2, 3
     * </pre>
     */
    private String registerWay;

    /**
     * <pre>
     *
     * </pre>
     */
    private String certNum;

    /**
     * <pre>
     * SSL安全认证 0否 1是
     * </pre>
     */
    private Integer certifiable;

    /**
     * <pre>
     *
     * </pre>
     */
    private String errCode;

    /**
     * <pre>
     *
     * </pre>
     */
    private String endTime;

    /**
     * <pre>
     * 保密属性, 缺省为0, 0-不涉密, 1-涉密
     * 允许值: 0, 1
     * </pre>
     */
    private Integer secrecy;

    /**
     * <pre>
     * 设备/区域/系统IP地址
     * </pre>
     */
    private String ipAddress;

    /**
     * <pre>
     * 设备/区域/系统端口
     * </pre>
     */
    private Integer port;

    /**
     * <pre>
     * 通道密码
     * </pre>
     */
    private String password;

    /**
     * <pre>
     *  在线状态
     * </pre>
     */
    private String status;

    /**
     * <pre>
     * 经度
     * </pre>
     */
    private float Longitude;

    /**
     * <pre>
     *  纬度
     * </pre>
     */
    private float Latitude;

}
