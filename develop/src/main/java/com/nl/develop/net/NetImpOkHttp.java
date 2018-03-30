package com.nl.develop.net;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.nl.develop.DevelopConfig;
import com.nl.develop.json.JsonFactory;
import com.nl.develop.utils.HttpTools;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by NiuLei on 2018/2/23.
 * 网络请求OkHttp实现
 */

public class NetImpOkHttp extends BasicNetFactory {
    private final OkHttpClient okHttpClient;

    public NetImpOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /*拦截器添加请求头*/
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request.Builder builder = chain.request().newBuilder();
                if (onRequestListener != null) {
                    ArrayMap<String, String> stringStringArrayMap = onRequestListener.requestWithHeads();
                    if (stringStringArrayMap != null && !stringStringArrayMap.isEmpty()) {
                        for (int i = 0; i < stringStringArrayMap.size(); i++) {
                            builder.addHeader(stringStringArrayMap.keyAt(i), stringStringArrayMap.valueAt(i));
                        }
                    }
                }
                final Request request = builder.build();
                return chain.proceed(request);
            }
        });
        builder.addInterceptor(httpLoggingInterceptor);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
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

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public IRequest post(@NonNull String url, @NonNull Object obj, @NonNull NetCallBack netCallBack) {
        final JsonFactory jsonFactory = DevelopConfig.getInstance().getJsonFactory();
        String content = "";
        if (jsonFactory != null) {
            content = jsonFactory.toJson(obj);
        }
        RequestBody requestBody = RequestBody.create(JSON, content);
        final Request request = generateRequestBuilder(url).post(requestBody).build();
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
        final OkHttpCallBackAdapter okHttpCallBackAdapter = new OkHttpCallBackAdapter(newProxy(netCallBack));
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
    final class RequestCallAdapter implements IRequest {
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

    /**
     * Created by NiuLei on 2018/2/23.
     * 适配okHttp回调
     */

    final class OkHttpCallBackAdapter implements Callback {
        private final NetCallBack netCallBack;

        public OkHttpCallBackAdapter(@NonNull NetCallBack netCallBack) {
            this.netCallBack = netCallBack;
            this.netCallBack.onStart();
        }

        @Override
        public void onFailure(Call call, IOException e) {
            try {
                netCallBack.onFailure(e);
            } finally {
                netCallBack.onFinish();
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responseString = null;
            ResponseBody body = response.body();
            if (body != null) {
                responseString = body.string();
            }
            try {
                netCallBack.onResponse(responseString, response.code(), response.message());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                netCallBack.onResponse(responseString);
            } finally {
                netCallBack.onFinish();
            }
        }
    }
}
