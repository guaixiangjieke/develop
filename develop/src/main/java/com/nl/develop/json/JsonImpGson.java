package com.nl.develop.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by NiuLei on 2018/1/30.
 * json解析 Gson实现
 */

public class JsonImpGson implements JsonFactory {
    private final Gson gson = new Gson();

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        try {
            return gson.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toJson(Object src) {
        return gson.toJson(src);
    }
}
