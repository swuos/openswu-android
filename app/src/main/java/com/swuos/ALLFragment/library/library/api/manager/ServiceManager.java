package com.swuos.ALLFragment.library.library.api.manager;

import com.swuos.ALLFragment.library.library.api.service.LibService;
import com.swuos.ALLFragment.library.library.utils.Configs;

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
 * Created by : youngkaaa on 2016/10/27.
 * Contact me : 645326280@qq.com
 */
public class ServiceManager {
    private LibService libService;
    public static ServiceManager serviceManager;
    private Object monitor=new Object();
    private CookieJar cookieJar=new CookieJar() {
        private List<Cookie> cookies=new ArrayList<>();
        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            cookies.addAll(list);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return cookies==null?new ArrayList<Cookie>():cookies;
        }
    };

    private OkHttpClient client=new OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .readTimeout(Configs.TIME_OUT, TimeUnit.SECONDS)
            .build();

    public static ServiceManager getInstance(){
        if(serviceManager==null){
            synchronized (ServiceManager.class){
                serviceManager=new ServiceManager();
            }
        }
        return serviceManager;
    }

    public LibService getLibService(){
        if(libService==null){
            synchronized (monitor){
                libService=new Retrofit.Builder()
                        .baseUrl(Configs.BASE_URL)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(client)
                        .build().create(LibService.class);
            }
        }
        return libService;
    }
}
