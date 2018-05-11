package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.HttpRequester;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.gallops.mobile.jmvclibrary.utils.json.JsonUtil;
import com.swuos.mobile.entity.LoginInfo;
import com.swuos.mobile.models.http.ApiHostUrl;
import com.swuos.mobile.models.http.RouteLogin;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 登录请求
 * Created by wangyu on 2018/3/6.
 */

public class LoginRequester extends HttpRequester<LoginInfo> {


    String phoneNumber;
    String password;

    public LoginRequester(String phoneNumber,
                          String password, @NonNull OnResultListener<LoginInfo> listener) {
        super(listener);
        this.phoneNumber = phoneNumber;
        this.password = password;

    }

    @Override
    protected LoginInfo onDumpData(JSONObject jsonObject) throws JSONException {
        //手动组装userInfo
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAcToken(jsonObject.optString("acToken"));
        return loginInfo;
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
        return RouteLogin.ROUTE_LOGIN;
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
        if (builder == null){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("phoneNumber", phoneNumber);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return FormBody.create(MediaType.parse("application/json"), jsonObject.toString());}
        return null;//get请求直接返回builder
    }
}
