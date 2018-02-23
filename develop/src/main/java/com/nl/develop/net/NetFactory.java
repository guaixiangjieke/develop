package com.nl.develop.net;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

/**
 * Created by NiuLei on 2018/1/30.
 * 网络请求工厂
 */

public interface NetFactory {
    /**
     * post请求
     *
     * @param url         路径
     * @param params      参数
     * @param netCallBack 回调
     */
    IRequest post(@NonNull String url, ArrayMap<String, String> params, @NonNull NetCallBack netCallBack);

    /**
     * get请求
     *
     * @param url         路径
     * @param netCallBack 回调
     */
    IRequest get(@NonNull String url, @NonNull NetCallBack netCallBack);

    /**
     * get请求
     *
     * @param url         路径
     * @param params      参数
     * @param netCallBack 回调
     */
    IRequest get(@NonNull String url, ArrayMap<String, String> params, @NonNull NetCallBack netCallBack);

}
