package com.swuos.mobile.api;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;

import okhttp3.Request;

/**
 * ac请求
 * Created by wangyu on 2018/5/11.
 */

@RequestMethod(HttpMethod.POST)
public abstract class AcHostRequester<T> extends HttpRequester<T> {
    public AcHostRequester(@NonNull OnResultListener<T> listener) {
        super(listener);
    }

    @Override
    protected ApiInterface getApi() {
        return getHttpModel().get(ApiConfig.AcHostApi.class);
    }

    @Override
    protected void preHandleRequest(@NonNull Request.Builder reqBuilder) {
        super.preHandleRequest(reqBuilder);
        reqBuilder.addHeader("acToken", getUserModel().getAccountInfo().getAcToken());
    }
}
