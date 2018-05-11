package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.ApiInterface;
import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.HttpRequester;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.RouteInterface;
import com.swuos.mobile.app.App;
import com.swuos.mobile.entity.AllWeeksClass;
import com.swuos.mobile.entity.BaseInfo;
import com.swuos.mobile.entity.ClassItemDetail;
import com.swuos.mobile.models.http.ApiHostUrl;
import com.swuos.mobile.models.http.FreegattyApiHostUrl;
import com.swuos.mobile.models.http.RouteGetSchedule;
import com.swuos.mobile.models.http.RouteSendVerificationCode;
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

public class GetScheduleRequester extends HttpRequester<AllWeeksClass> {
    private String swuId, academicYear, term;

    public GetScheduleRequester(String swuId, String academicYear, String term, @NonNull OnResultListener<AllWeeksClass> listener) {
        super(listener);
        this.academicYear = academicYear;
        this.swuId = swuId;
        this.term = term;

    }

    @Override
    protected AllWeeksClass onDumpData(JSONObject jsonObject) throws JSONException {
        //registerInfo
        AllWeeksClass allWeeksClass = new AllWeeksClass();
        ArrayList<AllWeeksClass.WeekClasses> weekClassesArrayList = new ArrayList<>();
        JSONArray array = jsonObject.getJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            AllWeeksClass.WeekClasses weekClasses = new AllWeeksClass.WeekClasses();
            weekClasses.setWeekSort((Integer) ((JSONObject) array.get(i)).get("weekSort"));
            JSONArray jsonWeekitem = ((JSONObject) array.get(i)).getJSONArray("weekitem");
            ArrayList<ClassItemDetail> classItemDetails=new ArrayList<>();
            for (int j = 0; j < jsonWeekitem.length(); j++) {
                ClassItemDetail classItemDetail = new ClassItemDetail();
                classItemDetail.setAcademicYear(((JSONObject) jsonWeekitem.get(j)).getString("academicYear"));
                classItemDetail.setTerm(((JSONObject) jsonWeekitem.get(j)).getString("term"));
                classItemDetail.setLessonId(((JSONObject) jsonWeekitem.get(j)).getString("lessonId"));
                classItemDetail.setLessonName(((JSONObject) jsonWeekitem.get(j)).getString("lessonName"));
                classItemDetail.setTeacher(((JSONObject) jsonWeekitem.get(j)).getString("teacher"));
                classItemDetail.setAcademicTitle(((JSONObject) jsonWeekitem.get(j)).getString("academicTitle"));
                classItemDetail.setStartTime(((JSONObject) jsonWeekitem.get(j)).getInt("startTime"));
                classItemDetail.setEndTime(((JSONObject) jsonWeekitem.get(j)).getInt("endTime"));
                classItemDetail.setDay(((JSONObject) jsonWeekitem.get(j)).getInt("day"));
                classItemDetail.setWeek(((JSONObject) jsonWeekitem.get(j)).getString("week"));
                classItemDetail.setCampus(((JSONObject) jsonWeekitem.get(j)).getString("campus"));
                classItemDetail.setClassRoom(((JSONObject) jsonWeekitem.get(j)).getString("classRoom"));
                classItemDetails.add(classItemDetail);
            }
            weekClasses.setWeekitem(classItemDetails);
            weekClassesArrayList.add(weekClasses);
        }
        allWeeksClass.setWeekClasses(weekClassesArrayList);
        return allWeeksClass;
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
        return RouteGetSchedule.ROUTE_GET_SCHEDULE;
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
