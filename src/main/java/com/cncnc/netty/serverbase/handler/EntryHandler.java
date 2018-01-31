package com.cncnc.netty.serverbase.handler;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author tukangzheng
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class EntryHandler extends GaxChannelInboundHandler{

    private DispatcherHandler dispatcherHandler;

    private String url;

    public void setDispatcherHandler(DispatcherHandler dispatcherHandler) {
        this.dispatcherHandler = dispatcherHandler;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        dispatcherHandler.register(url, ctx);
    }

    @Override
    protected void gaxChannelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // do nothing
    }
}
