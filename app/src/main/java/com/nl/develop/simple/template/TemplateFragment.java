package com.nl.develop.simple.template;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nl.develop.mvp.MvpFragment;

/**
 * mvp模板 mvp view实现类
 */

public class TemplateFragment extends MvpFragment<Contract.IPresenter> implements Contract.IView {

    public TemplateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.fragment_template, container, false);
        return contentView;
    }
}
