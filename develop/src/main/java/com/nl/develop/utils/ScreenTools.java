package com.nl.develop.utils;

import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by NiuLei on 2018/1/30.
 */

public class ScreenTools {
    /**
     * 获取屏幕分辨率
     *
     * @param activity
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        if (activity == null) {
            return null;
        }
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            defaultDisplay.getRealMetrics(displayMetrics);
        } else {
            defaultDisplay.getMetrics(displayMetrics);
        }
        return displayMetrics;
    }
}
