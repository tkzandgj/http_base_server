package com.cncnc.netty.serverbase.handler;

import com.cncnc.netty.serverbase.http.NotFoundResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.ReferenceCountUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author tukangzheng
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class DispatcherHandler extends GaxChannelInboundHandler{

    private Map<String, ChannelHandlerContext> pathContextMap = new HashMap<>();

    /**
     * 注册对应handler的Context到url
     * @param url 请求url
     * @param ctx 开始handler对应的Context
     */
    public void register(String url, ChannelHandlerContext ctx) {
        pathContextMap.put(url, ctx);
    }

    @Override
    protected void gaxChannelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof FullHttpRequest)) {
            throw new Exception("非法请求！");
        }

        FullHttpRequest httpRequest = (FullHttpRequest)msg;
        //解析url
        String url = httpRequest.uri();
        QueryStringDecoder decoder = new QueryStringDecoder(url);
        String path = decoder.path();
        ChannelHandlerContext entryCtx = pathContextMap.get(path);
        if (entryCtx == null) {
            ctx.writeAndFlush(new NotFoundResponse());
            ctx.close();
            ReferenceCountUtil.release(msg);
        } else {
            entryCtx.fireChannelRead(msg);
        }
    }

}
