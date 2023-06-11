package com.cqmike.sip.core.gb28181.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

/**
 * rtc服务配置
 *
 * @author cqmike
 * @return
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "iot.rtc", ignoreInvalidFields = true)
@EnableAutoConfiguration
@Configuration
public class RtcConfig {

    /**
     * 收流端口
     */
    private Integer muxPort;

}
