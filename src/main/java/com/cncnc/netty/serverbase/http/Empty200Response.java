package com.cncnc.netty.serverbase.http;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author tukangzheng
 */
public final class Empty200Response extends DefaultFullHttpResponse {

    public Empty200Response() {
        super(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        this.headers().set(HttpHeaderNames.CONTENT_LENGTH, 0);
    }
}