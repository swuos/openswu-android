package com.swuos.ALLFragment.charge.model;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/9/19.
 */
public interface ChargeBalance {
    @Headers({"Referer:http://211.83.23.198/","User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.113 Safari/537.36"})
    @GET("balance.do")
    Observable<String> chargeBalance(@Query("page") int i);

}
