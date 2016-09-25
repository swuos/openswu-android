package com.swuos.ALLFragment.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.swuos.ALLFragment.BaseFragment;
import com.swuos.ALLFragment.wifi.presenter.IWifiPresenetrCompl;
import com.swuos.ALLFragment.wifi.presenter.IWifiPresenter;
import com.swuos.ALLFragment.wifi.view.IWifiFragmentView;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

/**
 * Created by 张孟尧 on 2016/4/27.
 */
public class WifiFragment extends BaseFragment implements IWifiFragmentView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, CompoundButton.OnCheckedChangeListener, RangeBar.OnThumbMoveListener {

    private Button login_button;
    private Button logout_button;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView wifiStateTextView;
    private TextView wifiUsername;
    private RangeBar rangeBar;
    private Switch aSwitch;
    private LocalRecevier localRecevier;
    private LocalBroadcastManager localBroadcastManager;
    private IWifiPresenter iWifiPresenter;
    private String wifissid;
    private WifiStateReciver wifiStateReciver;
    private IntentFilter wififilter;
    private IntentFilter nameIntentFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.wifis_contain_layout, container, false);
        iWifiPresenter = new IWifiPresenetrCompl(this, getContext());
        iWifiPresenter.initdata();
        initview();
        setReceiver();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SALog.d("WifiFragment", "onDestroyView");
        view = null;
    }

    private void initview() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.wifi_SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(this);
        login_button = (Button) view.findViewById(R.id.wifi_login_button);
        logout_button = (Button) view.findViewById(R.id.wifi_logout_button);
        wifiStateTextView = (TextView) view.findViewById(R.id.wifi_state);
        wifiUsername = (TextView) view.findViewById(R.id.wifi_username);
        rangeBar = (RangeBar) view.findViewById(R.id.rangebar);
        aSwitch = (Switch) view.findViewById(R.id.timing_switch);
        aSwitch.setOnCheckedChangeListener(this);
        logout_button.setOnClickListener(this);
        login_button.setOnClickListener(this);
        wifiStateTextView.setOnClickListener(this);
        wifiUsername.setText("当前用户:" + iWifiPresenter.getUsername());

        rangeBar.setTickEnd(240);
        rangeBar.setTickInterval(10);
        //        rangeBar.setTickEnd(10);
        rangeBar.setSeekPinByIndex(0);
        rangeBar.setVisibility(View.INVISIBLE);
        rangeBar.setEnabled(false);
        rangeBar.setThumbMoveListener(this);
        aSwitch.setChecked(false);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.wifi_login_button:
                login_button.setClickable(false);
                iWifiPresenter.login(iWifiPresenter.getUsername(), iWifiPresenter.getPassword(), wifissid);
                swipeRefreshLayout.setRefreshing(true);
                break;
            case R.id.wifi_logout_button:
                logout_button.setClickable(false);
                iWifiPresenter.logout(iWifiPresenter.getUsername(), iWifiPresenter.getPassword(), wifissid);
                swipeRefreshLayout.setRefreshing(true);
                break;
            case R.id.wifi_state:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            default:
                break;
        }
    }


    @Override
    public void showResult(String result) {
        swipeRefreshLayout.setRefreshing(false);
        login_button.setClickable(true);
        logout_button.setClickable(true);
        rangeBar.setEnabled(true);
        Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void changeWifiState(String state) {
        wifiStateTextView.setText(state);
    }

    @Override
    public void onRefresh() {

    }

    private void setReceiver() {
        //注册本地广播接收器

        nameIntentFilter = new IntentFilter();
        nameIntentFilter.addAction("com.swuos.Logined");
        localRecevier = new LocalRecevier();
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(localRecevier, nameIntentFilter);
        wififilter = new IntentFilter();
        wififilter.setPriority(1000);
        wififilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        wififilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        wififilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        wifiStateReciver = new WifiStateReciver();
        getActivity().registerReceiver(wifiStateReciver, wififilter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            rangeBar.setVisibility(View.VISIBLE);
            rangeBar.setEnabled(true);
        } else {
            rangeBar.setVisibility(View.INVISIBLE);
            rangeBar.setEnabled(false);
        }
    }

    @Override
    public void onThumbMovingStart(RangeBar rangeBar, boolean isLeftThumb) {

    }

    @Override
    public void onThumbMovingStop(RangeBar rangeBar, boolean isLeftThumb) {
        swipeRefreshLayout.setRefreshing(true);
        rangeBar.setEnabled(false);
        iWifiPresenter.timingLogout(iWifiPresenter.getUsername(), iWifiPresenter.getPassword(), Integer.valueOf(rangeBar.getRightPinValue()), wifissid);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(wifiStateReciver);
        localBroadcastManager.unregisterReceiver(localRecevier);
    }

    /*设置广播接收刷新消息*/
    class LocalRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            wifiUsername.setText("当前用户:" + iWifiPresenter.getUsername());

        }
    }

    class WifiStateReciver extends BroadcastReceiver {
        @Override


        public void onReceive(Context context, Intent intent) {
//            SALog.d("WifiStateReciver", intent.getAction());
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            wifissid = wifiInfo.getSSID();
            int wifiState = wifiManager.getWifiState();
            switch (wifiState) {
                case WifiManager.WIFI_STATE_ENABLING:
//                    SALog.d("WifiStateReciver", "WIFI_STATE_ENABLING");
                    changeWifiState("正在打开WIFI");
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
//                    SALog.d("WifiStateReciver", "WIFI_STATE_ENABLED");
                    changeWifiState("WIFI已打开");
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            changeWifiState(wifissid.replace("\"", ""));
                        }
                    } else if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                            changeWifiState("正在连接 " + wifissid);
                        }
                    }
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    changeWifiState("WIFI已关闭");
//                    SALog.d("WifiStateReciver", "WIFI_STATE_DISABLED");
                    break;

            }


        }
    }
}
