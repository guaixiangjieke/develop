package com.nl.develop.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.nl.develop.R;
import com.nl.develop.widgets.recycler.OnItemPressListener;
import com.nl.develop.widgets.recycler.RecyclerAdapter;
import com.nl.develop.widgets.recycler.SimpleRecyclerAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by NiuLei on 2017/12/6.
 * 底部弹框
 */

public class BottomMenu extends PopupWindow {
    private final int height;
    private final View parent;
    private RecyclerAdapter<String> recyclerAdapter;
    private final WeakReference<Activity> activityRef;
    private RecyclerView recyclerView;
    private final List<String> data;
    private OnBottomMenuListener onBottomMenuListener;

    /**
     * 菜单监听
     */
    public interface OnBottomMenuListener {
        /**
         * 子项点击监听
         *
         * @param bottomMenu
         * @param item
         * @param position
         */
        void onBottomMenuItemClick(BottomMenu bottomMenu, String item, int position);
    }

    public BottomMenu(Activity context, List<String> data, int height, OnBottomMenuListener onBottomMenuListener) {
        super(context);
        this.data = data;
        this.height = height;
        this.activityRef = new WeakReference<>(context);
        this.onBottomMenuListener = onBottomMenuListener;
        parent = context.getWindow().getDecorView();
        init(context);
    }

    private void init(Context context) {
        setBackgroundDrawable(null);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(height);
        setAnimationStyle(R.style.BottomUpAnimation);
        //初始化列表
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerAdapter = new SimpleRecyclerAdapter<>(context, data);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnItemTouchListener(onItemPressListener);
        setContentView(recyclerView);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }

    /**
     * item事件
     */
    private final OnItemPressListener onItemPressListener = new OnItemPressListener() {
        @Override
        public void onItemPressed(boolean longPress, RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int position) {
            if (!longPress) {
                if (onBottomMenuListener != null) {
                    onBottomMenuListener.onBottomMenuItemClick(BottomMenu.this, data.get(position), position);
                }
                dismiss();
            }
        }
    };

    /**
     * 以activity为依附 底部显示
     */
    public void show() {
        final Activity activity = activityRef.get();
        if (activity != null) {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.alpha = 0.2f;
            activity.getWindow().setAttributes(attributes);
        }
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void dismiss() {
        final Activity activity = activityRef.get();
        if (activity != null) {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.alpha = 1.0f;
            activity.getWindow().setAttributes(attributes);
        }
        super.dismiss();
    }
}
