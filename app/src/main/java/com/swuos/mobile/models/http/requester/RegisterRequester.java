package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.HttpRequester;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.gallops.mobile.jmvclibrary.utils.json.JsonUtil;
import com.swuos.mobile.entity.RegisterInfo;
import com.swuos.mobile.models.http.ApiHostUrl;
import com.swuos.mobile.models.http.RouteRegister;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

public class RegisterRequester extends HttpRequester<RegisterInfo> {


    String phoneNumber;
    String password;
    String verificationCode;

    public RegisterRequester(String phoneNumber,
                             String password,
                             String verificationCode, @NonNull OnResultListener<RegisterInfo> listener) {
        super(listener);
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.verificationCode = verificationCode;
    }

    @Override
    protected RegisterInfo onDumpData(JSONObject jsonObject) throws JSONException {
        //registerInfo
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setAvatar(jsonObject.optString("avatar"));
        registerInfo.setNickname(jsonObject.optString("nickname"));
        registerInfo.setAcToken(jsonObject.optString("acToken"));
        return registerInfo;
    }

    @NonNull
    @Override
    protected HttpMethod setMethod() {
        return HttpMethod.POST;
    }

    @Override
    protected ApiInterface getApi() {
        return new ApiHostUrl();
    }

    @NonNull
    @Override
    protected RouteInterface setRoute() {
        return RouteRegister.ROUTE_REGISTER;
    }

    @Override
    protected String setReqUrl() {
        return super.setReqUrl();
    }


    @Override
    protected String appendUrl(String url) {
        return url;
    }

    @NonNull
    @Override
    protected RequestBody onPutParams(FormBody.Builder builder) {
        if (builder == null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("phoneNumber", phoneNumber);
                jsonObject.put("password", password);
                jsonObject.put("verificationCode", verificationCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return FormBody.create(MediaType.parse("application/json"), jsonObject.toString());
        }
        return null;//get请求直接返回builder
    }
}
