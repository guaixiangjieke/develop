package com.nl.develop.widgets;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by NiuLei on 2018/2/8.
 * viewPager fragment 混合使用适配器
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> pages;

    public FragmentAdapter(List<Fragment> pages, FragmentManager fm) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return pages == null ? null : pages.get(position);
    }

    @Override
    public int getCount() {
        return pages == null ? 0 : pages.size();
    }
}
