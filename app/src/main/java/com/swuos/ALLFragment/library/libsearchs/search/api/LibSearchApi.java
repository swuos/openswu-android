package com.swuos.ALLFragment.library.libsearchs.search.api;

import com.swuos.swuassistant.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 张孟尧 on 2016/10/25.
 */
public class LibSearchApi {
    private static RxJavaCallAdapterFactory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static ScalarsConverterFactory scalarsConverterFactory = ScalarsConverterFactory.create();
    private static LibSearch libSearch;
    private static CookieJar cookieJar = new CookieJar() {
        List<Cookie> cookies=new ArrayList<>();

        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            cookies.addAll(list);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };


    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(cookieJar)
//            .proxy(proxy)
            .readTimeout(Constant.TIMEOUT, TimeUnit.MILLISECONDS)
            .build();

    public static LibSearch getLibSearch() {
        if (libSearch == null) {
            Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                    .baseUrl("http://202.202.121.3:99/")
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(scalarsConverterFactory)
                    .build();
            libSearch = retrofit.create(LibSearch.class);
        }
        return libSearch;
    }
}
