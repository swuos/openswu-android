package com.gallops.mobile.jmvclibrary.models;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.app.BaseModel;
import com.gallops.mobile.jmvclibrary.http.ApiInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Created by wangyu on 2018/3/27.
 */

public class HttpModel extends BaseModel {
    private ExecutorService executorService;
    private Handler handler;
    private Map<String, ApiInterface> apiMap = new HashMap<>();

    @Override
    public void onModelCreate(Application application) {
        super.onModelCreate(application);
        executorService = Executors.newCachedThreadPool();
        handler = new Handler(Looper.getMainLooper());
    }

    public final void registerApi(ApiInterface apiInterface) {
        String key = apiInterface.getClass().getName();
        apiMap.put(key, apiInterface);
    }

    @NonNull
    public final ApiInterface get(Class<? extends ApiInterface> apiCls) {
        ApiInterface apiInterface = apiMap.get(apiCls.getName());
        if (apiInterface == null) throw new RuntimeException("请注册apiInterface: " + apiCls.getName());
        return apiInterface;
    }

    public final void clearApi() {
        apiMap.clear();
    }

    public ExecutorService getExecutor() {
        return executorService;
    }

    public Handler getHandler() {
        return handler;
    }
}
