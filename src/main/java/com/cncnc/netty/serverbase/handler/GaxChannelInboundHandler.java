package com.cncnc.netty.serverbase.handler;

import com.cncnc.netty.serverbase.http.Empty200Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author tukangzheng
 */
public abstract class GaxChannelInboundHandler extends ChannelInboundHandlerAdapter{

    private static final Logger logger = LoggerFactory.getLogger(GaxChannelInboundHandler.class);

    private static final Logger ioexceptionLogger = LoggerFactory.getLogger("ioexception");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            gaxChannelRead(ctx, msg);
        } catch (Exception e) {
            ReferenceCountUtil.release(msg);
            throw e;
        } finally {

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            ioexceptionLogger.error("error", cause);
        } else {
            logger.error("remote address:{}", ctx.channel().remoteAddress().toString());
            logger.error("handle exception: ", cause);
        }
        FullHttpResponse response = new Empty200Response();
        ctx.writeAndFlush(response);
    }

    protected abstract void gaxChannelRead(ChannelHandlerContext ctx, Object msg) throws Exception;

}
