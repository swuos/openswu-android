package com.swuos.mobile.models.user;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

import java.util.ArrayList;
import java.util.List;

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

    private List<OnUserStateChangeListener> onUserStateChangeListenerList = new ArrayList<>();

    @Override
    public void onModelCreate(Application application) {
        super.onModelCreate(application);
        sp = application.getSharedPreferences(USER_CACHE_NAME, Context.MODE_PRIVATE);
        spEditor = sp.edit();
        spEditor.apply();
        initUserInfo();
    }

    @Override
    public void onAllModelCreate() {
        super.onAllModelCreate();
        if (!isNeedLogin) {
            notifyUserLogin();
        }
    }

    private void initUserInfo() {
        mUserInfo = readUserInfo();
        isNeedLogin = mUserInfo == null;
    }

    public void login(final AccountInfo accountInfo, @Nullable final OnResultListener<UserInfo> listener) {
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
                if (code == RESULT_DATA_OK) {
                    saveAccountInfo(accountInfo);
                    mUserInfo = userInfo;
                    saveUserInfo(userInfo);
                    if (listener != null) listener.onResult(RESULT_DATA_OK, userInfo);
                    notifyUserLogin();
                } else {
                    if (listener != null) listener.onResult(code, null);
                }
            }
        });
    }

    public void loginQuiet() {
        AccountInfo accountInfo = readAccountInfo();
        if (accountInfo == null) {
            throw new IllegalArgumentException("找不到lastAccountInfo");
        }
        login(accountInfo, null);
    }

    @Nullable
    private UserInfo readUserInfo() {
        UserInfo userInfo;
        String userInfoString = sp.getString(CacheKey.CURRENT_USER.getKey(), "");
        try {
            JSONObject jsonObject = new JSONObject(userInfoString);
            userInfo = JsonUtil.toObject(jsonObject, UserInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
            userInfo = null;
        }
        return userInfo;
    }

    public void saveUserInfo(UserInfo userInfo) {
        spEditor.putString(CacheKey.CURRENT_USER.getKey(), JsonUtil.toJSONObject(userInfo).toString()).apply();
    }

    @Nullable
    private AccountInfo readAccountInfo() {
        AccountInfo accountInfo;
        String accountString = sp.getString(CacheKey.LAST_ACCOUNT.getKey(), "");
        try {
            JSONObject jsonObject = new JSONObject(accountString);
            accountInfo = JsonUtil.toObject(jsonObject, AccountInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
            accountInfo = null;
        }
        return accountInfo;
    }

    private void saveAccountInfo(AccountInfo accountInfo) {
        spEditor.putString(CacheKey.LAST_ACCOUNT.getKey(), JsonUtil.toJSONObject(accountInfo).toString()).apply();
    }

    /**
     * 是否需要登录
     * @return
     */
    public boolean isNeedLogin() {
        return isNeedLogin;
    }

    @Nullable
    public UserInfo getUserInfo() {
        if (mUserInfo == null) return null;
        return mUserInfo.clone();
    }

    /**
     * 注册用户状态变化监听事件
     * @param listener  回调
     */
    public void addOnUserStateChangeListener(OnUserStateChangeListener listener) {
        onUserStateChangeListenerList.add(listener);
    }

    /**
     * 注销用户状态变化监听事件
     * @param listener  回调
     */
    public void removeOnUserStateChangeListener(OnUserStateChangeListener listener) {
        onUserStateChangeListenerList.remove(listener);
    }

    /**
     * 用户登录
     */
    private void notifyUserLogin() {
        for (OnUserStateChangeListener listener : onUserStateChangeListenerList) {
            listener.onLogin(mUserInfo.clone());
        }
    }

    /**
     * 用户登出
     */
    private void notifyUserLogout() {
        for (OnUserStateChangeListener listener : onUserStateChangeListenerList) {
            listener.onLogout(mUserInfo.clone());
        }
    }

    /**
     * wifi登录
     */
    private void notifyUserWifiLogin() {
        for (OnUserStateChangeListener listener : onUserStateChangeListenerList) {
            listener.onWifiLogin(null);
        }
    }

    /**
     * wifi登出
     */
    private void notifyUserWifiLogout() {
        for (OnUserStateChangeListener listener : onUserStateChangeListenerList) {
            listener.onWifiLogout(null);
        }
    }
}
