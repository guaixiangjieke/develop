package com.nl.develop.widgets;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * Created by NiuLei on 2018/4/12.
 */

public class IgnoreBottomViewHolder extends BottomViewHolder<IgnoreAble> {
    public IgnoreBottomViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(IgnoreAble item) {
        super.onBind(item);
        if (itemView instanceof TextView) {
            ((TextView) itemView).setTextColor(item.isIgnore() ? Color.parseColor("#C5C5C5") : Color.parseColor("#626262"));
        }
    }
}
