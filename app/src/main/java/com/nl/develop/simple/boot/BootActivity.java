package com.nl.develop.simple.boot;

import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nl.develop.BootBaseActivity;
import com.nl.develop.simple.R;
import com.nl.develop.widgets.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动页面-引导页 mvp view实现类
 * simple 默认0.8f  RadioGroup RadioButton实现
 */

public class BootActivity extends BootBaseActivity<Contract.IPresenter> implements Contract.IView {

    private RadioGroup radioGroup;

    @Override
    protected float guidePercent() {
        return 0.9f;
    }

    @Override
    protected PagerAdapter getPageAdapter() {
        List<View> data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            data.add(imageView);
        }
        return new ViewPagerAdapter(data);
    }

    @Override
    protected void onPageDataChanged(FrameLayout bootIndicatorContainer, PagerAdapter pagerAdapter) {
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (radioGroup != null && position >= 0 && position < radioGroup.getChildCount()) {
            radioGroup.check(radioGroup.getChildAt(position).getId());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
