package com.nl.develop.simple.boot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nl.develop.mvp.MvpActivity;
import com.nl.develop.simple.R;

/**
 * 启动页面-引导页 mvp view实现类
 */

public class BootActivity extends MvpActivity<Contract.IPresenter> implements Contract.IView {

    private ViewPager bootPager;
    private RadioGroup bootIndicator;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
        setUp();
    }

    @Override
    protected void onDestroy() {
        if (bootPager != null) {
            bootPager.removeOnPageChangeListener(onPageChangeListener);
        }
        super.onDestroy();
    }

    private void setUp() {
        bootPager = findViewById(R.id.bootPager);
        bootIndicator = findViewById(R.id.bootIndicator);
        pagerAdapter = getPagerAdapter();
        bootPager.setAdapter(pagerAdapter);
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            bootIndicator.addView(new RadioButton(this));
        }
        bootPager.addOnPageChangeListener(onPageChangeListener);
    }

    /**
     * 页面滑动监听 更新指示器
     */
    private final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (position >= 0 && position < bootIndicator.getChildCount()) {
                View childAt = bootIndicator.getChildAt(position);
                bootIndicator.check(childAt.getId());
            }
        }
    };

    private PagerAdapter getPagerAdapter() {
        return new PagerAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return false;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return super.instantiateItem(container, position);
            }
        };
    }
}
