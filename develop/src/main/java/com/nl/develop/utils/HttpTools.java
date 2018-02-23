package com.nl.develop.utils;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

/**
 * Created by NiuLei on 2018/2/23.
 */

public class HttpTools {
    /**
     * 根据参数生成路径
     * rg : key1 value1
     * key2 value2
     * ?key1=value1&key2=value2
     *
     * @param params 集合类型请求参数
     * @return 字符串类型请求参数
     */
    @NonNull
    public static String generatePath(ArrayMap<String, String> params) {
        final int size = params.size();
        final StringBuilder stringBuilder = new StringBuilder("?");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(params.keyAt(i));
            stringBuilder.append("=");
            stringBuilder.append(params.valueAt(i));
            stringBuilder.append("&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
