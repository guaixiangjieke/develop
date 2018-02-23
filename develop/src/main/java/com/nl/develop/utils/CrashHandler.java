package com.nl.develop.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by NiuLei on 2018/2/23.
 * 未捕获异常处理
 * 初始化后需要调用 {@link Thread#setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler)} 系统默认处理
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    //默认处理
    private final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    private final Writer writer = new StringWriter();
    private final PrintWriter printWriter = new PrintWriter(writer);

    public CrashHandler() {
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace(printWriter);
        LogTools.log(Log.ERROR, "uncaughtException" + "\r\n"
                + "ThreadName:" + t.getName() + "\r\n"
                + "PID:" + android.os.Process.myPid() + "\r\n"
                + writer.toString());

        if (defaultUncaughtExceptionHandler != null) {
            defaultUncaughtExceptionHandler.uncaughtException(t, e);
        }

    }
}
