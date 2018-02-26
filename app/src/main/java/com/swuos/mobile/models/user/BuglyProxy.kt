package com.swuos.mobile.models.user

import com.swuos.mobile.app.App
import com.swuos.mobile.entity.UserInfo
import com.tencent.bugly.crashreport.CrashReport

/**
 * bugly统计
 * Created by wangyu on 2018/2/26.
 */
class BuglyProxy(private val userModel: UserModel) {
    private val onUserStateChangeListener: OnUserStateChangeListener = object : OnUserStateChangeListener {
        override fun onLogin(userInfo: UserInfo?) {
            setBugly()
        }

        override fun onLogout(userInfo: UserInfo?) {
            setBugly()
        }
    }

    init {
        userModel.addOnUserStateChangeListener(onUserStateChangeListener)
        setBugly()
    }

    private fun setBugly() {
        if (!App.isDebug()) {
            CrashReport.setUserId(userModel.userId + "")
        }
    }
}
