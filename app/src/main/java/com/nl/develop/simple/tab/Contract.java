package com.nl.develop.simple.tab;

import android.support.v4.app.Fragment;

import com.nl.develop.mvp.MvpContract;

import java.util.List;

/**
 * 选项卡页面 mvp契约类,呈现关系结构
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
        /**
         * 获取选项卡页面
         */
        List<Fragment> getPages();
    }
}
