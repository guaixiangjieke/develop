package com.nl.develop.net;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.nl.develop.utils.Tools;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

/**
 * Created by NiuLei on 2018/3/1.
 * 网络工厂基类
 */

public abstract class BasicNetFactory implements NetFactory {
    private final Handler handler = new Handler(Looper.getMainLooper());
    protected OnRequestListener onRequestListener;

    /**
     * Created by NiuLei on 2018/2/26.
     * 回调代理  main线程
     */

    static class NetCallBackHandler implements InvocationHandler {
        private final Handler handler;
        private final NetCallBack realCallBack;

        NetCallBackHandler(Handler handler, NetCallBack realCallBack) {
            this.handler = handler;
            this.realCallBack = realCallBack;
        }

        @Override
        public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        method.invoke(realCallBack, args);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }

        public static NetCallBack newProxy(@NonNull Handler handler, @NonNull NetCallBack realCallBack) {
            final Class<NetCallBack> netCallBackClass = NetCallBack.class;
            return (NetCallBack) Proxy.newProxyInstance(netCallBackClass.getClassLoader(), new Class[]{netCallBackClass}, new NetCallBackHandler(handler, realCallBack));
        }
    }

    protected NetCallBack newProxy(NetCallBack netCallBack) {
        return NetCallBackHandler.newProxy(handler, netCallBack);
    }

    @Override
    public IRequest post(@NonNull String url, Object obj, @NonNull NetCallBack netCallBack) {
        return null;
    }

    @Override
    public void test(@NonNull NetCallBack netCallBack, final Callable<String> callable) {
        final NetCallBack netCallBack1 = newProxy(netCallBack);
        Tools.THREAD_POOL_EXECUTOR.submit(new Runnable() {
            @Override
            public void run() {
                netCallBack1.onStart();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    String call = callable.call();
                    netCallBack1.onResponse(call);
                } catch (Exception e) {
                    e.printStackTrace();
                    netCallBack1.onFailure(new IOException(e.getMessage()));
                } finally {
                    netCallBack1.onFinish();
                }
            }
        });

    }

    @Override
    public void setOnRequestListener(OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
    }
}
