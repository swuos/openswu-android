package com.swuos.mobile.api;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;

import okhttp3.Request;

/**
 * Freegatty请求
 * Created by wangyu on 2018/5/11.
 */

@RequestMethod(HttpMethod.POST)
public abstract class FreegattyHostRequester<T> extends HttpRequester<T> {
    public FreegattyHostRequester(@NonNull OnResultListener<T> listener) {
        super(listener);
    }

    @Override
    protected ApiInterface getApi() {
        return getHttpModel().get(ApiConfig.FreegattyHostApi.class);
    }

    @Override
    protected void preHandleRequest(@NonNull Request.Builder reqBuilder) {
        super.preHandleRequest(reqBuilder);
        reqBuilder.addHeader("acToken", getUserModel().getAccountInfo().getAcToken());
    }
}
