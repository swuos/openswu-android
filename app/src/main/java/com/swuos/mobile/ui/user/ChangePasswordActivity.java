package com.swuos.mobile.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gallops.mobile.jmvclibrary.app.BaseActivity;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;

public class ChangePasswordActivity extends BaseActivity {
    @FindViewById(R.id.old_password)
    EditText oldPasswordEditText;
    @FindViewById(R.id.new_password)
    EditText newPasswordEt;
    @FindViewById(R.id.new_password_again)
    EditText newPasswordAgainEt;
    @FindViewById(R.id.back_im)
    Button backButton;
    @FindViewById(R.id.comfirm)
    Button confirm;

    @Override
    protected int getLayoutResId() {
        return R.layout.a_chage_password;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {

    }

    @OnClick({R.id.back_im, R.id.comfirm})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_im:
                finish();
                break;
            case R.id.comfirm:
                chanegPassword(oldPasswordEditText.getText().toString(), newPasswordEt.getText().toString(), newPasswordAgainEt.getText().toString());
                break;
            default:
                break;
        }
    }

    private void chanegPassword(String oldPassword, String newPassword, String newPasswordAgain) {

        if (TextUtils.isEmpty(oldPassword)) {
            showToast("没有输入原密码哦");
            return;
        }
        if (!newPassword.equals(newPasswordAgain)) {
            showToast("两次密码不正确哦");
            return;
        }
        if (newPassword.length() < 6 || newPassword.length() > 18) {
            showToast("新密码的长度在6-18位之间哦");
            return;
        }


    }
}
