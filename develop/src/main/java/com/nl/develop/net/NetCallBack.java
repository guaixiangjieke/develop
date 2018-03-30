package com.nl.develop.net;

import java.io.IOException;

/**
 * Created by NiuLei on 2018/2/23.
 * 网络请求回调
 */

public interface NetCallBack {
    /**
     * 开始请求
     */
    void onStart();

    /**
     * 请求失败
     *
     * @param e
     */
    void onFailure(final IOException e);

    /**
     * 请求响应返回
     *
     * @param response
     */
    void onResponse(final String response);

    /**
     * 请求完成
     */
    void onFinish();

    /**
     * 请求响应返回
     *
     * @param response
     */
    void onResponse(final String response, int code, String message);

}
