package com.swuos.mobile.ui

import android.Manifest
import android.os.Bundle
import com.gallops.mobile.jmvclibrary.app.BaseActivity
import com.gallops.mobile.jmvclibrary.utils.kt.proxy.bindModel
import com.jianyuyouhun.permission.library.EZPermission
import com.jianyuyouhun.permission.library.PRequester
import com.swuos.mobile.R
import com.swuos.mobile.models.user.UserModel
import com.swuos.mobile.ui.tab.MainActivity
import com.swuos.mobile.ui.user.LoginActivity


/**
 * 启动页
 * Created by wangyu on 2018/3/7.
 */

class EntryActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.a_entry

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
            postStartActivity(MainActivity::class.java, 10)
        } else {
            //            userModel.loginQuiet()
            postStartActivity(LoginActivity::class.java, 1000)
        }
    }
}
