package com.swuos.mobile.models.http.requester;

import android.support.annotation.NonNull;

import com.gallops.mobile.jmvclibrary.http.HttpMethod;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.http.annotation.RequestMethod;
import com.swuos.mobile.api.FreegattyHostRequester;
import com.swuos.mobile.api.Route;
import com.swuos.mobile.api.RouteEnum;
import com.swuos.mobile.entity.AllWeeksClass;
import com.swuos.mobile.entity.ClassItemDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Request;

/**
 * 注册请求
 * Created by wangyu on 2018/3/6.
 */

@RequestMethod(HttpMethod.GET)
@Route(RouteEnum.ROUTE_GET_SCHEDULE)
public class GetScheduleRequester extends FreegattyHostRequester<AllWeeksClass> {
    private String swuId, academicYear, term;

    public GetScheduleRequester(String swuId, String academicYear, String term, @NonNull OnResultListener<AllWeeksClass> listener) {
        super(listener);
        this.academicYear = academicYear;
        this.swuId = swuId;
        this.term = term;

    }

    @Override
    protected AllWeeksClass onDumpData(@NonNull JSONObject jsonObject) throws JSONException {
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
