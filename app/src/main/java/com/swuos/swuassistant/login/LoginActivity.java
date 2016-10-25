package com.swuos.swuassistant.login;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.swuos.ALLFragment.library.lib.utils.MetricUtils;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.swuos.swuassistant.login.presenter.ILoginPresenter;
import com.swuos.swuassistant.login.presenter.LoginPresenterCompl;
import com.swuos.swuassistant.login.view.ILoginView;
import com.swuos.util.tools.Tools;
import com.swuos.widget.reveal.RevealLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张孟尧 on 2016/7/19.
 */
public class LoginActivity extends AppCompatActivity implements ILoginView {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign_in_button)
    Button signInButton;

    @BindView(R.id.reveal_viewLogin)
    View mRevealView;
    @BindView(R.id.reveal_layoutLogin)
    RevealLayout mRevealLayout;

    @BindView(R.id.textViewLoginBack)
    TextView mTextViewLoginBack;

    ILoginPresenter iLoginPresenter;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    String muserName;
    String mpassWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        iLoginPresenter = new LoginPresenterCompl(this, this);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this).setPositiveButton(R.string.i_know, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).create();
    }

    @OnClick(R.id.sign_in_button)
    public void onClick(View view) {
        Tools.closeSoftKeyBoard(this);
      /*显示登陆过程窗口*/
//        progressDialog.setMessage(this.getString(R.string.loging_and_wait));
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        signInButton.setText("");
        mTextViewLoginBack.setVisibility(View.VISIBLE);
        float delta = 30;
        PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(.10f, -delta),
                Keyframe.ofFloat(.26f, delta),
                Keyframe.ofFloat(.42f, -delta),
                Keyframe.ofFloat(.58f, delta),
                Keyframe.ofFloat(.74f, -delta),
                Keyframe.ofFloat(.90f, delta),
                Keyframe.ofFloat(1f, 0f)
        );
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mTextViewLoginBack, pvhTranslateX);
        animator.setDuration(1000);
        animator.setRepeatCount(10);
        animator.start();

        muserName = username.getText().toString();
        mpassWord = password.getText().toString();
        iLoginPresenter.doLogin(muserName, mpassWord);

    }

    @Override
    public void onLoginResult(String result) {
        progressDialog.cancel();
        if ("success".equals(result)) {
            /*开启下一个窗口*/
            Intent intent = new Intent();
            setResult(Constant.LOGIN_RESULT_CODE, intent);
            signInButton.setClickable(false);
            int[] location = new int[2];
//            signInButton.getLocationOnScreen(location);
//            location[0] += signInButton.getWidth() / 2;
//            location[1] -= (signInButton.getHeight()+30);
            location[0] = (int) (MetricUtils.getScrWidth(this) / 2);
            location[1] = (int) (MetricUtils.getScrHeight(this) / 2 - 100);
            mRevealView.setVisibility(View.VISIBLE);
            mRevealLayout.setVisibility(View.VISIBLE);
            mRevealLayout.show(location[0], location[1]);
            signInButton.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    /**
                     * Without using R.anim.hold, the screen will flash because of transition
                     * of Activities.
                     */
                    //没作用 因为不是通过startActivity()
                    overridePendingTransition(0, R.anim.hold);
                }
            }, 400);
            signInButton.postDelayed(new Runnable() {
                @Override
                public void run() {
                    signInButton.setClickable(true);
                    mTextViewLoginBack.clearAnimation();
                    mTextViewLoginBack.setVisibility(View.GONE);
                    signInButton.setText("登录");
                    mRevealLayout.setVisibility(View.INVISIBLE);
                    mRevealView.setVisibility(View.INVISIBLE);
                }
            }, 700);
        } else {
            mTextViewLoginBack.clearAnimation();
            mTextViewLoginBack.setVisibility(View.GONE);
            signInButton.setText("登录");
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }


}
