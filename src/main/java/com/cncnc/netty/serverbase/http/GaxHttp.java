package com.cncnc.netty.serverbase.http;

import com.cncnc.netty.serverbase.constant.Constant;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;

/**
 * @author tukangzheng
 */
public class GaxHttp {

    /**
     * Get client ip from header
     *
     * @param headers HttpHeaders
     * @return client ip or null if not found
     */
    public static String getClientIP(HttpHeaders headers) {
        String ips = headers.get(Constant.HTTP_X_FORWARDED_FOR);
        if (ips != null) {
            String ip = ips.split(",")[0];
            if (ip != null) {
                return ip;
            }
        }
        return null;
    }

    /**
     * set response header info
     * @param response
     */
    public static void setResponseHeader(FullHttpResponse response) {
        response.headers().set("Access-Control-Allow-Origin", "*");
        response.headers().set("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.headers().set("Access-Control-Request-Headers", "Content-Type");
        response.headers().set("Access-Control-Allow-Headers", "Content-Type");
        response.headers().set("Access-Control-Allow-Credentials", true);
    }
}
