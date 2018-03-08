package com.nl.develop.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by NiuLei on 2018/3/8.
 */

public class ViewTools {
    /**
     * recyclerView ScrollView 嵌套冲突  建议使用 {@link android.support.v4.widget.NestedScrollView}
     */
    public static void recyclerViewWithScrollconflict(RecyclerView recyclerView) {
        if (recyclerView != null) {
            final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                ((LinearLayoutManager) layoutManager).setSmoothScrollbarEnabled(true);
            }
            layoutManager.setAutoMeasureEnabled(true);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
        }
    }
}
