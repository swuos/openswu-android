package com.swuos.ALLFragment.library.libsearchs_origin.search.model.douabn;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/9/10.
 */
public interface DoubanSearch {
    @Headers({"User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36"})
    @GET("subject_search?cat=1001")
    Observable<String> doubanSearch(@Query("search_text") String search_text);
}
