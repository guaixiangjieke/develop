package com.nl.develop.widgets;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.nl.develop.widgets.recycler.RecyclerViewHolder;

public class BottomViewHolder<T> extends RecyclerViewHolder<T> {
    public BottomViewHolder(View itemView) {
        super(itemView);
        if (itemView instanceof TextView) {
            ((TextView) itemView).setGravity(Gravity.CENTER);
            ((TextView) itemView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        }
    }
}