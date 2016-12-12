package com.swuos.swuassistant.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.swuos.ALLFragment.swujw.net.api.SwuJwApi;
import com.swuos.ALLFragment.swujw.net.jsona.LoginJson;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.login.view.ILoginView;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 张孟尧 on 2016/7/19.
 */
public class LoginPresenterCompl {
    private String name;
    private String swuid;
    private String username;
    private String password;
    private String cookie;
    private ILoginView iLoginView;
    private Context context;


    public LoginPresenterCompl(ILoginView iLoginView, Context context) {
        this.iLoginView = iLoginView;
        this.context = context;
    }

    public String doLogin(final String userName, final String password) {


        String swuLoginjsons = String.format("{\"serviceAddress\":\"https://uaaap.swu.edu.cn/cas/ws/acpInfoManagerWS\",\"serviceType\":\"soap\",\"serviceSource\":\"td\",\"paramDataFormat\":\"xml\",\"httpMethod\":\"POST\",\"soapInterface\":\"getUserInfoByUserName\",\"params\":{\"userName\":\"%s\",\"passwd\":\"%s\",\"clientId\":\"yzsfwmh\",\"clientSecret\":\"1qazz@WSX3edc$RFV\",\"url\":\"http://i.swu.edu.cn\"},\"cDataPath\":[],\"namespace\":\"\",\"xml_json\":\"\"}", userName, password);
        SwuJwApi.loginIswu().login(swuLoginjsons).flatMap(new Func1<LoginJson, Observable<String>>() {
            @Override
            public Observable<String> call(LoginJson loginJson) {
                if (loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().isSuccess()) {
                    String tgt = loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getAttributes().getTgt();
                    cookie = String.format("CASTGC=\"%s\"; rtx_rep=no", new String(Base64.decode(tgt, Base64.DEFAULT)));
                    getBasicInfo(loginJson, password);
                    storageInfo();
                    return SwuJwApi.loginJw(cookie).login();
                } else
                    return Observable.error(new Throwable(Constant.USERNAMEORPSWERRR));

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String error = e.getMessage();
                        if (e instanceof UnknownHostException) {
                            error = Constant.CLIENT_ERROR;
                        } else if (e instanceof SocketTimeoutException) {
                            error = Constant.CLIENT_TIMEOUT;
                        }
                        iLoginView.onLoginResult(error);
                        Log.d("LoginPresenterCompl", "e:" + e);
                    }

                    @Override
                    public void onNext(String s) {
                        iLoginView.onLoginResult("success");
                    }
                });


        return null;
    }


    private void storageInfo() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", username);
        editor.putString("password", password);
        editor.putString("name", name);
        editor.putString("swuID", swuid);
        editor.apply();
    }

    private void getBasicInfo(LoginJson loginJson, String password) {
        /*获得基本信息名字和学号*/
        this.password = password;
        LoginJson.DataBean.GetUserInfoByUserNameResponseBean.ReturnBean.InfoBean.AttributesBean returnBean = loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getAttributes();
        name = new String(Base64.decode(returnBean.getACPNAME(), Base64.DEFAULT));
        swuid = (loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getId());
        username = (new String(Base64.decode(returnBean.getACPNICKNAME(), Base64.DEFAULT)));

    }

}