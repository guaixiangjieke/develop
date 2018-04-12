package com.nl.develop.widgets;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiuLei on 2018/4/12.
 */

public class IgnoreBottomMenu<T extends IgnoreAble> extends BottomMenu<T> {

    public IgnoreBottomMenu(Activity context, int maxHeight, OnBottomMenuListener<T> onBottomMenuListener) {
        super(context, new ArrayList<T>(), maxHeight, onBottomMenuListener);
        recyclerAdapter = new IgnoreBottomAdapter<>(context, data);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void onItemPressed(int position) {
        if (onBottomMenuListener != null) {
            onBottomMenuListener.onBottomMenuItemClick(this, data.get(position), position);
        }
    }

    public void ignore(List<T> ignore) {
        if (data == null) {
            return;
        }
        boolean isEmpty = false;
        if (ignore == null || ignore.isEmpty()) {
            isEmpty = true;
        }
        for (T item : data) {
            if (isEmpty) {
                item.setIgnore(false);
            } else {
                item.setIgnore(ignore.indexOf(item) != -1);
            }

        }
        recyclerAdapter.notifyDataSetChanged();
    }
}
