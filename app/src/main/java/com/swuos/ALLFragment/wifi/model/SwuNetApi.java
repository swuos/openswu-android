package com.swuos.ALLFragment.wifi.model;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 张孟尧 on 2016/10/18.
 */
public class SwuNetApi {
    private static InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 8888);
    private static Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
    private static ScalarsConverterFactory scalarsConverterFactory = ScalarsConverterFactory.create();
    private static GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static NewSwuNet newSwuNet;
    //    private static NewSwuNetDevice newSwuNetDevice;
    private static SwuNetSelf swuNetSelf;
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
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            //            .proxy(proxy)
            .build();
    private static OkHttpClient okHttpClientSelf = new OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE))
            //            .addNetworkInterceptor(new Interceptor() {
            //                @Override
            //                public Response intercept(Chain chain) throws IOException {
            //                    Request request=chain.request();
            ////                    System.out.println(request.header("Cookie"));
            //                    return chain.proceed(request);
            //                }
            //            })
            //            .proxy(proxy)
            .build();

    public static NewSwuNet getNewSwuNet() {
        if (newSwuNet == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://222.198.127.170")
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(scalarsConverterFactory)
                    .client(okHttpClient)
                    .build();
            newSwuNet = retrofit.create(NewSwuNet.class);
        }
        return newSwuNet;
    }


    public static SwuNetSelf getSwuNetSelf() {
        if (swuNetSelf == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://http://service2.swu.edu.cn/ ")
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(scalarsConverterFactory)
                    .client(okHttpClientSelf)
                    .build();
            swuNetSelf = retrofit.create(SwuNetSelf.class);
        }
        return swuNetSelf;
    }
}
