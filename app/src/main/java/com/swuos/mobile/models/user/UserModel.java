package com.swuos.mobile.models.user;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.swuos.mobile.api.ErrorCode;
import com.swuos.mobile.api.OnResultListener;
import com.swuos.mobile.app.BaseModel;
import com.swuos.mobile.entity.AccountInfo;
import com.swuos.mobile.entity.UserInfo;
import com.swuos.mobile.utils.json.JsonUtil;

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

    private UserInfo mUserInfo;
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
        buglyProxy = new BuglyProxy(this);
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

    /**
     * 登录
     * @param accountInfo   账号信息
     * @param listener      回调
     */
    public void login(final AccountInfo accountInfo, @Nullable final OnResultListener<UserInfo> listener) {
        if (!isNeedLogin) throw new RuntimeException("请勿重复调用登录");
        LoginRequester loginRequester = new LoginRequester(accountInfo, (code, userInfo, msg) -> {
            if (code == ErrorCode.RESULT_DATA_OK) {
                saveAccountInfo(accountInfo);
                isNeedLogin = false;
                saveUserInfo(userInfo);
                if (listener != null) listener.onResult(ErrorCode.RESULT_DATA_OK, userInfo, msg);
                notifyUserLogin();
            } else {
                if (listener != null) listener.onResult(code, null, msg);
            }
        });
        loginRequester.execute();
    }

    public void loginQuiet() {
        AccountInfo accountInfo = readAccountInfo();
        if (accountInfo == null) {
            throw new IllegalArgumentException("找不到lastAccountInfo");
        }
        login(accountInfo, null);
    }

    /**
     * 登出，目前没有接口，LogoutRequester逻辑是模拟操作
     * @param listener   回调
     */
    public void logout(@Nullable OnResultListener<JSONObject> listener) {
        if (isNeedLogin) throw new RuntimeException("请勿重复调用退出");
        new LogoutRequester((code, jsonObject, msg) -> {
            if (code == ErrorCode.RESULT_DATA_OK) {
                notifyUserLogout();
                clearUserInfo();
                initUserInfo();
            }
            if (listener != null) {
                listener.onResult(code, jsonObject, msg);
            }
        }).execute();
    }

    @Nullable
    private UserInfo readUserInfo() {
        UserInfo userInfo;
        String userInfoString = sp.getString(UserCacheKey.CURRENT_USER.getKey(), "");
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
        mUserInfo = userInfo.clone();
        spEditor.putString(UserCacheKey.CURRENT_USER.getKey(), JsonUtil.toJSONObject(userInfo).toString()).apply();
    }

    public void clearUserInfo() {
        spEditor.putString(UserCacheKey.CURRENT_USER.getKey(), "{}");
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

    private void saveAccountInfo(AccountInfo accountInfo) {
        spEditor.putString(UserCacheKey.LAST_ACCOUNT.getKey(), JsonUtil.toJSONObject(accountInfo).toString()).apply();
    }

    /**
     * 是否需要登录
     * @return
     */
    public boolean isNeedLogin() {
        return isNeedLogin;
    }

    /**
     * 用户对象可为空，要注意使用逻辑
     * @return
     */
    public UserInfo getUserInfo() {
        if (mUserInfo == null) return null;
        return mUserInfo.clone();
    }

    public String getUserId() {
        if (getUserInfo() == null) {
            return GUEST_ID;
        } else {
            return getUserInfo().getStudentId();
        }
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

}
