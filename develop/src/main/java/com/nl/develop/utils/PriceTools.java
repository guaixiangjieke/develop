package com.nl.develop.utils;

import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

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

    /**
     * 输入限制
     *
     * @param editText
     */
    public static void supportPrice(@NonNull EditText editText) {
        editText.setFilters(new InputFilter[]{getPriceFilter()});
        editText.setOnKeyListener(deleteListener);
    }

    //价钱小数点后两位限制
    private static int DECIMAL_DIGITS = 2;
    //最大输入价钱位数
    private static int LENGTH_DIGITS = 9;

    /**
     * 小数点后有数字 病情 删除小数点 的行为  屏蔽
     */
    public static final View.OnKeyListener deleteListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && v instanceof EditText) {
                final String string = ((EditText) v).getText().toString();
                int selectionStart = ((EditText) v).getSelectionStart();
                if (string.length() > 1 && selectionStart - 1 >= 0) {//删除的字符是否为小数点
                    String substring = string.substring(selectionStart - 1, selectionStart);
                    if (".".equals(substring) && selectionStart < string.length()) {
                        return true;
                    }
                    if (1 == selectionStart && selectionStart + 1 <= string.length()) {//查看删除时光标后的一位字符是否为小数点
                        String selectAfter = string.substring(selectionStart, selectionStart + 1);
                        if (".".equals(selectAfter)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    };

    /**
     * 价钱输入限制
     * 第一位输入小数点 或 0 自动补0.
     * 限制输入小数点后 DECIMAL_DIGITS 位
     */
    public static final InputFilter getPriceFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // source:当前输入的字符
                // start:输入字符的开始位置
                // end:输入字符的结束位置
                // dest：当前已显示的内容
                // dstart:当前光标开始位置
                // dent:当前光标结束位置
                String destString = dest.toString();
                //第一个字符输入 0或者. 默认显示为0.1
                if (dest.length() == 0) {
                    if (source.equals(".") || source.equals("0")) {
                        return "0.";
                    }
                }
                //第一位插入0时 判断原有字符串第一位是否为小数点 如果不是返回""
                if ("0".equals(source) && 0 == dstart && 0 == dend) {
                    if (!destString.isEmpty()) {
                        String first = destString.substring(0, 1);
                        if (!".".equals(first)) {
                            return "";
                        }
                    }
                }
                //分割字符串
                String[] splitArray = destString.split("\\.");
                if (splitArray == null || splitArray.length == 0) {
                    return null;
                }
                String before = splitArray[0];
                String after = "";
                if (splitArray.length > 1) {
                    after = splitArray[1];
                }
                int indexOf = destString.indexOf(".");
                if (indexOf == -1) {
                    //没有小数点的时候 输入字符超过限制长度
                    if (!".".equals(source) && before.length() >= LENGTH_DIGITS) {
                        return "";
                    }
                } else {
                    if (dstart == dend) {//添加或插入字符
                        if (dstart <= indexOf) { //小数点前插入 && 长度判断
                            if (before.length() >= LENGTH_DIGITS) {
                                return "";
                            }
                        } else {
                            // //小数点后插入 && 长度判断
                            if (after.length() == DECIMAL_DIGITS) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }
        };
    }
}
