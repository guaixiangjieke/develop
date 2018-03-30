package com.nl.develop.mvp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.nl.develop.DevelopConfig;
import com.nl.develop.R;
import com.nl.develop.net.NetCallBack;
import com.nl.develop.utils.LogTools;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiuLei on 2018/1/29.
 */

public class MvpPresenter<M extends MvpContract.IModel, V extends MvpContract.IView> implements MvpContract.IPresenter {
    private static final int PERMISSION_REQUEST_CODE = 102;
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
    public void onUiVisible() {

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
        checkNeedPermissions();
    }

    /**
     * 检查所需权限
     */
    private void checkNeedPermissions() {
        String permissions[] = getNeedPermissions();
        if (permissions != null && permissions.length > 0) {
            for (int i = 0; i < permissions.length; i++) {
                String needPermission = permissions[i];
                List<String> deniedPermissions = new ArrayList<>();
                //收集没有获得的权限
                if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(view.getActivity(), needPermission)) {
                    deniedPermissions.add(needPermission);
                }
                int size = deniedPermissions.size();
                if (size > 0) {//请求权限
                    ActivityCompat.requestPermissions(view.getActivity(), deniedPermissions.toArray(new String[size]), PERMISSION_REQUEST_CODE);
                }
            }
        }
    }

    protected String[] getNeedPermissions() {
        return null;
    }

    @Override
    public void onPause() {

    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PERMISSION_REQUEST_CODE == requestCode) {
            if (grantResults.length > 0) {
                // 2017/12/25 未做其他处理
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    if (PackageManager.PERMISSION_DENIED == grantResult) {
                        LogTools.w("onRequestPermissionsResult: "+permissions[i] + " PERMISSION_DENIED");
                    }
                }
            }

        }
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
            e.printStackTrace();
            String message = e.getMessage();
            if (message != null) {
                if (message.toLowerCase().indexOf("Canceled".toLowerCase()) != -1) {
                    view.showToast(R.string.net_canceled);
                } else if (message.toLowerCase().indexOf("TimeOut".toLowerCase()) != -1) {
                    view.showToast(R.string.net_timeout);
                } else {
                    view.showToast(R.string.net_failure);
                }
            }
        }

        @Override
        public void onResponse(String response) {
            if (successMsg > 0) {
                view.showToast(successMsg);
            }
            onPresenterResponse(response);
        }

        @Override
        public void onFinish() {
            view.stopProgress();
        }

    }

    protected void onPresenterResponse(String response) {

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
            onPresenterResponse(result);
            onResponse(result);
        }

        public abstract void onResponse(T result);
    }

    protected <T> void onPresenterResponse(T result) {

    }
}
