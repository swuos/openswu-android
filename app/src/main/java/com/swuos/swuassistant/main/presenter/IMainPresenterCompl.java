package com.swuos.swuassistant.main.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.mran.polylinechart.BuildConfig;
import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.Service.ClassAlarmService;
import com.swuos.Service.WifiNotificationService;
import com.swuos.swuassistant.BaseApplication;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.main.view.IMainview;
import com.swuos.util.SALog;
import com.swuos.util.updata.FirJson;
import com.swuos.util.updata.GetAppVersion;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;


/**
 * Created by 张孟尧 on 2016/7/20.
 */
public class IMainPresenterCompl {
    IMainview iMainview;
    Context context;
    SharedPreferences sharedPreferences;

    public IMainPresenterCompl(IMainview iMainview, Context context) {
        this.iMainview = iMainview;
        this.context = context;
    }


    public void startServier() {
        SharedPreferences settingSharedPreferences = context.getSharedPreferences("com.swuos.swuassistant_preferences", Context.MODE_PRIVATE);
        Boolean scheduleIsRemind = settingSharedPreferences.getBoolean("schedule_is_should be_remind", false);
        if (scheduleIsRemind) {
            Intent statrtClassAlarmIntent = new Intent(context, ClassAlarmService.class);
            context.startService(statrtClassAlarmIntent);
        }
        Boolean wifiNotification = settingSharedPreferences.getBoolean("wifi_notification_show", false);
        if (wifiNotification) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            int wifistate = wifiManager.getWifiState();
            if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                Intent statrtWifiIntent = new Intent(context, WifiNotificationService.class);
                context.startService(statrtWifiIntent);
            }
        }
    }


    public void initData(TotalInfos totalInfo) {
         /*打开保存用户信息的文件*/
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        totalInfo.setUserName(sharedPreferences.getString("userName", ""));
        totalInfo.setName(sharedPreferences.getString("name", ""));
        totalInfo.setPassword(sharedPreferences.getString("password", ""));
        totalInfo.setSwuID(sharedPreferences.getString("swuID", ""));
        totalInfo.setScheduleDataJson(sharedPreferences.getString("scheduleDataJson", ""));
    }


    public void cleanData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    public void startUpdata() {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, Constant.REQUEST_CODE_ASK_CALL_PHONE);
            return;
        } else {

            FIR.checkForUpdateInFIR("ab63d4ce50e42ddbbc200d7c939a8da7", new VersionCheckCallback() {
                @Override
                public void onSuccess(String versionJson) {
                    SALog.d("IMainPresenterCompl", "check from fir.im success! " + "\n" + versionJson);
                    if (versionJson.contains("西大助手")) {

                        //                        String url = versionJson.substring(versionJson.indexOf("update_url\":\"") + 13, versionJson.indexOf("\",\"binary\""));
                        //
                        //                        String changelog = versionJson.substring(versionJson.indexOf("changelog\":\"") + 12, versionJson.indexOf("\",\"updated_at\""));
                        //                        String versioName = GetAppVersion.getPackageInfo(context).versionName;
                        //                        changelog = changelog.replace("\\r\\n", "\n");
                        //                        Log.d("IMainPresenterCompl", url);
                        //                        Log.d("IMainPresenterCompl", changelog);

                        Gson gson = new Gson();
                        FirJson updatajson = gson.fromJson(versionJson, FirJson.class);
                        final String versionName = GetAppVersion.getPackageInfo(BaseApplication.getContext()).versionName;

                        if (!updatajson.getVersionShort().contains(versionName)) {
                            iMainview.showUpdata(updatajson.getChangelog(), updatajson.getUpdate_url());
                        }

                    }
                }

                @Override
                public void onFail(Exception exception) {
                    SALog.d("fir", "check fir.im fail! " + "\n" + exception.getMessage());
                }

                @Override
                public void onStart() {
                    if (BuildConfig.DEBUG)
                        Log.d("IMainPresenterCompl", "正在获取");
                }

                @Override
                public void onFinish() {
                    if (BuildConfig.DEBUG)
                        Log.d("IMainPresenterCompl", "获取完成");
                }
            });
            //            XiaomiUpdateAgent.update(context,true);
        }
    }
}
