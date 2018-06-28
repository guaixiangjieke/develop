package com.nl.develop.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by NiuLei on 2018/3/7.
 * 软键盘工具
 */

public class SoftKeybordTools {
    /**
     * 打开软键盘
     *
     * @param context
     * @param currentFocus
     */
    public static void showSoftInput(Context context, View currentFocus) {
        if (context == null) {
            return;
        }

        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && !imm.isActive() && currentFocus != null) {
            imm.showSoftInput(currentFocus, 0);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param currentFocus
     */
    public static void hideSoftInput(Context context, View currentFocus) {
        if (context == null) {
            return;
        }

        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive() && currentFocus != null) {
            final IBinder windowToken = currentFocus.getWindowToken();
            if (windowToken != null) {
                imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

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
     *软键盘事件
     */
    public static class ActionListener implements TextView.OnEditorActionListener {
        private int actionId;
        private Runnable task;

        public ActionListener(int actionId, Runnable task) {
            this.actionId = actionId;
            this.task = task;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (this.actionId == actionId) {
                task.run();
                return true;
            }
            return false;
        }
    }

    /**
     * 软键盘搜索事件
     */
    public static class SearchActionListener extends ActionListener {

        public SearchActionListener(Runnable task) {
            super(EditorInfo.IME_ACTION_SEARCH, task);
        }

    }
}
