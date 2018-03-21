package com.nl.develop.mvp;

import android.support.annotation.NonNull;

import com.nl.develop.DevelopConfig;
import com.nl.develop.net.NetCallBack;
import com.nl.develop.net.NetFactory;
import com.nl.develop.utils.LogTools;

import java.util.concurrent.Callable;

/**
 * Created by NiuLei on 2018/1/29.
 * mvp model 基类
 */

public class MvpModel implements MvpContract.IModel {
    public MvpModel() {

    }

    protected NetFactory getNetFactory() {
        DevelopConfig instance = DevelopConfig.getInstance();
        if (instance != null) {
            return instance.getNetFactory();
        }
        return null;
    }

    protected void test(@NonNull NetCallBack netCallBack, @NonNull Callable<String> callable) {
        NetFactory netFactory = getNetFactory();
        if (netFactory != null) {
            netFactory.test(netCallBack, callable);
        } else {
            LogTools.e("test: netFactory == null");
        }

    }
}
