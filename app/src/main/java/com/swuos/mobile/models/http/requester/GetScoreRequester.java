package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.HttpRequester;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.swuos.mobile.app.App;
import com.swuos.mobile.entity.AllScoreItem;
import com.swuos.mobile.entity.AllWeeksClass;
import com.swuos.mobile.entity.ClassItemDetail;
import com.swuos.mobile.entity.ScoreItem;
import com.swuos.mobile.models.http.FreegattyApiHostUrl;
import com.swuos.mobile.models.http.RouteGetSchedule;
import com.swuos.mobile.models.http.RouteGetScore;
import com.swuos.mobile.models.user.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

public class GetScoreRequester extends HttpRequester<AllScoreItem> {
    private String swuId, academicYear, term;

    public GetScoreRequester(String swuId, String academicYear, String term, @NonNull OnResultListener<AllScoreItem> listener) {
        super(listener);
        this.academicYear = academicYear;
        this.swuId = swuId;
        this.term = term;

    }

    @Override
    protected AllScoreItem onDumpData(JSONObject jsonObject) throws JSONException {
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

    @NonNull
    @Override
    protected HttpMethod setMethod() {
        return HttpMethod.GET;
    }

    @Override
    protected void preHandleRequest(Request.Builder reqBuilder) {
        super.preHandleRequest(reqBuilder);
        reqBuilder.addHeader("acToken", App.getInstance().getModel(UserModel.class).getAccountInfo().getAcToken());
    }

    @Override
    protected ApiInterface getApi() {
        return new FreegattyApiHostUrl();
    }

    @NonNull
    @Override
    protected RouteInterface setRoute() {
        return RouteGetScore.ROUTE_GET_SCORE;
    }


    @Override
    protected String setReqUrl() {
        return super.setReqUrl();
    }


    @Override
    protected String appendUrl(String url) {
        return url + "?swuid=" + swuId + "&academicYear=" + academicYear + "&term=" + term;
    }

    @NonNull
    @Override
    protected RequestBody onPutParams(FormBody.Builder builder) {
        if (builder == null)
            return FormBody.create(MediaType.parse("application/json"), "");
        return null;//get请求直接返回builder
    }
}
