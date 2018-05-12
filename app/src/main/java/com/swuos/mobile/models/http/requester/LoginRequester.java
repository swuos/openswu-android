package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.gallops.mobile.jmvclibrary.http.annotation.BodyCreator;
import com.gallops.mobile.jmvclibrary.http.creator.JsonBodyCreator;
import com.swuos.mobile.api.AcHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.entity.LoginInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Request;

/**
 * 登录请求
 * Created by wangyu on 2018/3/6.
 */

@Route(RouteEnum.ROUTE_LOGIN)
@BodyCreator(JsonBodyCreator.class)
public class LoginRequester extends AcHostRequester<LoginInfo> {

    String phoneNumber;
    String password;

    public LoginRequester(String phoneNumber,
                          String password, @NonNull OnResultListener<LoginInfo> listener) {
        super(listener);
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    protected void preHandleRequest(@NonNull Request.Builder reqBuilder) {
        //覆盖掉父类自动填充token逻辑
    }

    @Override
    protected LoginInfo onDumpData(@NonNull JSONObject jsonObject) throws JSONException {
        //手动组装userInfo
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAcToken(jsonObject.optString("acToken"));
        return loginInfo;
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {
        params.put("phoneNumber", phoneNumber);
        params.put("password", password);
    }
}
