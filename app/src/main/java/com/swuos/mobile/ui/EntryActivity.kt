package com.swuos.mobile.ui

import android.Manifest
import android.os.Bundle
import com.jianyuyouhun.permission.library.EZPermission
import com.jianyuyouhun.permission.library.PRequester
import com.swuos.mobile.app.BaseActivity
import com.swuos.mobile.models.user.UserModel
import com.swuos.mobile.ui.tab.MainActivity
import com.swuos.mobile.ui.user.LoginActivity
import com.swuos.mobile.utils.kt.proxy.bindModel

/**
 * 启动页
 * Created by wangyu on 2018/3/7.
 */

class EntryActivity : BaseActivity() {

    private val userModel by bindModel(UserModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }

    /**
     * 申请存储权限
     */
    private fun requestPermission() {
        EZPermission.instance.requestPermission(
                this,
                PRequester(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                onSuccess = {
                    judgeForwardUI()
                },
                onFailed = {
                    judgeForwardUI()
                })
    }

    /**
     * 判断登录状态
     */
    private fun judgeForwardUI() {
        if (!userModel.isNeedLogin) {
            postStartActivity(LoginActivity::class.java, 2000)
        } else {
            //            userModel.loginQuiet()
            postStartActivity(MainActivity::class.java, 2000)
        }
    }
}
