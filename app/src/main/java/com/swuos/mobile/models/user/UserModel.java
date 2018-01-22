package com.swuos.mobile.models.user;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.swuos.mobile.api.ApiUrl;
import com.swuos.mobile.api.HttpMethod;
import com.swuos.mobile.api.HttpRequester;
import com.swuos.mobile.api.OnHttpResultListener;
import com.swuos.mobile.api.OnResultListener;
import com.swuos.mobile.app.BaseModel;
import com.swuos.mobile.entity.AccountInfo;
import com.swuos.mobile.entity.UserInfo;
import com.swuos.mobile.models.cache.CacheKey;
import com.swuos.mobile.utils.encode.RSAUtil;
import com.swuos.mobile.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 用户管理器
 * Created by wangyu on 2018/1/20.
 */

public class UserModel extends BaseModel {

    private static final String USER_CACHE_NAME = "user_info";
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    private UserInfo mUserInfo;
    private boolean isNeedLogin = false;

    @Override
    public void onModelCreate(Application application) {
        super.onModelCreate(application);
        sp = application.getSharedPreferences(USER_CACHE_NAME, Context.MODE_PRIVATE);
        spEditor = sp.edit();
        spEditor.apply();
        initUserInfo();
    }

    private void initUserInfo() {
        String userInfoString = sp.getString(CacheKey.CURRENT_USER.getKey(), "");
        if (TextUtils.isEmpty(userInfoString)) {
            isNeedLogin = true;
        } else {
            try {
                JSONObject jsonObject = new JSONObject(userInfoString);
                mUserInfo = JsonUtil.toObject(jsonObject, UserInfo.class);
                isNeedLogin = false;
            } catch (JSONException e) {
                e.printStackTrace();
                isNeedLogin = true;
            }
        }
    }

    public void login(AccountInfo accountInfo, @NonNull final OnResultListener<UserInfo> listener) {
        String swuLoginJsons = String.format("{\"serviceAddress\":\"https://uaaap.swu.edu.cn/cas/ws/acpInfoManagerWS\",\"serviceType\":\"soap\",\"serviceSource\":\"td\",\"paramDataFormat\":\"xml\",\"httpMethod\":\"POST\",\"soapInterface\":\"getUserInfoByUserName\",\"params\":{\"userName\":\"%s\",\"passwd\":\"%s\",\"clientId\":\"yzsfwmh\",\"clientSecret\":\"1qazz@WSX3edc$RFV\",\"url\":\"http://i.swu.edu.cn\"},\"cDataPath\":[],\"namespace\":\"\",\"xml_json\":\"\",\"businessServiceName\":\"uaaplogin\"}", accountInfo.getUserName(), accountInfo.getUserPwd());
        RequestBody body = new FormBody.Builder()
                .add("serviceInfo", RSAUtil.encrypt(swuLoginJsons))
                .build();
        HttpRequester httpRequester = new HttpRequester.Builder(ApiUrl.LOGIN_URL)
                .body(body)
                .method(HttpMethod.POST)
                .build();
        httpRequester.execute(new OnHttpResultListener<UserInfo>() {
            @Override
            public void onResult(int code, UserInfo userInfo) {
                listener.onResult(code, userInfo);
            }
        });
    }

    @Nullable
    public UserInfo getUserInfo() {
        if (mUserInfo == null) return null;
        return mUserInfo.clone();
    }
}
