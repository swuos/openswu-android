package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.BodyCreator;
import com.gallops.mobile.jmvclibrary.http.creator.JsonBodyCreator;
import com.gallops.mobile.jmvclibrary.utils.json.JsonUtil;
import com.swuos.mobile.api.AcHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.entity.RegisterInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Request;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */
@Route(RouteEnum.ROUTE_REGISTER)
@BodyCreator(JsonBodyCreator.class)
public class RegisterRequester extends AcHostRequester<RegisterInfo> {

    String phoneNumber;
    String password;
    String verificationCode;

    public RegisterRequester(String phoneNumber,
                             String password,
                             String verificationCode, @NonNull OnResultListener<RegisterInfo> listener) {
        super(listener);
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.verificationCode = verificationCode;
    }

    @Override
    protected void preHandleRequest(@NonNull Request.Builder reqBuilder) {
        //do nothing
    }

    @Override
    protected RegisterInfo onDumpData(@NonNull JSONObject jsonObject) throws JSONException {
        //registerInfo
        return JsonUtil.toObject(jsonObject, RegisterInfo.class);
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {
        params.put("phoneNumber", phoneNumber);
        params.put("password", password);
        params.put("verificationCode", verificationCode);
    }
}
