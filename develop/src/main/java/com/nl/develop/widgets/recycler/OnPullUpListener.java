package com.nl.develop.widgets.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Created by NiuLei on 2018/3/23.
 * 根据item事件实现上拉刷新
 */

public abstract class OnPullUpListener extends RecyclerView.SimpleOnItemTouchListener {
    private float startY;
    /**
     * 是否刷新成功
     */
    private boolean isSuccess = true;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        int action = e.getAction();
        float rawY = e.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                float diffY = rawY - startY;
                startY = rawY;
                if (diffY < 0) {/*上拉*/
                    /*滑动到最底部*/
                    if (isSuccess && !rv.canScrollVertically(1)) {
                        onPullUp(rv);
                        isSuccess = false;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(rv, e);
    }

    /**
     * 设置刷新成功
     */
    public void setSuccess() {
        isSuccess = true;
    }

    /**
     * 上拉
     */
    public abstract void onPullUp(RecyclerView rv);
}
