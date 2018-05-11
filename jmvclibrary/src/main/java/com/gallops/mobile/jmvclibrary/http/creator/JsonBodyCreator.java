package com.gallops.mobile.jmvclibrary.http.creator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * json格式参数解析
 * Created by wangyu on 2018/5/11.
 */

public class JsonBodyCreator implements BodyCreateAction {
    @Override
    public RequestBody onCreate(Map<String, Object> params) {
        JSONObject jsonObject = new JSONObject();
        for (String key : params.keySet()) {
            try {
                jsonObject.put(key, params.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    }
}
