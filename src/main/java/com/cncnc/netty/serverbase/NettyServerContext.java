package com.cncnc.netty.serverbase;


import com.cncnc.netty.serverbase.config.ServerConfig;

/**
 * @author Administrator
 * 对外提供接口，用来访问服务器的配置等信息
 */
public class NettyServerContext {

    private static ServerConfig serverConfig;

    public static void init(ServerConfig pServerConfig){
        serverConfig = pServerConfig;
    }

    public static int getPort() {
        return serverConfig.getPort();
    }

    public static boolean isSslEnable() {
        return serverConfig.getHttpConfig().isSsl();
    }

    public static int getMaxRequestLength() {
        return serverConfig.getHttpConfig().getMaxRequestLength();
    }

    public static String getEncode() {
        return serverConfig.getEncode();
    }
}
