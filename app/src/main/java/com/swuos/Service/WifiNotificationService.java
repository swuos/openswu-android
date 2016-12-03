package com.swuos.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.swuos.swuassistant.main.MainActivity;

/**
 * Created by 张孟尧 on 2016/5/14.
 */
public class WifiNotificationService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    private void showNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.wifi_notification_layout);
        Intent intentLogin = new Intent(Constant.NOTIFICATION_LOGIN);
        intentLogin.setAction(Constant.NOTIFICATION_LOGIN);
        Intent intentlogout = new Intent(Constant.NOTIFICATION_LOGOUT);
        intentlogout.setAction(Constant.NOTIFICATION_LOGOUT);
        PendingIntent pendingIntentactivity = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentLogin = PendingIntent.getBroadcast(this, 0, intentLogin, 0);
        PendingIntent pendingIntentLogout = PendingIntent.getBroadcast(this, 0, intentlogout, 0);
        remoteViews.setOnClickPendingIntent(R.id.wifi_notification_login, pendingIntentLogin);
        remoteViews.setOnClickPendingIntent(R.id.wifi_notification_logout, pendingIntentLogout);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Notification notification = mBuilder.setContent(remoteViews).setTicker("校园wifi").setContentIntent(pendingIntentactivity).setOngoing(true).setSmallIcon(R.mipmap.icon_notification).build();
        startForeground(Constant.NOTIFICATION_WIFI_ID, notification);
    }


}


