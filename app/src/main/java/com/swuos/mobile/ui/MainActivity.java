package com.swuos.mobile.ui;

import android.os.Bundle;

import com.swuos.mobile.R;
import com.swuos.mobile.api.OnResultListener;
import com.swuos.mobile.app.BaseActivity;
import com.swuos.mobile.entity.AccountInfo;
import com.swuos.mobile.entity.UserInfo;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.utils.injector.Model;

public class MainActivity extends BaseActivity {

    @Model
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showProgressDialog();
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUserName("222012321062035");
        accountInfo.setUserPwd("iforgetit123");
        userModel.login(accountInfo, new OnResultListener<UserInfo>() {
            @Override
            public void onResult(int code, UserInfo userInfo) {
                dismissProgressDialog();
            }
        });
    }
}
