package com.swuos.BroacastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mran.polylinechart.BuildConfig;
import com.swuos.ALLFragment.wifi.model.NewSwuNetLoginResultJson;
import com.swuos.ALLFragment.wifi.model.NewSwuNetParse;
import com.swuos.ALLFragment.wifi.model.SwuNetApi;
import com.swuos.Service.WifiNotificationService;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;
import com.swuos.util.tools.MToast;
import com.swuos.util.wifi.WifiExit;
import com.swuos.util.wifi.WifiLogin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 张孟尧 on 2016/5/16.
 */
public class MwifiBroadcast extends BroadcastReceiver {
    private Context mcontext;
    private String username;
    private String password;

    @Override
    public void onReceive(Context context, Intent intent) {
        mcontext = context;
        //wifi ssid状态获取
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String wifiSsid = wifiInfo.toString();

        String action = intent.getAction();
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        username = sharedPreferences.getString("userName", "");
        password = sharedPreferences.getString("password", "");

        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifistate = wifiManager.getWifiState();

            if (wifistate == WifiManager.WIFI_STATE_DISABLED || wifistate == WifiManager.WIFI_STATE_DISABLING) {
                Intent stopIntent = new Intent(context, WifiNotificationService.class);
                context.stopService(stopIntent);
                SALog.d("setting", "关闭前台服务");
                SALog.d("wifi", "WIFI关闭");
            }

            if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                SharedPreferences settingSharedPreferences = context.getSharedPreferences("com.swuos.swuassistant_preferences", context.MODE_PRIVATE);
                Boolean wifiNotification = settingSharedPreferences.getBoolean("wifi_notification_show", true);
                if (wifiNotification) {
                    Intent statrtIntent = new Intent(context, WifiNotificationService.class);
                    context.startService(statrtIntent);
                    SALog.d("setting", "开启前台服务");
                    SALog.d("wifi", "WIFI开启");
                }
            }
        }

        if (action.equals(Constant.NOTIFICATION_LOGIN)) {
            Toast.makeText(context, "正在登录", Toast.LENGTH_SHORT).show();
            login(username, password, wifiSsid);
        } else if (action.equals(Constant.NOTIFICATION_LOGOUT)) {
            Toast.makeText(context, "正在登出", Toast.LENGTH_SHORT).show();

            logout(username, password, wifiSsid);
        }

    }

    private void logout(final String username, final String password, final String wifissid) {


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
                Toast.makeText(mcontext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", "throwable:" + throwable.getMessage());
            }

            @Override
            public void onNext(String s) {
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", s);
                if (s.contains("下线成功")) {
                    Toast.makeText(mcontext, "下线成功", Toast.LENGTH_SHORT).show();
                } else if (s.contains("帐号没有登录")) {
                    Toast.makeText(mcontext, "帐号没有登录", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(mcontext, "下线失败", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void login(final String username, final String password, final String wifissid) {

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
                Toast.makeText(mcontext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", "throwable:" + throwable.getMessage());
            }

            @Override
            public void onNext(String s) {
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", s);
                if (s.contains("登录成功")) {
                    Toast.makeText(mcontext, "登录成功", Toast.LENGTH_SHORT).show();

                } else {
                    NewSwuNetLoginResultJson newSwuNetLoginResultJson = NewSwuNetParse.str2json(s, NewSwuNetLoginResultJson.class);

                    if (newSwuNetLoginResultJson.getMessage().equals("")) {
                        Toast.makeText(mcontext, "登录成功", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(mcontext, newSwuNetLoginResultJson.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    class Mytask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            String wifissid = params[0];
            String todo = params[1];
            if (todo.equals("logout")) {
                return WifiExit.logout(username, password, wifissid);
            } else
                return WifiLogin.login(username, password, wifissid);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MToast.show(mcontext, s, Toast.LENGTH_SHORT);

        }
    }
}