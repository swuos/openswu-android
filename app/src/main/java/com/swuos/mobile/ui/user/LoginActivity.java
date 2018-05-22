package com.swuos.mobile.ui.user;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gallops.mobile.jmvclibrary.app.BaseActivity;
import com.gallops.mobile.jmvclibrary.http.ErrorCode;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.utils.Logger;
import com.gallops.mobile.jmvclibrary.utils.injector.Model;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;
import com.swuos.mobile.entity.LoginInfo;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.ui.tab.MainActivity;


/**
 * 登录页面
 * Created by wangyu on 2018/3/7.
 */

public class LoginActivity extends BaseActivity {
    @FindViewById(R.id.phonenumber_edit)
    private EditText phoneEditText;
    @FindViewById(R.id.password_edit)
    private EditText passwordEditText;
    @FindViewById(R.id.login_button)
    private Button loginButton;
    @FindViewById(R.id.forget_password_tv)
    private TextView forgetTextView;
    @FindViewById(R.id.register_now_tv)
    private TextView registerTextView;
    @FindViewById(R.id.show_password_im)
    private ImageView showPasswordImageView;
    @Model
    private UserModel userModel;

    /**
     * 启动页面，清除所有存活的activity
     * @param context
     */
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @OnClick({R.id.show_password_im, R.id.login_button, R.id.register_now_tv, R.id.forget_password_tv})
    private void onClick(View view) {
        Logger.d("tag", view.getClass().getSimpleName());
        switch (view.getId()) {
            case R.id.login_button:
                if (phoneEditText.length() < 11) {
                    showToast("手机号码应为11位哦");
                    return;
                }
                if (passwordEditText.length() == 0) {
                    showToast("密码不能为空哦");
                    return;
                }
                login(phoneEditText.getText().toString(), passwordEditText.getText().toString());
                break;
            case R.id.show_password_im:
                if (passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.length());
                    showPasswordImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.opened_eye));
                } else {
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.length());
                    showPasswordImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.closed_eye));
                }
                break;
            case R.id.register_now_tv:
                startActivity(RegisterActivity.class);
                break;
            case R.id.forget_password_tv:
                startActivity(ForgetPasswordActivity.class);
                break;
            default:
                break;

        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.a_login;
    }

    void login(String phoneNumber, String password) {
        showProgressDialog("正在登录");
        userModel.login(phoneNumber, password, (code, loginInfo, msg) -> {
            dismissProgressDialog();
            if (code == ErrorCode.RESULT_DATA_OK) {
                postStartActivity(MainActivity.class, 0);
            } else {
                showToast(msg);
            }
        });

    }
}
