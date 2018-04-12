package com.nl.develop.widgets;

import android.content.Context;

import com.nl.develop.widgets.recycler.RecyclerAdapter;

import java.util.List;

/**
 * Created by NiuLei on 2018/4/12.
 */

public class IgnoreBottomAdapter<T> extends RecyclerAdapter<T> {
    public IgnoreBottomAdapter(Context context, List<T> data) {
        super(context, IgnoreBottomViewHolder.class, android.R.layout.simple_list_item_1, data);
    }
}
