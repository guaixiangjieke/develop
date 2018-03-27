package com.nl.develop.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by NiuLei on 2018/1/30.
 */

public class Tools {

    /**
     * 最大线程数量
     */
    public static final int MAX_NUM_THREADS = 5;
    /**
     * 线程池
     */
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(MAX_NUM_THREADS, MAX_NUM_THREADS,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(MAX_NUM_THREADS * 2));

    /**
     * 点击输入框外部 隐藏输入法
     *
     * @param activity
     * @param inputMethodManager
     * @param ev
     */
    public static void hideSoftKeyboardOutsideOfFocus(Activity activity, InputMethodManager inputMethodManager, MotionEvent ev) {
        if (activity == null) {
            return;
        }
        if (inputMethodManager == null) {
            return;
        }
        if (ev == null) {
            return;
        }
        if (inputMethodManager.isActive()) {
            final View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                final IBinder windowToken = currentFocus.getWindowToken();
                if (windowToken != null) {
                    final Rect globalVisibleRect = new Rect();
                    currentFocus.getGlobalVisibleRect(globalVisibleRect);
                    if (!globalVisibleRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                        inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
                    }
                }
            }
        }
    }

    /**
     * 手机号正则
     */
    //     移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
//     联通：130、131、132、152、155、156、185、186
//     电信：133、153、180、189、（1349卫通）
    public static final Pattern phonePattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    /**
     * 验证是否为手机号码格式
     *
     * @param PhoneNo
     * @return
     */
    public static boolean isPhoneFomart(String PhoneNo) {
        Matcher m = phonePattern.matcher(PhoneNo);
        return m == null ? false : m.matches();

    }
}
