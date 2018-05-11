package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;
import com.swuos.mobile.api.FreegattyHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.entity.AllScoreItem;
import com.swuos.mobile.entity.ScoreItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

@Route(RouteEnum.ROUTE_GET_SCORE)
@RequestMethod(HttpMethod.GET)
public class GetScoreRequester extends FreegattyHostRequester<AllScoreItem> {
    private String swuId, academicYear, term;

    public GetScoreRequester(String swuId, String academicYear, String term, @NonNull OnResultListener<AllScoreItem> listener) {
        super(listener);
        this.academicYear = academicYear;
        this.swuId = swuId;
        this.term = term;
    }

    @Override
    protected AllScoreItem onDumpData(@NonNull JSONObject jsonObject) throws JSONException {
        //registerInfo
        AllScoreItem allScoreItem = new AllScoreItem();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        ArrayList<ScoreItem> scoreItems = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ScoreItem scoreItem = new ScoreItem();
            scoreItem.setLessonName(((JSONObject) jsonArray.get(i)).getString("lessonName"));
            scoreItem.setScore(((JSONObject) jsonArray.get(i)).getString("score"));
            scoreItem.setTerm(((JSONObject) jsonArray.get(i)).getString("term"));
            scoreItem.setGradePoint(((JSONObject) jsonArray.get(i)).getString("gradePoint"));
            scoreItem.setCredit(((JSONObject) jsonArray.get(i)).getString("credit"));
            scoreItem.setExamType(((JSONObject) jsonArray.get(i)).getString("examType"));
            scoreItem.setLessonType(((JSONObject) jsonArray.get(i)).getString("lessonType"));
            scoreItems.add(scoreItem);
        }
        allScoreItem.setArrayList(scoreItems);
        return allScoreItem;
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {
        params.put("swuid", swuId);
        params.put("academicYear", academicYear);
        params.put("term", term);
    }
}
