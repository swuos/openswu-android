package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;
import com.gallops.mobile.jmvclibrary.utils.json.JsonUtil;
import com.swuos.mobile.api.FreegattyHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.entity.AllWeeksClass;
import com.swuos.mobile.entity.ClassItemDetail;
import com.swuos.mobile.entity.WeekClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

@RequestMethod(HttpMethod.GET)
@Route(RouteEnum.ROUTE_GET_SCHEDULE)
public class GetScheduleRequester extends FreegattyHostRequester<List<WeekClasses>> {
    private String swuId, academicYear, term;

    public GetScheduleRequester(String swuId, String academicYear, String term, @NonNull OnResultListener<List<WeekClasses>> listener) {
        super(listener);
        this.academicYear = academicYear;
        this.swuId = swuId;
        this.term = term;

    }

    @Override
    protected List<WeekClasses> onDumpData(@NonNull JSONObject jsonObject) throws JSONException {
        //registerInfo
        JSONArray data = jsonObject.optJSONArray("data");
        return JsonUtil.toList(data, WeekClasses.class);
    }

    @Override
    protected void preHandleRequest(@NonNull Request.Builder reqBuilder) {
        super.preHandleRequest(reqBuilder);
        reqBuilder.addHeader("acToken", getUserModel().getAccountInfo().getAcToken());
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {
        params.put("swuid", swuId);
        params.put("academicYear", academicYear);
        params.put("term", term);
    }
}
