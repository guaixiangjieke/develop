package com.nl.develop.widgets.recycler;

import android.content.Context;

import java.util.List;

/**
 * Created by NiuLei on 2018/2/28.
 * 简单适配器
 */

public class SimpleRecyclerAdapter<T> extends RecyclerAdapter<T> {
    public SimpleRecyclerAdapter(Context context, List<T> data) {
        super(context, RecyclerViewHolder.class, android.R.layout.simple_list_item_1, data);
    }
}
