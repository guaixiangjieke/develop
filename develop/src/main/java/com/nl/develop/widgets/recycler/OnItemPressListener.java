package com.nl.develop.widgets.recycler;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by NiuLei on 2017/12/4.
 * recyclerView item事件
 */

public abstract class OnItemPressListener extends RecyclerView.SimpleOnItemTouchListener implements GestureDetector.OnGestureListener {
    private GestureDetectorCompat detectorCompat;
    private RecyclerView recyclerView;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        this.recyclerView = rv;
        if (detectorCompat == null) {
            detectorCompat = new GestureDetectorCompat(rv.getContext(),this);
        }
        detectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        onPress(e,false);
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        onPress(e,true);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private void onPress(MotionEvent e, boolean longPress){
        if (recyclerView == null) {
            return;
        }
        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (childView == null) {
            return;
        }
        int childAdapterPosition = recyclerView.getChildAdapterPosition(childView);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childView);
        onItemPressed(longPress,recyclerView,adapter,childViewHolder,childAdapterPosition);
    }
    public abstract void onItemPressed(boolean longPress, RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int position);
}
