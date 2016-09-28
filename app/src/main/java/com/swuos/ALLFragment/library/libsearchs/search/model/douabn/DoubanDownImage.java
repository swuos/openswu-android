package com.swuos.ALLFragment.library.libsearchs.search.model.douabn;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/9/28.
 */

public interface DoubanDownImage {
    @Headers({"User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36"})

    @GET
    Observable<ResponseBody> downImage(@Url String url);
}
