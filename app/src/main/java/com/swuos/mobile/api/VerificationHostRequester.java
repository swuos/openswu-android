package com.swuos.mobile.api;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpRequester;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;

/**
 * 账号验证请求
 * Created by wangyu on 2018/5/11.
 */

public abstract class VerificationHostRequester<T> extends HttpRequester<T> {
    public VerificationHostRequester(@NonNull OnResultListener<T> listener) {
        super(listener);
    }

    @Override
    protected ApiInterface getApi() {
        return getHttpModel().get(ApiConfig.VerificationHostApi.class);
    }
}
