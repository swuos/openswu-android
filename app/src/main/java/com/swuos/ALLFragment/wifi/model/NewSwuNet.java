package com.swuos.ALLFragment.wifi.model;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/10/18.
 */
public interface NewSwuNet {

    //先访问这个网址获得连接设备的信息
    @GET("/")
    @Headers({"Connection: keep-alive",
            "Pragma: no-cache",
            "Cache-Control: no-cache",
            "Upgrade-Insecure-Requests: 1",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.113 Safari/537.36",
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
            "DNT: 1"})
    Observable<String> loginPre();

    //登录操作
    @FormUrlEncoded
    @POST("eportal/InterFace.do?method=login ")
    Observable<String> login(@FieldMap Map<String, String> bodyMap);

    //退出操作
    @POST("/eportal/InterFace.do?method=logout")
    Observable<String> logout(@Field("userIndex") String userIndex);


}
