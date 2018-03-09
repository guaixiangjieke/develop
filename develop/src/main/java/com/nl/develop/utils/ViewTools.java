package com.nl.develop.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

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

    /**
     * 绘制视图树时执行 任务
     * @param view
     * @param runnable
     */
    public static void onPreDrawOfView(@NonNull final View view, @NonNull final Runnable runnable) {
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                runnable.run();
                return false;
            }
        });
    }
}
