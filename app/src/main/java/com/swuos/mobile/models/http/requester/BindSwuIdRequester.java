package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.swuos.mobile.api.AcHostRequester;
import com.swuos.mobile.app.App;
import com.swuos.mobile.api.RouteEnum;
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

public class BindSwuIdRequester extends AcHostRequester<BaseInfo> {

    private String swuId;
    private String password;

    public BindSwuIdRequester(String swuId, String password, @NonNull OnResultListener<BaseInfo> listener) {
        super(listener);
        this.swuId = swuId;
        this.password = password;

    }

    @Override
    protected BaseInfo onDumpData(JSONObject jsonObject) throws JSONException {
        //registerInfo

        return null;
    }

    @Override
    protected void preHandleRequest(Request.Builder reqBuilder) {
        super.preHandleRequest(reqBuilder);
        String actoken = App.getInstance().getModel(UserModel.class).getAccountInfo().getAcToken();
        reqBuilder.addHeader("acToken",actoken==null?"":actoken );
    }

    @NonNull
    @Override
    protected HttpMethod setMethod() {
        return HttpMethod.POST;
    }

    @NonNull
    @Override
    protected RouteInterface setRoute() {
        return RouteEnum.ROUTE_BIND;
    }

    @NonNull
    @Override
    protected RequestBody onPutParams(FormBody.Builder builder) {
        if (builder == null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("swuid", swuId);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return FormBody.create(MediaType.parse("application/json"), jsonObject.toString());
        }
        return null;//get请求直接返回builder
    }
}
