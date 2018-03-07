package com.swuos.mobile.models.user;

import android.support.annotation.NonNull;

import com.swuos.mobile.api.ApiUrl;
import com.swuos.mobile.api.HttpMethod;
import com.swuos.mobile.api.HttpRequester;
import com.swuos.mobile.api.OnResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;

/**
 * 登出请求，模拟逻辑，后台暂未提供接口
 * Created by wangyu on 2018/3/7.
 */

public class LogoutRequester extends HttpRequester<JSONObject> {

    public LogoutRequester(@NonNull OnResultListener<JSONObject> listener) {
        super(listener);
    }

    @Override
    protected JSONObject onDumpData(JSONObject jsonObject) throws JSONException {
        return jsonObject;
    }

    @NonNull
    @Override
    protected HttpMethod setMethod() {
        return HttpMethod.GET;
    }

    @NonNull
    @Override
    protected ApiUrl setApiUrl() {
        return ApiUrl.LOGOUT_URL;
    }

    @NonNull
    @Override
    protected FormBody.Builder onPutParams(FormBody.Builder builder) {
        return builder.add("swuId", getUserModel().getUserId());
    }
}
