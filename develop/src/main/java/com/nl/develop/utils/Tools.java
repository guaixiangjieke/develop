package com.nl.develop.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    public static final ExecutorService THREAD_POOL_EXECUTOR = Executors.newCachedThreadPool();/*new ThreadPoolExecutor(MAX_NUM_THREADS, MAX_NUM_THREADS,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(MAX_NUM_THREADS * 2));*/

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
     * 运营商号段如下：
     * 中国联通号码：130、131、132、145（无线上网卡）、155、156、185（iPhone5上市后开放）、186、176（4G号段）、
     * 175（2015年9月10日正式启用，暂只对北京、上海和广东投放办理）
     * 中国移动号码：134、135、136、137、138、139、147（无线上网卡）、150、151、152、157、158、159、182、183、187、188、178
     * 中国电信号码：133、153、180、181、189、177、173、149 虚拟运营商：170、1718、1719
     * 手机号前3位的数字包括：
     * 1 :1
     * 2 :3,4,5,7,8
     * 3 :0,1,2,3,4,5,6,7,8,9
     **/
    public static final Pattern phonePattern = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$");
    /**
     * 验证带区号的
     */
    public static final Pattern fixedPhonePatternRegion = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");
    /**
     * 验证没有区号的
     */
    public static final Pattern fixedPhonePattern = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");

    /**
     * 验证是否为手机号码格式
     *
     * @param PhoneNo
     * @return
     */
    public static boolean isPhoneFomart(String PhoneNo) {
        if (PhoneNo == null) {
            return false;
        }
        if (PhoneNo.length() != 11) {
            return false;
        }
        Matcher m = phonePattern.matcher(PhoneNo);
        return m == null ? false : m.matches();

    }

    /**
     * 验证是否为固定电话号码格式
     *
     * @param PhoneNo
     * @return
     */
    public static boolean isFixedPhoneFomart(String PhoneNo) {
        Matcher m = null;
        boolean b = false;
        if (PhoneNo.length() > 8) {
            m = fixedPhonePatternRegion.matcher(PhoneNo);
            b = m.matches();
        } else {
            m = fixedPhonePattern.matcher(PhoneNo);
            b = m.matches();
        }
        return b;

    }

}
