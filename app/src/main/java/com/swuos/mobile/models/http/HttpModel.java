package com.swuos.mobile.models.http;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;


import com.gallops.mobile.jmvclibrary.app.BaseModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http执行环境
 * Created by wangyu on 2018/1/20.
 */

public class HttpModel extends BaseModel {

    private static ExecutorService executorService;
    private static Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onModelCreate(Application application) {
        super.onModelCreate(application);
        executorService = Executors.newCachedThreadPool();
    }

    public ExecutorService getExecutor() {
        return executorService;
    }

    public Handler getHandler() {
        return handler;
    }
}
