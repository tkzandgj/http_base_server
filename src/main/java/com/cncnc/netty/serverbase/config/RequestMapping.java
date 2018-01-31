package com.cncnc.netty.serverbase.config;

import lombok.Data;

import java.util.List;

/**
 * @author tukangzheng
 */
@Data
public class RequestMapping {

    private String url;

    private List<String> handlers;
}
