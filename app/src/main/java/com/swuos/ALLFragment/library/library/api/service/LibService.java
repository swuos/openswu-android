package com.swuos.ALLFragment.library.library.api.service;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/10/25.
 */
public interface LibService {
    @POST("http://202.202.121.3:8080/login")
    @Headers({"Referer: http://202.202.121.3:99/gold/opac/login?referer=/opac/mylibrary&tag=index"})
    @FormUrlEncoded
    Observable<String> loginLib(@FieldMap Map<String, String> data);

    @GET("http://202.202.121.3:8080/login?service=http%3A%2F%2F202.202.121.3%3A99%2Fgold%2Fopac%2Fsearch%2Fsimsearch&get-lt=true")
    Observable<String> getLoginParam(@Query("n") long time1, @Query("_") long time2);

    @GET("/gold/opac/mylibrary")
    Observable<String> userLib();

    /**
     *   such as :  gold/opac/mylibrary/circulationHistory?pager.offset=0
     * @param pos
     * @return
     */
    @GET("/gold/opac/mylibrary/circulationHistory")
    Observable<String> getBorrowHistory(@Query("pager.offset") int pos);
}
