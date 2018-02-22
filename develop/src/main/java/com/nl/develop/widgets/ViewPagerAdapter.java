package com.nl.develop.widgets;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by NiuLei on 2018/2/22.
 * ViewPager适配器
 */

public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter {
    private List<View> views;

    public ViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
