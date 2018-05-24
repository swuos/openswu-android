package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.BodyCreator;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;
import com.gallops.mobile.jmvclibrary.http.creator.JsonBodyCreator;
import com.swuos.mobile.api.AcHostRequester;
import com.swuos.mobile.api.FreegattyHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.app.App;
import com.swuos.mobile.entity.AccountInfo;
import com.swuos.mobile.models.user.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

@Route(RouteEnum.ROUTE_GET_CALENDAR)
@RequestMethod(HttpMethod.GET)
@BodyCreator(JsonBodyCreator.class)
public class GetCalendarRequester extends FreegattyHostRequester<JSONObject> {
    private String academicYear, term;

    public GetCalendarRequester(String academicYear, String term, @NonNull OnResultListener<JSONObject> listener) {
        super(listener);
        this.academicYear = academicYear;
        this.term = term;
    }

    @Override
    protected JSONObject onDumpData(JSONObject jsonObject) throws JSONException {
        return jsonObject;
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {
        params.put("academicYear",academicYear);
        params.put("term",term);

    }
}
