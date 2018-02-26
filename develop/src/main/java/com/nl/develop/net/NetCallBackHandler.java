package com.nl.develop.net;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by NiuLei on 2018/2/26.
 * 回调代理  main线程
 */

public class NetCallBackHandler implements InvocationHandler {
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
