package com.swuos.ALLFragment.swujw.net.interfacelmpl;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/9/2.
 */
public interface JwJudgement {
    @POST("jwglxt/xspjgl/xspj_cxXspjIndex.html?gnmkdmKey=N4010")
    @FormUrlEncoded
    Observable<String> getTeacherList(@Query("sessionUserKey") String sessionUserkey, @Query("_t") long time, @Field("gnmkdm") String gnmkdm, @Field("czdmKey") String czdmKey);

    @POST("jwglxt/xspjgl/xspj_bcXspj.html?gnmkdmKey=N4010")
    @FormUrlEncoded
    Observable<String> setJudgement(@Query("sessionUserKey") String sessionUserkey, @FieldMap Map<String, String> data);
}

