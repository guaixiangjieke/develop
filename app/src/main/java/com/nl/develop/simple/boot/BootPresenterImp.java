package com.nl.develop.simple.boot;

import com.nl.develop.mvp.MvpPresenter;

/**
 * 启动页面-引导页 mvp presenter实现类
 */

public class BootPresenterImp extends MvpPresenter<Contract.IModel, Contract.IView> implements Contract.IPresenter {
    public BootPresenterImp(Contract.IModel model, Contract.IView view) {
        super(model, view);
    }
}
