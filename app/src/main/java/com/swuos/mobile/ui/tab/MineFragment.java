package com.swuos.mobile.ui.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gallops.mobile.jmvclibrary.app.BaseFragment;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.utils.injector.Model;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;
import com.swuos.mobile.app.App;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.ui.user.LoginActivity;
import com.swuos.mobile.ui.user.SecrityActivity;


/**
 * 课表fragment
 * Created by wangyu on 2018/3/7.
 */

public class MineFragment extends BaseFragment {
    @FindViewById(R.id.avatar)
    ImageView avatarIm;
    @FindViewById(R.id.nickname)
    TextView nickName;
    @FindViewById(R.id.profile)
    TextView profileTv;
    @FindViewById(R.id.count_security)
    LinearLayout countSecurityLayout;
    @FindViewById(R.id.score)
    LinearLayout scoreLayout;
    @FindViewById(R.id.school_card)
    LinearLayout schoolCardLayout;
    @FindViewById(R.id.water_electricity)
    LinearLayout waterElectricityLayout;
    @FindViewById(R.id.feedback)
    LinearLayout feedbackLayout;
    @FindViewById(R.id.about_us)
    LinearLayout aboutUsLayout;
    @FindViewById(R.id.logout)
    Button logoutButton;
    @Model
    private UserModel userModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.f_mine;
    }

    @Override
    protected void onCreateView(View rootView, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.logout, R.id.avatar, R.id.nickname, R.id.profile, R.id.count_security, R.id.score, R.id.school_card, R.id.water_electricity, R.id.feedback, R.id.about_us})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                break;
            case R.id.nickname:
                break;
            case R.id.profile:
                break;
            case R.id.count_security:
                startActivity(SecrityActivity.class);
                break;
            case R.id.score:
                startActivity(ScoreActivity.class);
                break;
            case R.id.school_card:
                break;
            case R.id.water_electricity:
                break;
            case R.id.feedback:
                break;
            case R.id.about_us:
                break;
            case R.id.logout:
                logout();
                break;
            default:
                break;

        }
    }

    private void logout() {
        showProgressDialog("正在退出...");
        userModel.logout((code, aVoid, msg) -> {
            dismissProgressDialog();
            LoginActivity.startActivity(getContext());
        });
    }
}
