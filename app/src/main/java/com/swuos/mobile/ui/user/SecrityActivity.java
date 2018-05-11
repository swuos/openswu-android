package com.swuos.mobile.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gallops.mobile.jmvclibrary.app.BaseActivity;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;

public class SecrityActivity extends BaseActivity {
    @FindViewById(R.id.back_im)
    Button backIm;
    @FindViewById(R.id.chage_password)
    RelativeLayout chagePasswordLinearLayout;
    @FindViewById(R.id.chage_phone_number)
    RelativeLayout chagePhoneNumber;

    @Override
    protected int getLayoutResId() {
        return R.layout.a_secrity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.back_im, R.id.chage_password, R.id.chage_phone_number})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_im:
                finish();
                break;
            case R.id.chage_password:
                startActivity(ChangePasswordActivity.class);
                break;
            case R.id.chage_phone_number:
                startActivity(ChangePhoneNumberActivity.class);

                break;
            default:
                break;
        }
    }
}
