package com.swuos.mobile.ui;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jianyuyouhun.permission.library.EZPermission;
import com.jianyuyouhun.permission.library.OnRequestPermissionResultListener;
import com.jianyuyouhun.permission.library.PRequester;
import com.swuos.mobile.app.BaseActivity;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.ui.tab.MainActivity;
import com.swuos.mobile.ui.user.LoginActivity;
import com.swuos.mobile.utils.injector.Model;

/**
 * 启动页
 * Created by wangyu on 2018/3/7.
 */

public class EntryActivity extends BaseActivity {

    @Model
    private UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    /**
     * 申请存储权限
     */
    private void requestPermission() {
        EZPermission.Companion.getInstance().requestPermission(
                this,
                new PRequester(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                new OnRequestPermissionResultListener() {
                    @Override
                    public void onRequestSuccess(String s) {
                        judgeForwardUI();
                    }

                    @Override
                    public void onRequestFailed(String s) {
                        judgeForwardUI();
                    }
                });
    }

    /**
     * 判断登录状态
     */
    private void judgeForwardUI() {
        if (userModel.isNeedLogin()) {
            postStartActivity(LoginActivity.class, 2000);
        } else {
            userModel.loginQuiet();
            postStartActivity(MainActivity.class, 2000);
        }
    }
}
