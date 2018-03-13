package com.nl.develop.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by NiuLei on 2018/3/12.
 */

public class CloseTools {
    public static void release(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
    }
}
