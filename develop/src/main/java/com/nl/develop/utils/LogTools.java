package com.nl.develop.utils;


import android.util.Log;

import com.nl.develop.BuildConfig;
import com.nl.develop.DevelopConfig;

import static com.nl.develop.BuildConfig.TAG;

/**
 * Created by NiuLei on 2018/2/23.
 * log工具类
 */

public class LogTools {
    public static void log(int level, String msg) {
        if (!DevelopConfig.getInstance().isDebug()) {
            //log开关
            return;
        }

        if (!BuildConfig.DEBUG && Log.INFO == level) {
            //线上过滤掉INFO等级信息
            return;
        }
        switch (level) {
            case Log.VERBOSE:
            case Log.DEBUG:
                Log.d(TAG, msg);
                break;
            case Log.INFO:
                Log.i(TAG, msg);
                break;
            case Log.WARN:
                Log.w(TAG, msg);
                break;
            case Log.ERROR:
                Log.e(TAG, msg);
                break;
        }
    }
}
