package com.swuos.ALLFragment.wifi.presenter;

import android.content.Context;
import android.util.Log;

import com.mran.polylinechart.BuildConfig;
import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.ALLFragment.wifi.model.NewSwuNetLoginResultJson;
import com.swuos.ALLFragment.wifi.model.NewSwuNetParse;
import com.swuos.ALLFragment.wifi.model.SwuNetApi;
import com.swuos.ALLFragment.wifi.util.Parse;
import com.swuos.ALLFragment.wifi.view.IWifiFragmentView;
import com.swuos.util.wifi.WifiExit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 张孟尧 on 2016/7/23.
 */
public class IWifiPresenetrCompl implements IWifiPresenter {
    IWifiFragmentView iWifiFragmentView;
    Context context;
    private TotalInfos totalInfo = TotalInfos.getInstance();

    public IWifiPresenetrCompl(IWifiFragmentView iWifiFragmentView, Context context) {
        this.iWifiFragmentView = iWifiFragmentView;
        this.context = context;
    }

    @Override
    public void login(final String username, final String password, final String wifissid) {

        final Map<String, String> map = new HashMap<String, String>();
        map.put("userId", username);
        map.put("password", password);
        map.put("service", "%E9%BB%98%E8%AE%A4");
        map.put("operatorPwd", "");
        map.put("operatorUserId", "");
        map.put("validcode", "");
        SwuNetApi.getNewSwuNet().loginPre().flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                if (s.contains("登录成功"))
                    return Observable.just(s);
                map.put("queryString", Parse.getSwuNetInfoQueryString(s));
                return SwuNetApi.getNewSwuNet().login(map);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                iWifiFragmentView.showResult(throwable.getMessage());
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", "throwable:" + throwable.getMessage());
            }

            @Override
            public void onNext(String s) {
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", s);
                if (s.contains("登录成功")) {
                    iWifiFragmentView.showResult("登录成功");

                } else {
                    NewSwuNetLoginResultJson newSwuNetLoginResultJson = NewSwuNetParse.str2json(s, NewSwuNetLoginResultJson.class);

                    if (newSwuNetLoginResultJson.getMessage().equals("")) {
                        iWifiFragmentView.showResult("登录成功");

                    } else
                        iWifiFragmentView.showResult(newSwuNetLoginResultJson.getMessage());
                }
            }
        });
    }

    @Override
    public void logout(final String username, final String password, final String wifissid) {

        SwuNetApi.getSwuNetSelf().loginToCheck(username, password)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return SwuNetApi.getSwuNetSelf().getMyLoginInfo();
                    }
                }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                if (!s.contains("上线时间")) {
                    return Observable.just("帐号没有登录");
                } else {
                    Document document = Jsoup.parse(s);
                    Elements elements = document.getElementById("a1").getAllElements();
                    //                    System.out.println(elements.text().replace("IP : ", ""));
                    return SwuNetApi.getSwuNetSelf().logout("mran:" + elements.text().replace("IP : ", ""));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                iWifiFragmentView.showResult(throwable.getMessage());
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", "throwable:" + throwable.getMessage());
            }

            @Override
            public void onNext(String s) {
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", s);
                if (s.contains("下线成功")) {
                    iWifiFragmentView.showResult("下线成功");
                } else if (s.contains("帐号没有登录")) {
                    iWifiFragmentView.showResult("帐号没有登录");
                } else {
                    iWifiFragmentView.showResult("下线失败");
                }
            }
        });
    }

    @Override
    public void timingLogout(final String username, final String password, final int delaytime, final String wifissid) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(WifiExit.timingLogout(username, password, wifissid, delaytime));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        iWifiFragmentView.showResult(s);
                        if (s.contains("排队成功")) {
                            iWifiFragmentView.showCountDowntimer(true, delaytime * 60 * 1000);
                        }
                    }
                });

    }

    @Override
    public String getUsername() {
        return totalInfo.getUserName();
    }

    @Override
    public String getPassword() {
        return totalInfo.getPassword();
    }

    @Override
    public void initdata() {

    }

    @Override
    public void unregisterReceiver() {

    }


}
