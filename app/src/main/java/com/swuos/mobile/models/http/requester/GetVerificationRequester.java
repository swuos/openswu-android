package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.HttpRequester;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.gallops.mobile.jmvclibrary.utils.json.JsonUtil;
import com.swuos.mobile.entity.BaseInfo;
import com.swuos.mobile.entity.RegisterInfo;
import com.swuos.mobile.models.http.ApiHostUrl;
import com.swuos.mobile.models.http.RouteRegister;
import com.swuos.mobile.models.http.RouteSendVerificationCode;
import com.swuos.mobile.models.http.VerificationApiHostUrl;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

public class GetVerificationRequester extends HttpRequester<BaseInfo> {

    String phoneNumber;


    public GetVerificationRequester(String phoneNumber, @NonNull OnResultListener<BaseInfo> listener) {
        super(listener);
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected BaseInfo onDumpData(JSONObject jsonObject) throws JSONException {
        //registerInfo

        return null;
    }

    @NonNull
    @Override
    protected HttpMethod setMethod() {
        return HttpMethod.POST;
    }

    @Override
    protected ApiInterface getApi() {
        return new VerificationApiHostUrl();
    }

    @NonNull
    @Override
    protected RouteInterface setRoute() {
        return RouteSendVerificationCode.ROUTE_SEND_VERIFICATION_CODE;
    }

    @Override
    protected String setReqUrl() {
        return super.setReqUrl();
    }


    @Override
    protected String appendUrl(String url) {
        return url;
    }

    @NonNull
    @Override
    protected RequestBody onPutParams(FormBody.Builder builder) {
        if (builder == null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("phoneNumber", phoneNumber);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return FormBody.create(MediaType.parse("application/json"),jsonObject.toString());
        }
        return null;//get请求直接返回builder
    }
}
