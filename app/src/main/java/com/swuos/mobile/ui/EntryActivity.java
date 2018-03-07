package com.swuos.mobile.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

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
        if (userModel.isNeedLogin()) {
            startLogin();
        } else {
            startMain();
        }
    }

    private void startLogin() {
        getHandler().postDelayed(() -> {
            startActivity(LoginActivity.class);
            finish();
        }, 2000);
    }

    private void startMain() {
        getHandler().postDelayed(() -> {
            startActivity(MainActivity.class);
            finish();
        }, 2000);
    }
}
