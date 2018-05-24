package com.swuos.mobile.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gallops.mobile.jmvclibrary.app.BaseActivity;
import com.gallops.mobile.jmvclibrary.app.JApp;
import com.gallops.mobile.jmvclibrary.http.ErrorCode;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;
import com.swuos.mobile.models.http.requester.GetVerificationRequester;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.ui.tab.MainActivity;

import org.json.JSONObject;

public class RegisterActivity extends BaseActivity {
    @FindViewById(R.id.phonenumber_edit)
    private EditText phoneEditText;
    @FindViewById(R.id.password_edit)
    private EditText passwordEditText;
    @FindViewById(R.id.get_verification_code_button)
    private Button getVerificationCodeButton;
    @FindViewById(R.id.verification_code_edit)
    private EditText verificationCodeEdit;
    @FindViewById(R.id.register_button)
    private Button registerButton;
    @FindViewById(R.id.clear_phonenumber_im)
    private ImageView clearPhonenumberIm;
    @FindViewById(R.id.back_im)
    private ImageView backIm;

    //    @FindViewById(R.id.show_password_im)
//    private ImageView showPasswordImageView;
    private UserModel userModel;
    private int countDown=60;//发送验证码的倒计时

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        userModel = JApp.getInstance().getModel(UserModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.a_register;
    }

    void initView() {
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    getVerificationCodeButton.setEnabled(true);
                } else getVerificationCodeButton.setEnabled(false);
            }
        });
    }

    @OnClick({R.id.clear_phonenumber_im, R.id.register_button, R.id.get_verification_code_button, R.id.back_im})
    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.clear_phonenumber_im:
                phoneEditText.setText("");
                break;
            case R.id.register_button:
                register(phoneEditText.getText().toString(), verificationCodeEdit.getText().toString(), passwordEditText.getText().toString());
                break;
            case R.id.get_verification_code_button:
                getVerificationCode();
                break;
            case R.id.back_im:
                finish();
                break;
        }
    }

    private void register(String phoneNumber, String verificationCode, String password) {
        if (phoneNumber.length()<11)
        {
            showToast("手机号码应为11位哦");
            return;
        }
        if (password.length()<8)
        {
            showToast("密码至少8位哦");
            return;
        }
        if (verificationCode.length()!=4)
        {
            showToast("验证码应该为4位哦");
            return;
        }

        userModel.register(phoneNumber,password, verificationCode,(code, registerInfo, msg) -> {
            dismissProgressDialog();
            if (code == ErrorCode.RESULT_DATA_OK) {
                postStartActivity(MainActivity.class, 0, true);
            } else {
                showToast(msg);
            }

        });

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
                getVerificationCodeButton.setText("" + countDown+"s");
                countDown();
            }
        }, 1000);
    }

    private void getVerificationCode() {

        showProgressDialog("正在发送");
        GetVerificationRequester getVerificationRequester = new GetVerificationRequester(phoneEditText.getText().toString(),new OnResultListener<JSONObject>() {
            @Override
            public void onResult(int code, JSONObject baseInfo, String msg) {
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
}
