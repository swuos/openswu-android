package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.BodyCreator;
import com.gallops.mobile.jmvclibrary.http.creator.JsonBodyCreator;
import com.swuos.mobile.api.AcHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */
@Route(RouteEnum.ROUTE_BIND)
@BodyCreator(JsonBodyCreator.class)
public class BindSwuIdRequester extends AcHostRequester<JSONObject> {

    private String swuId;
    private String password;

    public BindSwuIdRequester(String swuId, String password, @NonNull OnResultListener<JSONObject> listener) {
        super(listener);
        this.swuId = swuId;
        this.password = password;

    }

    @Override
    protected JSONObject onDumpData(@NonNull JSONObject jsonObject) throws JSONException {
        //registerInfo
        return jsonObject;
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {
        params.put("swuid", swuId);
        params.put("password", password);
    }

}
