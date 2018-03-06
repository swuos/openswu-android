package com.swuos.mobile.api;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 请求入口，主要封装日志打印，方便查看
 * Created by wangyu on 2018/1/20.
 */

public abstract class HttpRequester<T> extends BaseRequester {

    protected OnResultListener<T> listener;

    public HttpRequester(@NonNull OnResultListener<T> listener) {
        this.listener = listener;
    }

    @Override
    protected void onError(Exception exception) {
        exception.printStackTrace();
        listener.onResult(OnResultListener.RESULT_NET_ERROR, null, exception.getMessage());
    }

    @Override
    protected void onResult(int code, JSONObject content, String msg) throws JSONException {
        T data = null;
        if (code == OnResultListener.RESULT_DATA_OK) {
            data = onDumpData(content);
        }
        listener.onResult(code, data, msg);
    }

    protected abstract T onDumpData(JSONObject jsonObject) throws JSONException;
}
