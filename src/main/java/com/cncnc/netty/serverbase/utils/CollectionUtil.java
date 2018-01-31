package com.cncnc.netty.serverbase.utils;

import java.util.*;

/**
 * @author tukangzheng
 */
public class CollectionUtil {

    /**
     * 判断List集合是否为空，第一个元素是否是空串
     * @param list
     * @return
     */
    public static boolean isEmpty(Collection<?> list) {
        return (list == null || list.isEmpty());
    }

    /**
     * 取交集
     *
     * @param ls1
     * @param ls2
     * @return
     */
    public static List intersect(List ls1, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls1.size()]));
        Collections.copy(list, ls1);
        list.retainAll(ls2);
        return list;
    }

    /**
     * 取并集
     *
     * @param ls1
     * @param ls2
     * @return
     */
    public static List union(List ls1, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls1.size()]));
        Collections.copy(list, ls1);
        list.addAll(ls2);
        return list;
    }

    /**
     * 取补集
     *
     * @param ls1
     * @param ls2
     * @return
     */
    public static List diff(List ls1, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls1.size()]));
        Collections.copy(list, ls1);
        list.removeAll(ls2);
        return list;
    }
}
