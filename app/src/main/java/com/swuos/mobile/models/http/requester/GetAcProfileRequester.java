package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.swuos.mobile.api.AcHostRequester;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.app.App;
import com.swuos.mobile.entity.BaseInfo;
import com.swuos.mobile.models.user.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

public class GetAcProfileRequester extends AcHostRequester<BaseInfo> {
    private String swuId, academicYear, term;

    public GetAcProfileRequester(@NonNull OnResultListener<BaseInfo> listener) {
        super(listener);
    }

    @Override
    protected BaseInfo onDumpData(JSONObject jsonObject) throws JSONException {
       App.getInstance().getModel(UserModel.class).getAccountInfo().setSwuId(jsonObject.optString("swuid"));
       return new BaseInfo() ;
    }

    @NonNull
    @Override
    protected HttpMethod setMethod() {
        return HttpMethod.GET;
    }

    @Override
    protected void preHandleRequest(Request.Builder reqBuilder) {
        super.preHandleRequest(reqBuilder);
        reqBuilder.addHeader("acToken", App.getInstance().getModel(UserModel.class).getAccountInfo().getAcToken());
    }

    @NonNull
    @Override
    protected RouteInterface setRoute() {
        return RouteEnum.GET_AC_PROFILE;
    }

    @NonNull
    @Override
    protected RequestBody onPutParams(FormBody.Builder builder) {
        if (builder == null)
            return FormBody.create(MediaType.parse("application/json"), "");
        return null;//get请求直接返回builder
    }
}
