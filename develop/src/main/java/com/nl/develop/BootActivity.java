package com.nl.develop;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 启动页面-引导页 mvp view实现类
 * step1、设置适配器  view? fragment?
 * step2、根据适配器个数设置指示器
 * step3、根据页面滑动状态更新指示器状态
 */

public abstract class BootActivity extends AppCompatActivity implements ViewPager
        .OnPageChangeListener {
    private ViewPager bootPager;
    private FrameLayout bootIndicator;
    private PagerAdapter pagerAdapter;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
        setUp();
    }

    @Override
    protected void onDestroy() {
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(dataSetObserver);
        }
        if (bootPager != null) {
            bootPager.removeOnPageChangeListener(this);
        }
        super.onDestroy();
    }

    private void setUp() {
        bootPager = findViewById(R.id.bootPager);
        bootIndicator = findViewById(R.id.bootIndicator);
        Guideline guideline = findViewById(R.id.bootIndicatorLine);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline
                .getLayoutParams();
        layoutParams.guidePercent = guidePercent();
        pagerAdapter = getPageAdapter();
        if (pagerAdapter != null) {
            pagerAdapter.registerDataSetObserver(dataSetObserver);
            bootPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
        }
        bootPager.addOnPageChangeListener(this);

    }


    /**
     * 适配器改变
     */
    private final DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            onPageDataChanged(bootIndicator, pagerAdapter);
        }

    };

    /**
     * 指示器布局 摆放位置百分比
     *
     * @return
     */
    protected float guidePercent() {
        return 0.8f;
    }

    /**
     * 获取适配器
     *
     * @return {@link PagerAdapter}
     */
    protected abstract PagerAdapter getPageAdapter();

    /**
     * 数据改变
     *
     * @param bootIndicatorContainer
     * @param pagerAdapter
     */
    protected void onPageDataChanged(FrameLayout bootIndicatorContainer, PagerAdapter
            pagerAdapter) {
        if (bootIndicatorContainer != null && pagerAdapter != null) {
            int childCount = bootIndicatorContainer.getChildCount();
            if (childCount > 0) {
                bootIndicatorContainer.removeAllViews();
            }
            int count = pagerAdapter.getCount();
            if (count > 0) {
                radioGroup = new RadioGroup(this);
                radioGroup.setOrientation(LinearLayout.HORIZONTAL);
                radioGroup.setGravity(Gravity.CENTER);
                for (int i = 0; i < count; i++) {
                    radioGroup.addView(new RadioButton(this));
                }
                bootIndicatorContainer.addView(radioGroup);
                radioGroup.check(radioGroup.getChildAt(0).getId());
            }

        }
    }

    @Override
    public void onPageSelected(int position) {
        if (radioGroup != null && position >= 0 && position < radioGroup.getChildCount()) {
            radioGroup.check(radioGroup.getChildAt(position).getId());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
