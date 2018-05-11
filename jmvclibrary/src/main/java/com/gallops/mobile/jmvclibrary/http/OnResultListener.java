package com.gallops.mobile.jmvclibrary.http;

/**
 * 通用请求回调
 * Created by wangyu on 2017/12/7.
 */

public interface OnResultListener<T> extends ErrorCode {
    void onResult(int code, T t, String msg);
}
