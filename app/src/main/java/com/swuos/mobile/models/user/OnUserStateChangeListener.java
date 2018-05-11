package com.swuos.mobile.models.user;

import com.swuos.mobile.entity.AccountInfo;
import com.swuos.mobile.entity.LoginInfo;

/**
 * 用户状态变化
 * Created by wangyu on 2018/1/22.
 */

public interface OnUserStateChangeListener {
    /**
     * 账号登录
     *
     * @param accountInfo
     */
    void onLogin(AccountInfo accountInfo);

    /**
     * 账号登出
     *
     * @param accountInfo
     */
    void onLogout(AccountInfo accountInfo);
}
