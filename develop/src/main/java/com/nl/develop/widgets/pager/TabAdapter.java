package com.nl.develop.widgets.pager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by NiuLei on 2018/2/8.
 * viewPager fragment 混合使用适配器
 */

public class TabAdapter extends FragmentAdapter {

    public TabAdapter(List pages, FragmentManager fm) {
        super(pages, fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment item = getItem(position);
        if (item != null && item instanceof ITabTitle) {
            return ((ITabTitle) item).getPageTitle();
        }
        return super.getPageTitle(position);
    }

}
