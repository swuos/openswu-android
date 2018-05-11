package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.api.VerificationHostRequester;
import com.swuos.mobile.entity.BaseInfo;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

public class GetVerificationRequester extends VerificationHostRequester<BaseInfo> {

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

    @NonNull
    @Override
    protected RouteInterface setRoute() {
        return RouteEnum.ROUTE_SEND_VERIFICATION_CODE;
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
