package com.nl.develop.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by NiuLei on 2018/2/23.
 * 适配okHttp回调
 */

public class OkHttpCallBackAdapter implements NetCallBack, Callback {
    private NetCallBack netCallBack;

    public OkHttpCallBackAdapter(NetCallBack netCallBack) {
        this.netCallBack = netCallBack;
    }

    @Override
    public void onStart() {
        if (netCallBack != null) {
            netCallBack.onStart();
        }
    }

    @Override
    public void onFailure(IOException e) {
        if (netCallBack != null) {
            netCallBack.onFailure(e);
        }
    }

    @Override
    public void onResponse(String response) {
        if (netCallBack != null) {
            netCallBack.onResponse(response);
        }
    }

    @Override
    public void onFinish() {
        if (netCallBack != null) {
            netCallBack.onFinish();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        onFailure(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String responseString = null;
        ResponseBody body = response.body();
        if (body != null) {
            responseString = body.string();
        }
        onResponse(responseString);
    }
}
