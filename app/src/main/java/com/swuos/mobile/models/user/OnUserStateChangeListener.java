package com.swuos.mobile.models.user;

import com.swuos.mobile.entity.UserInfo;
import com.swuos.mobile.entity.UserWifiInfo;

/**
 * 用户状态变化
 * Created by wangyu on 2018/1/22.
 */

public interface OnUserStateChangeListener {
    /**
     * 账号登录
     *
     * @param userInfo
     */
    void onLogin(UserInfo userInfo);

    /**
     * 账号登出
     *
     * @param userInfo
     */
    void onLogout(UserInfo userInfo);

    /**
     * WiFi登录
     */
    void onWifiLogin(UserWifiInfo wifiInfo);

    /**
     * WiFi登出
     */
    void onWifiLogout(UserWifiInfo wifiInfo);
}
