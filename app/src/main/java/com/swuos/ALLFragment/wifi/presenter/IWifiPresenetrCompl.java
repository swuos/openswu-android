package com.swuos.ALLFragment.wifi.presenter;

import android.content.Context;
import android.util.Log;

import com.mran.polylinechart.BuildConfig;
import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.ALLFragment.wifi.model.NewSwuNetLoginResultJson;
import com.swuos.ALLFragment.wifi.model.NewSwuNetParse;
import com.swuos.ALLFragment.wifi.model.SwuNetApi;
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
        //        Observable.create(new Observable.OnSubscribe<String>() {
        //            @Override
        //            public void call(Subscriber<? super String> subscriber) {
        //                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
        //                    subscriber.onNext(context.getString(R.string.not_logged_in));
        //                else
        //                    subscriber.onNext(WifiLogin.login(username, password, wifissid));
        //            }
        //        }).subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(new Action1<String>() {
        //                    @Override
        //                    public void call(String s) {
        //                        iWifiFragmentView.showResult(s);
        //                    }
        //                });
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", username);
        map.put("password", password);
        map.put("service", "%E9%BB%98%E8%AE%A4");
        map.put("queryString", "wlanacname%3Dc3d7ed6d307ae29d%26ssid%3D46be4f158ac727af%26nasip%3Df9dbb3fe11a1f4e3b5cce4a65fc79cf9%26mac%3D9bca081b48d1f514ce2f43e9408158aa%26t%3Dwireless-v2%26url%3Dbc769469379bc92a49dd39c8187326462c2c594662118267");
        map.put("operatorPwd", "");
        map.put("operatorUserId", "");
        map.put("validcode", "");
        SwuNetApi.getNewSwuNet().login(map)
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
        //        Observable.create(new Observable.OnSubscribe<String>() {
        //            @Override
        //            public void call(Subscriber<? super String> subscriber) {
        //                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
        //                    subscriber.onNext(context.getString(R.string.not_logged_in));
        //                else
        //                    subscriber.onNext(WifiExit.logout(username, password, wifissid));
        //
        //            }
        //        }).subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(new Action1<String>() {
        //                    @Override
        //                    public void call(String s) {
        //                        iWifiFragmentView.showResult(s);
        //                    }
        //                });

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
