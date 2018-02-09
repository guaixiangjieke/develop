package com.nl.develop.simple.tab;

import android.support.v4.app.Fragment;

import com.nl.develop.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 选项卡页面 mvp presenter实现类
 */

public class TabPresenterImp extends MvpPresenter<Contract.IModel, Contract.IView> implements Contract.IPresenter {
    public TabPresenterImp(Contract.IModel model, Contract.IView view) {
        super(model, view);
    }

    @Override
    public List<Fragment> getPages() {
        // TODO: 2018/2/8 获取页面集合
        List<Fragment> pages = new ArrayList<>();
        pages.add(new Fragment());
        pages.add(new Fragment());
        pages.add(new Fragment());
        return pages;
    }
}
