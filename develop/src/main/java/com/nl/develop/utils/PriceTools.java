package com.nl.develop.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.text.DecimalFormat;

/**
 * Created by NiuLei on 2018/1/30.
 * 价格工具类
 */

public class PriceTools {

    /**
     * 价钱保存小数点后两位 转字符
     *
     * @param price
     * @return
     */
    public static String formatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(price);
    }

    /**
     * 字符 价钱转float
     *
     * @param price
     * @return
     */
    public static double formatPrice(String price) {
        try {
            return Double.parseDouble(price);
        } catch (Exception ignored) {
        }
        return 0.0d;
    }

    //价钱小数点后两位限制
    private static int DECIMAL_DIGITS = 2;

    /**
     * 价钱输入限制
     * 第一位输入小数点 或 0 自动补0.
     * 限制输入小数点后 DECIMAL_DIGITS 位
     */
    public static final InputFilter getPriceFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // source:当前输入的字符
                // start:输入字符的开始位置
                // end:输入字符的结束位置
                // dest：当前已显示的内容
                // dstart:当前光标开始位置
                // dent:当前光标结束位置
                if (dest.length() == 0) {
                    if (source.equals(".") || source.equals("0")) {
                        return "0.";
                    }
                }
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    if (dotValue.length() == DECIMAL_DIGITS) {
                        return "";
                    }
                }
                return null;
            }
        };
    }
}
