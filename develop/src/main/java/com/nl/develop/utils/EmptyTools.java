package com.nl.develop.utils;

import java.util.List;

/**
 * Created by NiuLei on 2018/1/30.
 */

public class EmptyTools {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        str = str.trim();
        return str.length() == 0 || "null".equals(str);
    }

    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        if (list == null) {
            return true;
        }
        return list.isEmpty();
    }

    /**
     * 判断数组是否为空
     *
     * @param arrays
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T[] arrays) {
        if (arrays == null) {
            return true;
        }
        return arrays.length == 0;
    }
}
