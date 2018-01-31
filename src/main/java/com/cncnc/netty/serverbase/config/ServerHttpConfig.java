package com.cncnc.netty.serverbase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tukangzheng
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "server.http")
public class ServerHttpConfig {

    private boolean ssl = false;

    private int maxRequestLength = 1048576;
}
