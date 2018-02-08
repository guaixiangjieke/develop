package com.nl.develop.simple.template;

import com.nl.develop.mvp.MvpPresenter;

/**
 * mvp模板 mvp presenter实现类
 */

public class TemplatePresenterImp extends MvpPresenter<Contract.IModel, Contract.IView> implements Contract.IPresenter {
    public TemplatePresenterImp(Contract.IModel model, Contract.IView view) {
        super(model, view);
    }
}
