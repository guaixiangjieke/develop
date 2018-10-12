package com.nl.develop.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * Created by NiuLei on 2018/6/20.
 * 展开 收起
 */

public class ExpandableEditText extends AppCompatEditText {
    /**
     * 最大行
     */
    private int maxLinesReal;
    /**
     * 当前text行总数
     */
    private int lineCount;
    private OnExpandableEditTextListener onExpandableEditTextListener;

    public void setOnExpandableEditTextListener(OnExpandableEditTextListener onExpandableEditTextListener) {
        this.onExpandableEditTextListener = onExpandableEditTextListener;
    }

    /**
     * 监听
     */
    public interface OnExpandableEditTextListener {
        /**
         * 超出最大行
         *
         * @param isOutOf 是否
         */
        void onOutOfMaxLines(boolean isOutOf);

        /**
         * 收起
         */
        void onExpanded();

        /**
         * 展开
         */
        void onCollapsed();
    }

    public ExpandableEditText(Context context) {
        super(context);
        init();
    }

    public ExpandableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int currentTextColor = getCurrentTextColor();
        setHintTextColor(currentTextColor);
    }

    /**
     * 设置最大行
     *
     * @param maxLinesReal
     */
    public void setMaxLinesReal(int maxLinesReal) {
        this.maxLinesReal = maxLinesReal;
        setMaxLines(maxLinesReal);
    }

    /**
     * 获取文本
     */
    public String getTextReal() {
        String text = getText().toString();
        CharSequence hint = getHint();
        return TextUtils.isEmpty(text) ? (TextUtils.isEmpty(hint) ? "" : hint.toString()) : text;
    }

    /**
     * 开关
     */
    public void toggle() {
        if (isCollapsed()) {
            expanded();
        } else {
            collapsed();
        }
    }

    /**
     * 是否展开
     */
    public boolean isCollapsed() {
        int maxLines = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            maxLines = getMaxLines();
        }
        return maxLines == maxLinesReal;
    }

    /**
     * 展开
     */
    private void expanded() {
        setMaxLines(lineCount);
        if (onExpandableEditTextListener != null) {
            onExpandableEditTextListener.onExpanded();
        }
        CharSequence hint = getHint();
        String hintStr = "";
        if (hint != null) {
            hintStr = hint.toString();
        }
        setText(hintStr);
        setFocusable(true);
        setFocusableInTouchMode(true);
        addTextChangedListener(textWatcher);
    }

    /**
     * 收起
     */
    private void collapsed() {
        setMaxLines(maxLinesReal);
        if (onExpandableEditTextListener != null) {
            onExpandableEditTextListener.onCollapsed();
        }
        removeTextChangedListener(textWatcher);
        String textStr = getText().toString();
        setText("");
        setHint(textStr);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    /**
     * 更新检查是否超出最大行
     *
     * @param isOutOfMaxLines
     */
    public void updateOutOf(boolean isOutOfMaxLines) {
        if (onExpandableEditTextListener != null) {
            onExpandableEditTextListener.onOutOfMaxLines(isOutOfMaxLines);
        }
    }

    /**
     * 设置文本
     *
     * @param textReal
     */
    public void setTextReal(String textReal) {
        setText(textReal);
        lineCount = getLineCount();
        setMaxLines(maxLinesReal);
        updateOutOf(lineCount > maxLinesReal);
        if (lineCount > maxLinesReal) {
            setText("");
            setHint(textReal);
            setFocusable(false);
            setFocusableInTouchMode(false);
        } else {
            setFocusable(true);
            setFocusableInTouchMode(true);
            addTextChangedListener(textWatcher);
        }
    }

    /**
     * 文本改变监听 更新 updateOutOf
     */
    final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            lineCount = getLineCount();
            setMaxLines(lineCount);
            updateOutOf(lineCount > maxLinesReal);
            if (onExpandableEditTextListener != null) {
                onExpandableEditTextListener.onExpanded();
            }
        }
    };
}
