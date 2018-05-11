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
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;

public class ForgetPasswordActivity extends BaseActivity {
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.a_forget_password;
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
                if (s.length() == 11)
                {
                    getVerificationCodeButton.setEnabled(true);
                }
                else getVerificationCodeButton.setEnabled(false);
            }
        });
    }

    @OnClick({R.id.clear_phonenumber_im, R.id.register_button, R.id.get_verification_code_button, R.id.back_im})
    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.clear_phonenumber_im:
                phoneEditText.setTag("");
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

    }

    private void getVerificationCode() {

    }
}
