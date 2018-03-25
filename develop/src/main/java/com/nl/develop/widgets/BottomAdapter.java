package com.nl.develop.widgets;

import android.content.Context;

import com.nl.develop.widgets.recycler.RecyclerAdapter;

import java.util.List;

public class BottomAdapter<T> extends RecyclerAdapter<T> {
    public BottomAdapter(Context context, List<T> data) {
        super(context, BottomViewHolder.class, android.R.layout.simple_list_item_1, data);
    }
}