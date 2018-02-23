package com.nl.develop.net;

import java.util.concurrent.Future;

/**
 * Created by NiuLei on 2018/2/23.
 * 适配线程池取消
 */

class RequestFutureAdapter implements IRequest {
    private Future future;

    public RequestFutureAdapter(Future future) {
        this.future = future;
    }

    @Override
    public void cancel() {
        if (future != null) {
            future.cancel(true);
        }
    }
}
