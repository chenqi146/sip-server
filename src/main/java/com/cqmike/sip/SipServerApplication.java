package com.cqmike.sip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author <a href="mailto:cqmike0315@gmail.com">chenqi</a>
 * @version 1.0
 */
@SpringBootApplication
@EnableConfigurationProperties
public class SipServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SipServerApplication.class, args);
    }
}
