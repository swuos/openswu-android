package com.swuos.mobile.models.user;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.gallops.mobile.jmvclibrary.app.BaseModel;
import com.gallops.mobile.jmvclibrary.app.JApp;
import com.gallops.mobile.jmvclibrary.http.ErrorCode;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.utils.json.JsonUtil;
import com.swuos.mobile.entity.AccountInfo;
import com.swuos.mobile.entity.LoginInfo;
import com.swuos.mobile.entity.RegisterInfo;
import com.swuos.mobile.models.http.requester.LoginRequester;
import com.swuos.mobile.models.http.requester.RegisterRequester;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理器
 * Created by wangyu on 2018/1/20.
 */

public class UserModel extends BaseModel {

    private static final String USER_CACHE_NAME = "user_info";
    private static final String GUEST_ID = "1001";//为bugly统计配置，逻辑上不存在访客
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    private AccountInfo accountInfo;
    private boolean isNeedLogin = false;
    private BuglyProxy buglyProxy;

    private List<OnUserStateChangeListener> onUserStateChangeListenerList = new ArrayList<>();

    @Override
    public void onModelCreate(Application application) {
        super.onModelCreate(application);
        sp = application.getSharedPreferences(USER_CACHE_NAME, Context.MODE_PRIVATE);
        spEditor = sp.edit();
        spEditor.apply();
        initUserInfo();
//        buglyProxy = new BuglyProxy(this);
    }

    @Override
    public void onAllModelCreate() {
        super.onAllModelCreate();
        if (!isNeedLogin) {
            notifyUserLogin();
        }
    }

    private void initUserInfo() {
        accountInfo = readAccountInfo();
        isNeedLogin = accountInfo == null;
    }

    /**
     * 登录
     *
     * @param  password,String phoneNumber 账号信息
     * @param listener   回调
     */
    public void login(String password,String phoneNumber,@Nullable final OnResultListener<LoginInfo> listener) {
        if (!isNeedLogin) throw new RuntimeException("请勿重复调用登录");
        LoginRequester loginRequester = new LoginRequester(password,phoneNumber,(code, userInfo, msg) -> {
            if (code == ErrorCode.RESULT_DATA_OK) {
                accountInfo = new AccountInfo();
                accountInfo.setPassword(password);
                accountInfo.setPhoneNumber(phoneNumber);
                accountInfo.setAcToken(userInfo.getAcToken());
                saveAccountInfo(accountInfo);
                isNeedLogin = false;
//                saveUserInfo(userInfo);
                if (listener != null) listener.onResult(ErrorCode.RESULT_DATA_OK, userInfo, msg);
                notifyUserLogin();
            } else {
                if (listener != null) listener.onResult(code, null, msg);
            }
        });
        loginRequester.execute();
    }


    public void register(String phoneNumber,String password,String verificationCode, @Nullable final OnResultListener<RegisterInfo> listener) {
        if (!isNeedLogin) throw new RuntimeException("请勿重复调用登录");
        RegisterRequester loginRequester = new RegisterRequester(phoneNumber,password,verificationCode, (code, registerInfo, msg) -> {
            if (code == ErrorCode.RESULT_DATA_OK) {
                accountInfo = new AccountInfo();
                accountInfo.setPassword(password);
                accountInfo.setPhoneNumber(phoneNumber);
                saveAccountInfo(accountInfo);
                isNeedLogin = false;

                if (listener != null)
                    listener.onResult(ErrorCode.RESULT_DATA_OK, registerInfo, msg);
                notifyUserLogin();
            } else {
                if (listener != null) listener.onResult(code, null, msg);
            }
        });
        loginRequester.execute();
    }
//    public void loginQuiet() {
//        AccountInfo accountInfo = readAccountInfo();
//        if (accountInfo == null) {
//            throw new IllegalArgumentException("找不到lastAccountInfo");
//        }
//        login(accountInfo, null);
//    }

    /**
     * 登出，目前没有接口，模拟操作
     *
     * @param listener 回调
     */
    public void logout(@Nullable OnResultListener<Void> listener) {
        if (isNeedLogin) throw new RuntimeException("请勿重复调用退出");
        JApp.getHandler().postDelayed(() -> {
            notifyUserLogout();
            clearUserInfo();
            initUserInfo();
            if (listener != null) {
                listener.onResult(ErrorCode.RESULT_DATA_OK, null, "");
            }
        }, 1000);
    }

    @Nullable
    private AccountInfo readUserInfo() {
        AccountInfo accountInfo;
        String userInfoString = sp.getString(UserCacheKey.CURRENT_USER.getKey(), "");
        try {
            JSONObject jsonObject = new JSONObject(userInfoString);
            accountInfo = JsonUtil.toObject(jsonObject, AccountInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
            accountInfo = null;
        }
        return accountInfo;
    }

    public void saveUserInfo(AccountInfo accountInfo) {
        accountInfo = accountInfo.clone();
        spEditor.putString(UserCacheKey.CURRENT_USER.getKey(), JsonUtil.toJSONObject(accountInfo).toString()).apply();
    }

    private void clearUserInfo() {
        spEditor.putString(UserCacheKey.CURRENT_USER.getKey(), "{}");
        spEditor.clear();
        spEditor.apply();
        spEditor.commit();
    }

    @Nullable
    private AccountInfo readAccountInfo() {
        AccountInfo accountInfo;
        String accountString = sp.getString(UserCacheKey.LAST_ACCOUNT.getKey(), "");
        try {
            JSONObject jsonObject = new JSONObject(accountString);
            accountInfo = JsonUtil.toObject(jsonObject, AccountInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
            accountInfo = null;
        }
        return accountInfo;
    }

    public void saveAccountInfo(AccountInfo accountInfo) {
        spEditor.putString(UserCacheKey.LAST_ACCOUNT.getKey(), JsonUtil.toJSONObject(accountInfo).toString()).apply();
    }

    /**
     * 是否需要登录
     *
     * @return
     */
    public boolean isNeedLogin() {
        return isNeedLogin;
    }

    /**
     * 用户对象可为空，要注意使用逻辑
     *
     * @return
     */
    public AccountInfo getAccountInfo() {
        if (accountInfo == null) return null;
        return accountInfo;
    }

    public String getSwuId() {
        AccountInfo accountInfo = getAccountInfo();
        if (accountInfo == null) {
            return GUEST_ID;
        } else {
            return accountInfo.getSwuId();
        }
    }

    /**
     * 注册用户状态变化监听事件
     *
     * @param listener 回调
     */
    public void addOnUserStateChangeListener(OnUserStateChangeListener listener) {
        onUserStateChangeListenerList.add(listener);
    }

    /**
     * 注销用户状态变化监听事件
     *
     * @param listener 回调
     */
    public void removeOnUserStateChangeListener(OnUserStateChangeListener listener) {
        onUserStateChangeListenerList.remove(listener);
    }

    /**
     * 用户登录
     */
    private void notifyUserLogin() {
        for (OnUserStateChangeListener listener : onUserStateChangeListenerList) {
            listener.onLogin(accountInfo.clone());
        }
    }

    /**
     * 用户登出
     */
    private void notifyUserLogout() {
        for (OnUserStateChangeListener listener : onUserStateChangeListenerList) {
            listener.onLogout(accountInfo.clone());
        }
    }

}
