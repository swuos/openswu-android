package com.swuos.ALLFragment.wifi.model;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/10/18.
 */
public interface SwuNetSelf {
    @FormUrlEncoded
    @POST("http://service2.swu.edu.cn/selfservice/module/scgroup/web/login_judge.jsf")
    Observable<String> loginToCheck(@Field("name") String name, @Field("password") String password);

    @GET("http://service2.swu.edu.cn/selfservice/module/webcontent/web/onlinedevice_list.jsf")
    Observable<String> getMyLoginInfo();

    @POST("http://service2.swu.edu.cn/selfservice/module/userself/web/userself_ajax.jsf?methodName=indexBean.kickUserBySelfForAjax ")
    @FormUrlEncoded
    Observable<String> logout(@Field("key") String key);
}
