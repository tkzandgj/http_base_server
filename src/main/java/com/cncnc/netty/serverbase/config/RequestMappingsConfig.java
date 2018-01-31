package com.cncnc.netty.serverbase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author tukangzheng
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "requestMappingConfig")
public class RequestMappingsConfig {

    private List<RequestMapping> requestMappings;

}
