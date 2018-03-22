package com.nl.develop.mvp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.nl.develop.utils.ViewTools;
import com.nl.develop.widgets.LoadingDialog;

import java.lang.reflect.Method;

/**
 * Created by NiuLei on 2018/1/29.
 */

public class MvpFragment<P extends MvpContract.IPresenter> extends Fragment implements MvpContract.IView<P> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewTools.onPreDrawOfView(view, new Runnable() {
            @Override
            public void run() {
                if (presenter != null) {
                    presenter.onUiVisible();
                }
            }
        });
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


    protected P presenter;

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    private LoadingDialog loadingDialog;

    @Override
    public void startProgress(int resId) {
        if (!invokeMvpActivity("startProgress", new Class[]{int.class}, new Object[]{resId})) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.setOnCancelListener(onCancelListener);
            }

            if (loadingDialog != null && isVisible() && !loadingDialog.isShowing()) {
                loadingDialog.show(getString(resId));
            }
        }
    }

    @Override
    public void stopProgress() {
        if (!invokeMvpActivity("stopProgress", null, null)) {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
            }
        }
    }

    /**
     * loading取消事件
     */
    DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            presenter.cancelRequest();
        }
    };

    /**
     * 反射调用对应activity方法
     *
     * @param name
     * @param parameterTypes
     * @param objects
     */
    private boolean invokeMvpActivity(String name, Class<?>[] parameterTypes, Object[] objects) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof MvpActivity) {
            Class<MvpActivity> mvpActivityClass = MvpActivity.class;
            try {
                Method declaredMethod = mvpActivityClass.getDeclaredMethod(name, parameterTypes);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(activity, objects);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
