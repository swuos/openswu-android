package com.swuos.mobile.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gallops.mobile.jmvclibrary.app.BaseActivity;
import com.gallops.mobile.jmvclibrary.http.ErrorCode;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;
import com.swuos.mobile.entity.BaseInfo;
import com.swuos.mobile.models.http.requester.GetVerificationRequester;

public class ChangePhoneNumberActivity extends BaseActivity {
    @FindViewById(R.id.old_phone_nubmber)
    EditText oldPhoneNumberEditText;
    @FindViewById(R.id.verification_code)
    EditText verificationCodeEt;
    @FindViewById(R.id.get_verification_code_button)
    Button getVerificationCodeButton;
    @FindViewById(R.id.new_phone_number)
    EditText newPhoneNumberEt;
    @FindViewById(R.id.back_im)
    Button backButton;
    @FindViewById(R.id.comfirm)
    Button confirm;
    private int countDown = 60;//发送验证码的倒计时

    @Override
    protected int getLayoutResId() {
        return R.layout.a_chage_phone_number;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {

    }

    @OnClick({R.id.back_im, R.id.comfirm, R.id.get_verification_code_button})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_im:
                finish();
                break;
            case R.id.get_verification_code_button:
                getVerificationCode(oldPhoneNumberEditText.getText().toString());
                break;
            case R.id.comfirm:
                chanegPhoneNumber(oldPhoneNumberEditText.getText().toString(), verificationCodeEt.getText().toString(), newPhoneNumberEt.getText().toString());
                break;
            default:
                break;
        }
    }

    private void getVerificationCode(String oldPhoneNumber) {
        if (TextUtils.isEmpty(oldPhoneNumber)) {
            showToast("没有输入手机号哦");
            return;
        }
        showProgressDialog("正在发送");
        GetVerificationRequester getVerificationRequester = new GetVerificationRequester(oldPhoneNumber, new OnResultListener<BaseInfo>() {
            @Override
            public void onResult(int code, BaseInfo baseInfo, String msg) {
                dismissProgressDialog();
                if (code == ErrorCode.RESULT_DATA_OK) {
                    showToast("发送成功");
                    countDown();
                } else {
                    showToast(msg);
                }
            }
        });
        getVerificationRequester.execute();
    }

    void countDown() {
        getHandler().postDelayed(() -> {
            countDown--;
            if (countDown == 0) {
                countDown = 60;
                getVerificationCodeButton.setText("获取验证码");
                getVerificationCodeButton.setEnabled(true);
                return;
            } else {
                getVerificationCodeButton.setText("" + countDown + "s");
                countDown();
            }
        }, 1000);
    }

    private void chanegPhoneNumber(String oldPhoneNumber, String verificationCode, String newPhoneNumber) {

        if (TextUtils.isEmpty(oldPhoneNumber)) {
            showToast("没有输入手机号哦");
            return;
        }
        if (TextUtils.isEmpty(verificationCode)) {
            showToast("没有输入验证码哦");
            return;
        }
        if (newPhoneNumber.length() != 11) {
            showToast("新的手机号码应该为11位哦");
            return;
        }


    }
}
