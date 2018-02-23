package com.nl.develop.simple;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.nl.develop.net.IRequest;
import com.nl.develop.net.NetCallBack;
import com.nl.develop.net.NetFactory;
import com.nl.develop.net.NetImpNative;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private NetFactory netFactory;
    private IRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        netFactory = new NetImpNative();
    }

    public void getBaidu(View view) {
        request = netFactory.get("https://www.baidu.com/", new NetCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: ");
            }

            @Override
            public void onFailure(IOException e) {
                Log.i(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: ");
            }
        });
    }

    public void postLoss(View view) {
        String url = "https://icore-alad-stg1.pingan.com.cn:14443/alad/mobile/EDamageAction/initInsuranceLossInfo";
        ArrayMap<String, String> params = new ArrayMap<>();
        params.put("siteCode", "MACN");
        params.put("signature", "VXJLRHBVVjhhQ2hVaDBaUWJYbDRvSGRBOUVHU0tma2ZLSERKRHI5K2hvR1V2RDdsSnBDUFRHa3c0WEZpWXJxVis4azJUYnlWV252b1IwK1ZaUXJpWlBRSzF4MmlFR2x2YlBYMkx6UHM5azBDQTlnNEcyYUR4WGdvK2hGYnVLZTFkVVdncFJTZzhzT3dCWGRGOUtwMk93aTFnbmNQa1ZaYXZqNHloTHJTNTRVPQ%3D%3D&");
        params.put("reqData", "eyJhY2Nlc3NVbSI6Iua1i%2BivleWumuaNn%2BWRmCIsImluc3VyYW5jZUNvbXBhbnlObyI6Ik1BQ04iLCJsb3NzU2VxTm8iOiIxIiwidGltZSI6MTUxOTM1NTI5NjE3OH0%3D%3D%3D&");
        request = netFactory.post(url, params, new NetCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: ");
            }

            @Override
            public void onFailure(IOException e) {
                Log.i(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: ");
            }
        });
    }

    public void cancel(View view) {
        if (request != null) {
            request.cancel();
        }
    }
}
