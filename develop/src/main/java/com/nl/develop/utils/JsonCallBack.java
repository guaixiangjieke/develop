package com.nl.develop.utils;

import com.nl.develop.DevelopConfig;
import com.nl.develop.net.NetCallBack;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by NiuLei on 2018/4/2.
 */

public abstract class JsonCallBack<T> implements NetCallBack {

    @Override
    public void onStart() {

    }

    @Override
    public void onFailure(IOException e) {

    }

    @Override
    public void onResponse(String response) {
        T result = null;
        try {
            final DevelopConfig instance = DevelopConfig.getInstance();
            if (instance != null) {
                Type type = TypeToken.getSuperclassTypeParameter(getClass());
                result = instance.getJsonFactory().fromJson(response, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            onResponse(result);
        }
    }

    public abstract void onResponse(T result);

    @Override
    public void onFinish() {

    }

    @Override
    public void onResponse(String response, int code, String message) {

    }
}
