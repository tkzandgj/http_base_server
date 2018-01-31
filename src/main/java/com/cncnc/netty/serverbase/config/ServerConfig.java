package com.cncnc.netty.serverbase.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tukangzheng
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "server")
public class ServerConfig {

    private Integer port;

    private String encode = "UTF-8";

    @Autowired
    private ServerHttpConfig http;

    public ServerHttpConfig getHttp() {
        return http;
    }

    public void setHttp(ServerHttpConfig http) {
        this.http = http;
    }

    public ServerHttpConfig getHttpConfig() {
        return http;
    }

    public void setHttpConfig(ServerHttpConfig httpConfig) {
        this.http = httpConfig;
    }
}
