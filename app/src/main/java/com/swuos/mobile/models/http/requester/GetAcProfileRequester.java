package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.BodyCreator;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;
import com.gallops.mobile.jmvclibrary.http.creator.JsonBodyCreator;
import com.swuos.mobile.api.AcHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.app.App;
import com.swuos.mobile.entity.AccountInfo;
import com.swuos.mobile.entity.BaseInfo;
import com.swuos.mobile.models.user.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Request;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

@Route(RouteEnum.GET_AC_PROFILE)
@RequestMethod(HttpMethod.GET)
@BodyCreator(JsonBodyCreator.class)
public class GetAcProfileRequester extends AcHostRequester<BaseInfo> {
    private String swuId, academicYear, term;

    public GetAcProfileRequester(@NonNull OnResultListener<BaseInfo> listener) {
        super(listener);
    }

    @Override
    protected BaseInfo onDumpData(JSONObject jsonObject) throws JSONException {
        AccountInfo accountInfo = App.getInstance().getModel(UserModel.class).getAccountInfo();
        accountInfo.setSwuId(jsonObject.optString("swuid"));
        App.getInstance().getModel(UserModel.class).saveAccountInfo(accountInfo);
        return new BaseInfo();
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {

    }
}
