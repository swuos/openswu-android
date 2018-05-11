package com.gallops.mobile.jmvclibrary.http.creator;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * 入参封装接口定义
 * Created by wangyu on 2018/5/11.
 */

public interface BodyCreateAction {
    RequestBody onCreate(Map<String, Object> params);
}
