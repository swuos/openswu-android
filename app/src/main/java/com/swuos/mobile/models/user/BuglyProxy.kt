package com.swuos.mobile.models.user

import com.gallops.mobile.jmvclibrary.app.JApp
import com.gallops.mobile.jmvclibrary.utils.kt.lgD
import com.swuos.mobile.entity.AccountInfo
import com.swuos.mobile.entity.LoginInfo

import com.tencent.bugly.crashreport.CrashReport

/**
 * bugly统计
 * Created by wangyu on 2018/2/26.
 */
class BuglyProxy(private val userModel: UserModel) {
    private val TAG = BuglyProxy::javaClass.name
    private val onUserStateChangeListener: OnUserStateChangeListener = object : OnUserStateChangeListener {
        override fun onLogin(accountInfo: AccountInfo?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            lgD(TAG, "bugly account login: " + accountInfo?.swuId)
            setBugly()
        }

        override fun onLogout(accountInfo: AccountInfo?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            lgD(TAG, "bugly account logout: " + accountInfo?.swuId)
            setBugly()
        }

    }

    init {
        userModel.addOnUserStateChangeListener(onUserStateChangeListener)
        setBugly()
    }

    private fun setBugly() {
        if (!JApp.isDebug()) {
            CrashReport.setUserId(userModel.swuId + "")
        }
    }
}
