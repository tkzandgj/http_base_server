package com.cncnc.netty.serverbase.http;

import com.cncnc.netty.serverbase.constant.Constant;
import com.cncnc.netty.serverbase.utils.Hex;
import com.cncnc.netty.serverbase.utils.StringUtil;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Set;

/**
 * @author tukangzheng
 */
public class GaxUserId {

    /**
     * 从cookie里获取GaxUserId
     *
     * @param headers http的headers
     * @return GaxUserId，没有返回null
     */
    public static String getGaxUserId(HttpHeaders headers) {
        String gaxUID = null;

        String cookieStr = headers.get(HttpHeaderNames.COOKIE);
        if (!StringUtil.isEmpty(cookieStr)) {
            Set<Cookie> cookies = ServerCookieDecoder.LAX.decode(cookieStr);
            if (!cookies.isEmpty()) {
                for (Cookie cookie : cookies) {
                    if (Constant.COOKIE_GAX_UID_NAME.equals(cookie.name())) {
                        gaxUID = cookie.value();
                        break;
                    }
                }
            }
        }
        return gaxUID;
    }

    /**
     * 从cookie里获取CookieName的值
     *
     * @param headers http的headers
     * @param cookieName 需要获取的cookie的name
     * @return str，没有返回null
     */
    public static String getCookieValueByCookieName(HttpHeaders headers, String cookieName) {
        String str = null;

        String cookieStr = headers.get(HttpHeaderNames.COOKIE);
        if (!StringUtil.isEmpty(cookieStr)) {
            Set<Cookie> cookies = ServerCookieDecoder.LAX.decode(cookieStr);
            if (!cookies.isEmpty()) {
                for (Cookie cookie : cookies) {
                    if (cookieName.equals(cookie.name())) {
                        str = cookie.value();
                        break;
                    }
                }
            }
        }
        return str;
    }

    /**
     * 新建一个GaxUserId
     *
     * @param ua userAgent
     * @return GaxUserId
     */
    public static String createGaxUserId(String ua) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String sourceString = ua + (new Date()).getTime();
        return Hex.encodeHexString(messageDigest.digest(sourceString.getBytes()));
    }

    /**
     * 设置gaxUID的cookie
     *
     * @param httpHeaders http头
     * @param gaxUID      gaxUID
     */
    public static void setGaxUserId(HttpHeaders httpHeaders, String gaxUID) {
        Cookie cookie = new DefaultCookie(Constant.COOKIE_GAX_UID_NAME, gaxUID);
        cookie.setDomain(Constant.COOKIE_DOMAIN);
        cookie.setMaxAge(Constant.COOKIE_MAXAGE);
        String cookieStr = ServerCookieEncoder.LAX.encode(cookie);
        httpHeaders.add(HttpHeaderNames.SET_COOKIE, cookieStr);
    }
}
