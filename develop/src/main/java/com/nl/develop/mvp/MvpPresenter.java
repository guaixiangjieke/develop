package com.nl.develop.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by NiuLei on 2018/1/29.
 */

public class MvpPresenter<M extends MvpContract.IModel, V extends MvpContract.IView> implements MvpContract.IPresenter {
    private M model;
    private V view;

    public MvpPresenter(M model, V view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public MvpContract.IPresenter getParent() {
        if (view != null && view instanceof Fragment) {
            Activity activity = view.getActivity();
            if (activity != null && activity instanceof MvpActivity) {
                return ((MvpActivity) activity).getPresenter();
            }
        }
        return null;
    }
}
