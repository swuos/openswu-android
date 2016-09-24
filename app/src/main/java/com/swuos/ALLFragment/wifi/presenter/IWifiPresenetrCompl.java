package com.swuos.ALLFragment.wifi.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.ALLFragment.wifi.view.IWifiFragmentView;
import com.swuos.swuassistant.R;
import com.swuos.util.wifi.WifiExit;
import com.swuos.util.wifi.WifiLogin;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
    public void login(final String username, final String password,final  String wifissid) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
                    subscriber.onNext(context.getString(R.string.not_logged_in));
                else
                    subscriber.onNext(WifiLogin.login(username, password, wifissid));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        iWifiFragmentView.showResult(s);
                    }
                });
    }

    @Override
    public void logout(final String username, final String password,final  String wifissid) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
                    subscriber.onNext(context.getString(R.string.not_logged_in));
                else
                    subscriber.onNext(WifiExit.logout(username, password, wifissid));

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        iWifiFragmentView.showResult(s);
                    }
                });
    }

    @Override
    public void timingLogout(final String username, final String password, final int delaytime,final  String wifissid) {
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
      /*  IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        broadcastManager.registerReceiver(wifiStateBroad, filter);*/
    }

    @Override
    public void unregisterReceiver() {

    }



}
