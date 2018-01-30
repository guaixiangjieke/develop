package com.nl.develop.widgets;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by NiuLei on 2018/1/19.
 * 软键盘展开关闭监听
 */

public abstract class OnSoftKeyboardStateListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private View view;

    public OnSoftKeyboardStateListener(View view) {
        this.view = view;
    }

    @Override
    public void onGlobalLayout() {
        boolean keyboardShown = isKeyboardShown(view);
        onSoftKeyboardStateChange(keyboardShown);
    }

    private boolean isKeyboardShown(View rootView) {
        if (rootView == null) {
            return false;
        }
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    /**
     * 软键盘显示回调
     *
     * @param isShown
     */
    protected abstract void onSoftKeyboardStateChange(boolean isShown);
}
