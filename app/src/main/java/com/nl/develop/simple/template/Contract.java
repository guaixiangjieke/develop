package com.nl.develop.simple.template;

import com.nl.develop.mvp.MvpContract;

/**
 * mvp模板 mvp契约类,呈现关系结构
 */

interface Contract {

    /**
     * 数据源
     */

    interface IModel extends MvpContract.IModel {

    }

    /**
     * 视图
     */

    interface IView extends MvpContract.IView<IPresenter> {

    }

    /**
     * 逻辑处理
     */

    interface IPresenter extends MvpContract.IPresenter {

    }
}
