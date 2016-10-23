package com.swuos.widget.wifi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.mran.polylinechart.BuildConfig;
import com.swuos.ALLFragment.wifi.model.NewSwuNetLoginResultJson;
import com.swuos.ALLFragment.wifi.model.NewSwuNetParse;
import com.swuos.ALLFragment.wifi.model.SwuNetApi;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
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
 * Implementation of App Widget functionality.
 */
public class wifi_widgets extends AppWidgetProvider {

    private static RemoteViews views;
    private static AppWidgetManager mappWidgetManager;
    private static int[] mappWidgetIds;
    private String username;
    private String password;
    private Context mcontext;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.wifi_widgets_layout);
        Intent intentLogin = new Intent(Constant.WIDGET_LOGIN);
        Intent intentlogout = new Intent(Constant.WIDGET_LOGOUT);
        Intent intentloginfo = new Intent(Constant.WIDGET_LOGINFO);
        intentLogin.setAction(Constant.WIDGET_LOGIN);
        intentlogout.setAction(Constant.WIDGET_LOGOUT);
        intentloginfo.setAction(Constant.WIDGET_LOGINFO);
        PendingIntent pendingIntentLogin = PendingIntent.getBroadcast(context, 0, intentLogin, 0);
        PendingIntent pendingIntentLogout = PendingIntent.getBroadcast(context, 0, intentlogout, 0);
        PendingIntent pendingIntentLoginfo = PendingIntent.getBroadcast(context, 0, intentloginfo, 0);
        views.setOnClickPendingIntent(R.id.wifi_widget_login, pendingIntentLogin);
        views.setOnClickPendingIntent(R.id.wifi_widget_logout, pendingIntentLogout);
        views.setOnClickPendingIntent(R.id.wifi_log_info, pendingIntentLoginfo);
        views.setViewVisibility(R.id.frameLayout1, View.INVISIBLE);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        mappWidgetManager = appWidgetManager;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        mappWidgetIds = appWidgetIds;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
           /*打开保存用户信息的文件*/
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //wifi ssid状态获取
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String wifiSsid = wifiInfo.toString();
        String action = intent.getAction();
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        username = sharedPreferences.getString("userName", "");
        password = sharedPreferences.getString("password", "");
        this.mcontext = context;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wifi_widgets_layout);

        if (action.equals(Constant.WIDGET_LOGIN)) {
            views.setTextViewText(R.id.wifi_log_info, "正在登录");
            views.setViewVisibility(R.id.frameLayout1, View.VISIBLE);
            views.setViewVisibility(R.id.frameLayout2, View.INVISIBLE);

            login(username, password, wifiSsid);
        } else if (action.equals(Constant.WIDGET_LOGOUT)) {
            views.setTextViewText(R.id.wifi_log_info, "正在退出");
            views.setViewVisibility(R.id.frameLayout1, View.VISIBLE);
            views.setViewVisibility(R.id.frameLayout2, View.INVISIBLE);
            logout(username, password, wifiSsid);
        } else if (action.equals(Constant.WIDGET_LOGINFO)) {

            views.setViewVisibility(R.id.frameLayout1, View.INVISIBLE);
            views.setViewVisibility(R.id.frameLayout2, View.VISIBLE);
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, wifi_widgets.class);

        //通知AppWidgetProvider更新
        appWidgetManager.updateAppWidget(componentName, views);
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
                String result = s;
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", s);
                if (s.contains("下线成功")) {
                    result = "下线成功";
                } else if (s.contains("帐号没有登录")) {
                    result = "帐号没有登录";

                } else {
                    result = "下线失败";
                }
                Toast.makeText(mcontext, result, Toast.LENGTH_SHORT).show();

                RemoteViews views = new RemoteViews(mcontext.getPackageName(), R.layout.wifi_widgets_layout);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mcontext);
                ComponentName componentName = new ComponentName(mcontext, wifi_widgets.class);
                views.setTextViewText(R.id.wifi_log_info, result);
                //通知AppWidgetProvider更新
                appWidgetManager.updateAppWidget(componentName, views);
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
                String result = s;
                if (BuildConfig.DEBUG)
                    Log.d("IWifiPresenetrCompl", s);
                if (s.contains("登录成功")) {
                    result = "登录成功";
                } else {
                    NewSwuNetLoginResultJson newSwuNetLoginResultJson = NewSwuNetParse.str2json(s, NewSwuNetLoginResultJson.class);

                    if (newSwuNetLoginResultJson.getMessage().equals("")) {
                        result = "登录成功";

                    } else {
                        result = newSwuNetLoginResultJson.getMessage();

                    }

                }
                Toast.makeText(mcontext, result, Toast.LENGTH_SHORT).show();

                RemoteViews views = new RemoteViews(mcontext.getPackageName(), R.layout.wifi_widgets_layout);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mcontext);
                ComponentName componentName = new ComponentName(mcontext, wifi_widgets.class);
                views.setTextViewText(R.id.wifi_log_info, result);
                //通知AppWidgetProvider更新
                appWidgetManager.updateAppWidget(componentName, views);
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
            RemoteViews views = new RemoteViews(mcontext.getPackageName(), R.layout.wifi_widgets_layout);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mcontext);
            ComponentName componentName = new ComponentName(mcontext, wifi_widgets.class);
            views.setTextViewText(R.id.wifi_log_info, s);
            //通知AppWidgetProvider更新
            appWidgetManager.updateAppWidget(componentName, views);
        }
    }

}

