package com.nl.develop.widgets.recycler;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by NiuLei on 2017/12/4.
 * ViewHolder
 */

public class RecyclerViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
    }

    /**
     * viewholder元素点击事件  tag为null视为item点击事件
     */
    public interface OnViewHolderElementClickListener {
        void onViewHolderElementClick(RecyclerView.ViewHolder viewHolder, int position, String tag);
    }

    private OnViewHolderElementClickListener elementClickListener;

    public void setElementClickListener(OnViewHolderElementClickListener elementClickListener) {
        this.elementClickListener = elementClickListener;
    }


    public void onBind(T item) {
        if (item != null && itemView instanceof TextView) {
            ((TextView) itemView).setText(item.toString());
        }
    }

    public void onBind(RecyclerAdapter<T> adapter, T item) {
        onBind(item);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (elementClickListener != null) {
            elementClickListener.onViewHolderElementClick(this, getAdapterPosition(), tag == null ? null : tag.toString());
        }

    }

    /**
     * 获取字符
     *
     * @param resId
     * @return
     */
    protected String getString(@StringRes int resId) {
        return itemView.getResources().getString(resId);
    }


    /**
     * 获取字符
     *
     * @param resId
     * @return
     */
    protected String getString(@StringRes int resId, Object... formatArgs) {
        return itemView.getResources().getString(resId, formatArgs);
    }
}
