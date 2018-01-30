package com.nl.develop.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by NiuLei on 2018/1/30.
 */

public class Tools {
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
}
