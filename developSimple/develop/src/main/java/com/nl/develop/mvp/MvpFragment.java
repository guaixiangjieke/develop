package com.nl.develop.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;

/**
 * Created by NiuLei on 2018/1/29.
 */

public class MvpFragment<P extends MvpContract.IPresenter> extends Fragment implements MvpContract.IView<P> {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter != null) {
            presenter.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (presenter != null) {
            presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter != null) {
            presenter.onActivityResult(requestCode, resultCode, data);
        }
    }


    private P presenter;

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(String text) {
        invokeMvpActivity("showToast", new Class[]{String.class}, new Object[]{text});
    }

    @Override
    public void startProgress(String text) {
        invokeMvpActivity("startProgress", new Class[]{String.class}, new Object[]{text});
    }

    @Override
    public void stopProgress() {
        invokeMvpActivity("stopProgress", null, null);
    }

    /**
     * 反射调用对应activity方法
     *
     * @param name
     * @param parameterTypes
     * @param objects
     */
    private void invokeMvpActivity(String name, Class<?>[] parameterTypes, Object[] objects) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof MvpActivity) {
            Class<MvpActivity> mvpActivityClass = MvpActivity.class;
            try {
                Method declaredMethod = mvpActivityClass.getDeclaredMethod(name, parameterTypes);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(activity, objects);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
