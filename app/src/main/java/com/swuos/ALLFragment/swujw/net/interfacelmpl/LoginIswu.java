package com.swuos.ALLFragment.swujw.net.interfacelmpl;




import com.swuos.ALLFragment.swujw.net.jsona.LoginJson;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by 张孟尧 on 2016/8/30.
 */
public interface LoginIswu {
    @Headers({"X-Requested-With:XMLHttpRequest", "Content-Type:application/x-www-form-urlencoded; charset=UTF-8"})
    @POST("http://i.swu.edu.cn/remote/service/process")
    @FormUrlEncoded
    Observable<LoginJson> login(@Field("serviceInfo") String swuLoginJson);
}
