package com.nl.develop.widgets.recycler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by NiuLei on 2017/12/5.
 * recyclerView 分割线
 */

public class DividerDecoration extends RecyclerView.ItemDecoration {
    private final Paint dividerPaint = new Paint();
    private int color;
    private int dividerHeight;

    /**
     * @param color         颜色
     * @param dividerHeight 高度
     */
    public DividerDecoration(@ColorInt int color, int dividerHeight) {
        this.color = color;
        this.dividerHeight = dividerHeight;
        dividerPaint.setColor(color);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }
}
