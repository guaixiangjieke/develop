package com.nl.develop.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.nl.develop.DevelopConfig;
import com.nl.develop.R;
import com.nl.develop.net.NetCallBack;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by NiuLei on 2018/1/29.
 */

public class MvpPresenter<M extends MvpContract.IModel, V extends MvpContract.IView> implements MvpContract.IPresenter {
    protected M model;
    protected V view;

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

    @Override
    public void cancelRequest() {

    }


    /**
     * 网络回调实现
     */
    protected class SimpleCallBack implements NetCallBack {

        private int ingMsg;
        private int successMsg;

        public SimpleCallBack() {
        }

        public SimpleCallBack(int ingMsg, int successMsg) {
            this.ingMsg = ingMsg;
            this.successMsg = successMsg;
        }


        @Override
        public void onStart() {
            if (ingMsg > 0) {
                view.startProgress(ingMsg);
            } else {
                view.startProgress(R.string.net_loading);
            }
        }

        @Override
        public void onFailure(IOException e) {
            String message = e.getMessage();
            if (message != null) {
                if (message.toLowerCase().indexOf("Canceled".toLowerCase()) != -1) {
                } else if (message.toLowerCase().indexOf("TimeOut".toLowerCase()) != -1) {
                } else if (message.toLowerCase().indexOf("Socket closed".toLowerCase()) != -1) {
                } else {
                }
            }
        }

        @Override
        public void onResponse(String response) {
            if (successMsg > 0) {
                view.showToast(successMsg);
            }
        }

        @Override
        public void onFinish() {
            view.stopProgress();
        }

    }

    /**
     * 回调json转换实现
     *
     * @param <T>
     */
    protected abstract class ConvertCallBack<T> extends SimpleCallBack {
        @Override
        public void onResponse(String response) {
            super.onResponse(response);
            Type type = com.nl.develop.utils.TypeToken.getSuperclassTypeParameter(getClass());
            T result = DevelopConfig.getInstance().getJsonFactory().fromJson(response, type);
            onResponse(result);
        }

        public abstract void onResponse(T result);
    }
}
