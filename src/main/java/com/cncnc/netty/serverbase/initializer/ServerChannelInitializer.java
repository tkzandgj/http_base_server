package com.cncnc.netty.serverbase.initializer;

import com.cncnc.netty.serverbase.NettyServerContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Administrator
 */
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Autowired
    private HandlerManager handlerManager;

    private SslContext sslContext;

    public void setSslContext(SslContext sslContext){
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        if (sslContext != null){
            p.addLast(sslContext.newHandler(socketChannel.alloc()));
        }
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(NettyServerContext.getMaxRequestLength()));

        List<ChannelHandler> handlers = getHandlers();
        if (handlers != null && handlers.size() > 0) {
            for (ChannelHandler channelHandler : handlers) {
                p.addLast(channelHandler);
            }
        }
    }

    protected List<ChannelHandler> getHandlers() throws ClassNotFoundException {
        return handlerManager.getHandles();
    }
}
