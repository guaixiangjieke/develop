package com.nl.develop.widgets;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nl.develop.R;


/**
 * 加载
 */
public class LoadingDialog extends Dialog {
    private final TextView tipTextView;
    private final ImageView spaceshipImage;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View loadingDialogView = inflater.inflate(R.layout.net_loading_dialog, null);// 得到加载view
        spaceshipImage = loadingDialogView.findViewById(R.id.img);
        tipTextView = loadingDialogView.findViewById(R.id.tipTextView);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(loadingDialogView);// 设置布局
        setCanceledOnTouchOutside(false);
        setCancelable(true);

    }

    public void show(String msg) {
        if (msg != null) {
            tipTextView.setText(msg);// 设置加载信息
        }
        spaceshipImage.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.loading_animation));
        if (!isShowing()) {
            show();
        }

    }

    @Override
    public void dismiss() {
        if (spaceshipImage != null) {
            spaceshipImage.clearAnimation();
        }
        super.dismiss();
    }

    @Override
    public void onDetachedFromWindow() {
        if (spaceshipImage != null) {
            spaceshipImage.clearAnimation();
        }
        super.onDetachedFromWindow();
    }
}
