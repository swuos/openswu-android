package com.swuos.swuassistant.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.swuos.swuassistant.BaseActivity;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.swuos.swuassistant.login.presenter.LoginPresenterCompl;
import com.swuos.swuassistant.login.view.ILoginView;

/**
 * Created by 张孟尧 on 2016/7/19.
 */
public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {


    private ProgressBar loginProgress;
    private EditText username;
    private EditText password;
    private Button signInButton;

    private LoginPresenterCompl iLoginPresenter;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private String muserName;
    private String mpassWord;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        initView();
    }

    void bindView() {
        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
        signInButton = (Button) this.findViewById(R.id.sign_in_button);
    }

    void initView() {
        iLoginPresenter = new LoginPresenterCompl(this, this);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this).setPositiveButton(R.string.i_know, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).create();
        signInButton.setOnClickListener(this);
    }


    public void onClick(View view) {


        muserName = username.getText().toString();
        mpassWord = password.getText().toString();
        if (TextUtils.isEmpty(muserName)) {
            username.setError("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(mpassWord)) {
            password.setError("密码不能为空");
            return;
        }
         /*显示登陆过程窗口*/
        progressDialog.setMessage(this.getString(R.string.loging_and_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        iLoginPresenter.doLogin(muserName, mpassWord);

    }

    @Override
    public void onLoginResult(String result) {
        progressDialog.cancel();
        if ("success".equals(result)) {

            /*开启下一个窗口*/
            Intent intent = new Intent();
            intent.putExtra("username", muserName);
            intent.putExtra("password", mpassWord);
            setResult(Constant.LOGIN_RESULT_CODE, intent);
            finish();
        } else {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }


}
