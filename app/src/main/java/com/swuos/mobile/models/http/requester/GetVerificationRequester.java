package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.BodyCreator;
import com.gallops.mobile.jmvclibrary.http.creator.JsonBodyCreator;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.api.VerificationHostRequester;
import com.swuos.mobile.entity.BaseInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

@Route(RouteEnum.ROUTE_SEND_VERIFICATION_CODE)
@BodyCreator(JsonBodyCreator.class)
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

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {
        params.put("phoneNumber", phoneNumber);
    }
}
