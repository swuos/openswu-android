package com.swuos.mobile.api;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;

import okhttp3.Request;

/**
 * 账号验证请求
 * Created by wangyu on 2018/5/11.
 */
@RequestMethod(HttpMethod.POST)
public abstract class VerificationHostRequester<T> extends HttpRequester<T> {
    public VerificationHostRequester(@NonNull OnResultListener<T> listener) {
        super(listener);
    }

    @Override
    protected ApiInterface getApi() {
        return getHttpModel().get(ApiConfig.VerificationHostApi.class);
    }

    @Override
    protected void preHandleRequest(@NonNull Request.Builder reqBuilder) {
        super.preHandleRequest(reqBuilder);
        // TODO: 2018/5/22 第一次打开app时没有账号 崩溃
        reqBuilder.addHeader("acToken", getUserModel().getAccountInfo().getAcToken());
    }
}