package com.nl.develop;

import android.content.Context;

import com.nl.develop.json.JsonFactory;
import com.nl.develop.json.JsonImpGson;
import com.nl.develop.net.NetFactory;
import com.nl.develop.net.NetImpOkHttp;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by NiuLei on 2018/2/23.
 * 配置
 */

public class DevelopConfig {
    /**
     * 单例模式 双重检验锁
     * 懒加载
     * 线程安全
     */

    private static final Object lock = new Object();
    private static DevelopConfig INSTANCE = null;
    private Context context;

    /**
     * 是否初始化
     */
    private static final AtomicBoolean isInit = new AtomicBoolean(false);

    private DevelopConfig(Context context) {
        this.context = context;
        isInit.set(true);
    }

    public static DevelopConfig getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null) {
                    INSTANCE = new DevelopConfig(context);
                }
            }
        }
        return INSTANCE;
    }

    public static DevelopConfig getInstance() {
        if (isInit.get()) {
            return INSTANCE;
        } else {
            return null;
        }
    }

    private NetFactory netFactory = new NetImpOkHttp();
    //        NetFactory netFactory = new NetImpNative();
    private JsonFactory jsonFactory = new JsonImpGson();

    private boolean isDebug = false;

    public NetFactory getNetFactory() {
        return netFactory;
    }

    public void setNetFactory(NetFactory netFactory) {
        this.netFactory = netFactory;
    }

    public JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    public void setJsonFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }
}
