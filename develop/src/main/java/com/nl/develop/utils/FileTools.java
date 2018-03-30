package com.nl.develop.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by NiuLei on 2018/3/12.
 * 文件工具
 */

public class FileTools {
    /**
     * 复制
     *
     * @param src      源
     * @param destPath 目标目录
     * @param name     名称
     * @return 成功与否
     */
    public static boolean copy(File src, String destPath, String name) {
        return moveTo(false, src, destPath, name);
    }

    /**
     * 复制
     *
     * @param src      源
     * @param destPath 目标目录
     * @return 成功与否
     */
    public static boolean copy(File src, String destPath) {
        if (src == null || !src.exists()) {
            return false;
        }
        return moveTo(false, src, destPath, src.getName());
    }

    /**
     * 复制
     *
     * @param src      源
     * @param destPath 目标目录
     * @param name     名称
     * @return 成功与否
     */
    public static boolean moveTo(File src, String destPath, String name) {
        return moveTo(true, src, destPath, name);
    }

    /**
     * 复制
     *
     * @param src      源
     * @param destPath 目标目录
     * @return 成功与否
     */
    public static boolean moveTo(File src, String destPath) {
        if (src == null || !src.exists()) {
            return false;
        }
        return moveTo(true, src, destPath, src.getName());
    }

    /**
     * 复制
     *
     * @param deleteOriginal 是否删除源文件
     * @param src            源
     * @param destPath       目标目录
     * @param name           名称
     * @return 成功与否
     */
    private static boolean moveTo(boolean deleteOriginal, File src, String destPath, String name) {
        if (src != null && src.exists() && destPath != null && name != null) {
            File destFile = new File(destPath, name);
            FileChannel srcChannel = null;
            FileChannel destChannel = null;
            try {
                boolean newFile = destFile.createNewFile();
                if (newFile) {
                    srcChannel = new FileInputStream(src).getChannel();
                    destChannel = new FileInputStream(destFile).getChannel();
                    long size = srcChannel.size();
                    long l = srcChannel.transferTo(0, size, destChannel);
                    LogTools.d("moveTo: " + l + " fileSize : " + size);
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                CloseTools.release(srcChannel);
                CloseTools.release(destChannel);
                if (deleteOriginal) {
                    src.delete();
                }
            }
        }
        return false;
    }

    /**
     * 根据uri复制 到指定目录
     * @param context
     * @param uri
     * @param targetPath
     * @return
     */
    public static boolean copyTo(Context context, Uri uri, String targetPath) {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            final ContentResolver contentResolver = context.getContentResolver();
            ParcelFileDescriptor descriptor = contentResolver.openFileDescriptor(uri, "r");
            File file = new File(targetPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            inChannel = new FileInputStream(descriptor.getFileDescriptor()).getChannel();
            outChannel = new FileOutputStream(file).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseTools.release(inChannel);
            CloseTools.release(outChannel);
        }
        return false;
    }
}
