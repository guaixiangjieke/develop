package com.nl.develop.json;

import java.lang.reflect.Type;

/**
 * Created by NiuLei on 2018/1/30.
 * json解析工厂
 */

public interface JsonFactory {
    /**
     * json 转对象
     *
     * @param json     json字符串x
     * @param classOfT class
     * @param <T>      泛型
     * @return 对象
     */
    <T> T fromJson(String json, Class<T> classOfT);

    <T> T fromJson(String json, Type typeOfT);

    /**
     * 对象转字符串
     *
     * @param src
     * @return json格式字符串
     */
    String toJson(Object src);
}
