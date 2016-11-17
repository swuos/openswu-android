package com.swuos.ALLFragment.library.libsearchs.search.model.douabn;


import com.swuos.swuassistant.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 张孟尧 on 2016/9/10.
 */
public class DoubanApi {
    private static DoubanSearch doubanSearch;
    private static DoubanDownImage doubanDownImage;
    private static RxJavaCallAdapterFactory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static ScalarsConverterFactory scalarsConverterFactory = ScalarsConverterFactory.create();
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    private static CookieJar cookieJar = new CookieJar() {
        List<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            cookies = list;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };


    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic("opensource", "freedom");
                    //                    SALog.d("okhttp", "认证");
                    Request request = response.request().newBuilder().header("Authorization", credential).build();
                    return request;
                }
            })
            .readTimeout(Constant.TIMEOUT, TimeUnit.MILLISECONDS)
            .build();

    public static DoubanSearch getDoubanSearch() {
        if (doubanSearch == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://book.douban.com/")
                    .addConverterFactory(scalarsConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            doubanSearch = retrofit.create(DoubanSearch.class);
        }
        return doubanSearch;
    }

    public static DoubanDownImage getDoubanDownImage() {
        if (doubanDownImage == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://book.douban.com/")
                    .addConverterFactory(scalarsConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            doubanDownImage = retrofit.create(DoubanDownImage.class);
        }
        return doubanDownImage;
    }
}
