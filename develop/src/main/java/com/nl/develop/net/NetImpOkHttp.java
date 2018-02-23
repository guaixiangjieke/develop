package com.nl.develop.net;

import android.support.annotation.NonNull;
import android.support.v4.BuildConfig;
import android.support.v4.util.ArrayMap;

import com.nl.develop.utils.HttpTools;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by NiuLei on 2018/2/23.
 * 网络请求OkHttp实现
 */

public class NetImpOkHttp implements NetFactory {
    private final OkHttpClient okHttpClient;

    public NetImpOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {//添加log 拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        okHttpClient = builder.build();
    }

    @Override
    public IRequest post(@NonNull String url, ArrayMap<String, String> params, @NonNull NetCallBack netCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            final int size = params.size();
            for (int i = 0; i < size; i++) {
                builder.add(params.keyAt(i), params.valueAt(i));
            }
        }
        final Request request = generateRequestBuilder(url).post(builder.build()).build();
        return exe(netCallBack, request);
    }

    @Override
    public IRequest get(@NonNull String url, @NonNull NetCallBack netCallBack) {
        final Request request = generateRequestBuilder(url).get().build();
        return exe(netCallBack, request);
    }

    @Override
    public IRequest get(@NonNull String url, ArrayMap<String, String> params, @NonNull NetCallBack netCallBack) {
        if (params != null && !params.isEmpty()) {
            url += HttpTools.generatePath(params);
        }
        final Request request = generateRequestBuilder(url).get().build();
        return exe(netCallBack, request);
    }

    /**
     * 执行
     *
     * @param netCallBack 回调
     * @param request     okhtpp请求
     * @return
     */
    @NonNull
    private IRequest exe(@NonNull NetCallBack netCallBack, Request request) {
        Call call = okHttpClient.newCall(request);
        final OkHttpCallBackAdapter okHttpCallBackAdapter = new OkHttpCallBackAdapter(netCallBack);
        call.enqueue(okHttpCallBackAdapter);
        return new RequestCallAdapter(call);
    }

    /**
     * 根据路径生成 Request.Builder对象
     *
     * @param url 路径
     * @return Request.Builder对象
     */
    private Request.Builder generateRequestBuilder(String url) {
        final Request.Builder builder = new Request.Builder();
        return builder.url(url);
    }

    /**
     * 适配okHttp网络请求取消
     */
    class RequestCallAdapter implements IRequest {
        private Call call;

        public RequestCallAdapter(Call call) {
            this.call = call;
        }

        @Override
        public void cancel() {
            if (call != null) {
                call.cancel();
            }
        }
    }
}
