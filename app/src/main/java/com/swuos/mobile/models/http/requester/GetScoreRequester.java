package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;
import com.gallops.mobile.jmvclibrary.utils.json.JsonUtil;
import com.swuos.mobile.api.FreegattyHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.entity.ScoreItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

@Route(RouteEnum.ROUTE_GET_SCORE)
@RequestMethod(HttpMethod.GET)
public class GetScoreRequester extends FreegattyHostRequester<ArrayList<ScoreItem>> {
    private String swuId, academicYear, term;

    public GetScoreRequester(String swuId, String academicYear, String term, @NonNull OnResultListener<ArrayList<ScoreItem>> listener) {
        super(listener);
        this.academicYear = academicYear;
        this.swuId = swuId;
        this.term = term;
    }

    @Override
    protected ArrayList<ScoreItem> onDumpData(@NonNull JSONObject jsonObject) throws JSONException {
        //registerInfo
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<ScoreItem> allscoreItem= JsonUtil.toList(jsonArray,ScoreItem.class);

        return (ArrayList<ScoreItem>) allscoreItem;
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {
        params.put("swuid", swuId);
        params.put("academicYear", academicYear);
        params.put("term", term);
    }
}
