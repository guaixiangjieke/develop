package com.nl.develop.simple.tab;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.nl.develop.mvp.MvpActivity;
import com.nl.develop.simple.R;
import com.nl.develop.widgets.pager.TabAdapter;

import java.util.List;

/**
 * 选项卡页面 mvp view实现类
 * {@link TabLayout#setTabMode(int)} 设置模式 {@link TabLayout#MODE_SCROLLABLE} 可滚动
 * {@link TabLayout#setTabTextColors(int, int)} 设置选项卡文字颜色改变
 * {@link TabLayout#setSelectedTabIndicatorColor(int)} 设置指示器颜色 可设置透明取消
 */

public class TabActivity extends MvpActivity<Contract.IPresenter> implements Contract.IView {
    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        final Contract.IModel model = new TabModelImp();
        new TabPresenterImp(model, this);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        setUpTab();
    }

    /**
     * 设置tab
     */
    void setUpTab() {
        List<Fragment> pages = presenter.getPages();
        tabAdapter = new TabAdapter(pages, getSupportFragmentManager());
        tabAdapter.registerDataSetObserver(pagerAdapterObserver);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        populateFromPagerAdapter();
        // TODO: 2018/2/8 设置监听  记得remove
//        viewPager.addOnPageChangeListener();
//        tabLayout.addOnTabSelectedListener();
    }

    /**
     * 填充tab
     */
    void populateFromPagerAdapter() {
        if (tabLayout != null) {
            int count = tabAdapter.getCount();
            for (int i = 0; i < count; i++) {
                CharSequence pageTitle = tabAdapter.getPageTitle(i);
                Fragment item = tabAdapter.getItem(i);
                // TODO: 2018/2/8 根据标题 设置tab 可自定义view
                tabLayout.getTabAt(i).setIcon(R.mipmap.ic_launcher);
            }
        }
    }

    /**
     * page适配器观察者
     */
    final DataSetObserver pagerAdapterObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            populateFromPagerAdapter();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };

    @Override
    protected void onDestroy() {
        if (tabAdapter != null) {
            tabAdapter.unregisterDataSetObserver(pagerAdapterObserver);
        }
        super.onDestroy();
    }
}
