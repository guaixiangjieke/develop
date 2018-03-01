package com.nl.develop.net;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.nl.develop.utils.HttpTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Future;

import static com.nl.develop.utils.Tools.THREAD_POOL_EXECUTOR;

/**
 * Created by NiuLei on 2018/1/30.
 * logo打印
 * 超时设置
 * 头设置  公共  个体
 * <p>
 * 响应时候 头信息 url 等等信息
 */

public class NetImpNative extends BasicNetFactory {
    class Method {
        public static final String POST = "POST";
        public static final String GET = "GET";
    }

    /**
     * 编码
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 默认
     */
    private static final int DEF_CONNECT_TIMEOUT = 5000;
    /**
     * 默认
     */
    private static final int DEF_READ_TIMEOUT = 5000;

    /**
     * 链接超时
     */
    private int connectTimeout = DEF_CONNECT_TIMEOUT;
    /**
     * 读取超时
     */
    private int readTimeout = DEF_READ_TIMEOUT;

    //logo
    public NetImpNative() {

    }

    @Override
    public IRequest post(@NonNull String url, ArrayMap<String, String> params, @NonNull NetCallBack netCallBack) {
        return exe(url, Method.POST, params, netCallBack);
    }

    @Override
    public IRequest get(@NonNull String url, @NonNull NetCallBack netCallBack) {
        return exe(url, Method.GET, null, netCallBack);
    }

    @Override
    public IRequest get(@NonNull String url, ArrayMap<String, String> params, @NonNull NetCallBack netCallBack) {
        return exe(url, Method.GET, params, netCallBack);
    }

    /**
     * 嵌套线程请求
     *
     * @param url
     * @param method
     * @param params
     * @param netCallBack
     */
    private IRequest exe(@NonNull final String url, @NonNull final String method, final ArrayMap<String, String> params, @NonNull final NetCallBack netCallBack) {
        if (!THREAD_POOL_EXECUTOR.isShutdown()) {
            final Future<?> submit = THREAD_POOL_EXECUTOR.submit(new Runnable() {
                @Override
                public void run() {
                    request(url, method, params, newProxy(netCallBack));
                }
            });
            return new RequestFutureAdapter(submit);
        }
        return null;
    }

    /**
     * 请求
     *
     * @param url         路径
     * @param method      请求方法 {@link Method}
     * @param params      请求参数
     * @param netCallBack 回调
     */
    private void request(@NonNull String url, @NonNull String method, ArrayMap<String, String> params, @NonNull NetCallBack netCallBack) {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        String paramsPath = null;
        //根据请求参数生成url格式参数
        if (params != null && !params.isEmpty()) {
            paramsPath = HttpTools.generatePath(params);
        }
        //如果是get请求 参数拼接到路径后面
        if (Method.GET.equals(method) && paramsPath != null) {
            url += paramsPath;
        }
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setDoInput(true);
            netCallBack.onStart();
            //如果是post请求 参数放在请求体内
            if (Method.POST.equals(method) && paramsPath != null) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(paramsPath.getBytes(CHARSET_NAME));
                outputStream.flush();
            }
            final int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                final StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                netCallBack.onResponse(stringBuilder.toString());
            } else {
                netCallBack.onFailure(new IOException("responseCode != 200"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            netCallBack.onFailure(e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignore) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ignore) {
                }

            }
            if (connection != null) {
                connection.disconnect();
            }
            netCallBack.onFinish();
        }
    }

    /**
     * Created by NiuLei on 2018/2/23.
     * 适配线程池取消
     */

    class RequestFutureAdapter implements IRequest {
        private Future future;

        RequestFutureAdapter(Future future) {
            this.future = future;
        }

        @Override
        public void cancel() {
            if (future != null) {
                future.cancel(true);
            }
        }
    }
}
