package com.swuos.mobile.api;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpRequester;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;

/**
 * ac请求
 * Created by wangyu on 2018/5/11.
 */

public abstract class AcHostRequester<T> extends HttpRequester<T> {
    public AcHostRequester(@NonNull OnResultListener<T> listener) {
        super(listener);
    }

    @Override
    protected ApiInterface getApi() {
        return getHttpModel().get(ApiConfig.AcHostApi.class);
    }

}
