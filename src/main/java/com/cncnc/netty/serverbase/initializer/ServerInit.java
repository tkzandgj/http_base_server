package com.cncnc.netty.serverbase.initializer;


import com.cncnc.netty.serverbase.NettyServerContext;
import com.cncnc.netty.serverbase.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * @author Administrator
 * 服务器初始化
 * 需要继承该类，并提供自定义的ChannelInitializer
 */
public abstract class ServerInit {

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 初始化服务器
     */
    public void init(String... args) throws CertificateException, SSLException, InterruptedException{
        final SslContext sslContext;

        if (serverConfig.getHttpConfig().isSsl()){
            SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
            sslContext = SslContextBuilder.forServer(
                    selfSignedCertificate.certificate(), selfSignedCertificate.privateKey()).build();
        } else {
            sslContext = null;
        }

        preInit(serverConfig);

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerChannelInitializer channelInitializer = getChannelInitializer(serverConfig);
            channelInitializer.setSslContext(sslContext);

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInitializer);

            ChannelFuture future = bootstrap.bind(NettyServerContext.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    /**
     * 子类可以通过继承该方法做一些自己的初始化(比如建立redis连接）
     * @param serverConfig
     */
    protected void preInit(ServerConfig serverConfig){

    }

    /**
     * 子类可以通过继承该方法，在连接关闭后做一些处理(比如释放redis连接）
     * @param serverConfig
     */
    protected void afterClose(ServerConfig serverConfig){

    }

    protected abstract ServerChannelInitializer getChannelInitializer(ServerConfig serverConfig);
}
