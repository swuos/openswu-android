package com.gallops.mobile.jmvclibrary.http.creator;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 表单参数解析
 * Created by wangyu on 2018/5/11.
 */

public class FormBodyCreator implements BodyCreateAction {
    @Override
    public RequestBody onCreate(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key).toString());
        }
        return builder.build();
    }
}
