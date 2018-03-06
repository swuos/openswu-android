package com.swuos.mobile.models.user;

import android.support.annotation.NonNull;

import com.swuos.mobile.api.ApiUrl;
import com.swuos.mobile.api.HttpMethod;
import com.swuos.mobile.api.HttpRequester;
import com.swuos.mobile.api.OnResultListener;
import com.swuos.mobile.entity.AccountInfo;
import com.swuos.mobile.entity.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;

/**
 *
 * Created by wangyu on 2018/3/6.
 */

public class LoginRequester extends HttpRequester<UserInfo> {

    private AccountInfo accountInfo;

    public LoginRequester(AccountInfo accountInfo, @NonNull OnResultListener<UserInfo> listener) {
        super(listener);
        this.accountInfo = accountInfo;
    }

    @Override
    protected UserInfo onDumpData(JSONObject jsonObject) throws JSONException {
        UserInfo userInfo = new UserInfo();
        userInfo.setStudentId(accountInfo.getUserName());
        userInfo.setStudentName(accountInfo.getUserName());
        userInfo.setToken(jsonObject.optString("token"));
        return userInfo;
    }

    @NonNull
    @Override
    protected HttpMethod setMethod() {
        return HttpMethod.GET;
    }

    @NonNull
    @Override
    protected ApiUrl setApiUrl() {
        return ApiUrl.LOGIN_URL;
    }

    @Override
    protected String appendUrl(String url) {
        url = url + "/" + accountInfo.getUserName() + "/" + accountInfo.getUserPwd();
        return url;
    }

    @NonNull
    @Override
    protected FormBody.Builder onPutParams(FormBody.Builder builder) {
        return builder;
    }
}
