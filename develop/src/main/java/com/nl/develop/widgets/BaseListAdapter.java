package com.nl.develop.widgets;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * TODO(这里用一句话描述这个类的作用)
 *
 * @author NiuLei
 * @date 2018/10/9 20:15
 */
public abstract class BaseListAdapter<T,VH extends BaseListAdapter.ViewHolder<T>> extends android.widget.BaseAdapter {
    protected List<T> data;

    public BaseListAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH viewHolder = null;
        if (convertView == null) {
            viewHolder =onCreateViewHolder(parent,getItemViewType(position));
            convertView = viewHolder.itemView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }
        viewHolder.onBind(getItem(position));
        return convertView;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int itemViewType);

    public static class ViewHolder<T>{
        protected View itemView;
        private T item;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public void onBind(T item) {
            this.item = item;
        }
    }
}
