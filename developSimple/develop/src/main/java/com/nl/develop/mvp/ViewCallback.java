package com.nl.develop.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by NiuLei on 2018/1/29.
 */

public interface ViewCallback {
    void onCreate(@Nullable Bundle savedInstanceState);

    void onDestroy();

    void onStart();

    void onStop();

    void onResume();

    void onPause();

    void onWindowFocusChanged(boolean hasFocus);

    boolean onBackPressed();

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
