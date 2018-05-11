package com.gallops.mobile.jmvclibrary.http;

/**
 * 默认的api实现
 * Created by wangyu on 2018/3/27.
 */

public class EmptyApiImpl implements ApiInterface {
    @Override
    public String getApiUrl() {
        return "";
    }
}
