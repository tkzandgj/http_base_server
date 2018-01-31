package com.cncnc.netty.serverbase.utils;

import java.util.List;

/**
 * @author tukangzheng
 */
public class StringUtil {

    public static String nullToEmpty(String obj) {
        return obj == null ? "" : obj;
    }

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static <T> boolean isEmptyList(List<T> list) {
        return list == null || list.size() < 1;
    }

}
