package com.swuos.ALLFragment.swujw.schedule;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.swuos.ALLFragment.BaseFragment;
import com.swuos.ALLFragment.swujw.schedule.adapter.ScheduleViewpagerAdapter;
import com.swuos.ALLFragment.swujw.schedule.model.CurrentWeek;
import com.swuos.ALLFragment.swujw.schedule.presenter.ISchedulePresenter;
import com.swuos.ALLFragment.swujw.schedule.presenter.SchedulePresenterCompl;
import com.swuos.ALLFragment.swujw.schedule.view.IScheduleView;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class ScheduleFragment extends BaseFragment implements IScheduleView, SwipeRefreshLayout.OnRefreshListener {

    TabLayout tabLayout;
    long lastclickTime = 0;
    /*viewpager*/
    private ViewPager sceduleViewPager;
    private View schedule_layout;
    /*下拉刷新布局*/
    private SwipeRefreshLayout swipeRefreshLayout;
    private LocalBroadcastManager localBroadcastManager;
    private List<Fragment> scheduleTabblefragmentList;    //保存所有的单周课表fragment
    private ISchedulePresenter iSchedulePresenter;
    private StudyYeayChageReciver studyYeayChageReciver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iSchedulePresenter = new SchedulePresenterCompl(getContext(), this);
        scheduleTabblefragmentList = new ArrayList<Fragment>();
        for (int i = 0; i < 21; i++) {
            /*批量加载课程表,参数i为周次*/
            scheduleTabblefragmentList.add(ScheduleTableFragment.newInstance(i));
        }
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        schedule_layout = inflater.inflate(R.layout.schedule_layout, container, false);
        bindView();
        initView();
        return schedule_layout;
    }

    void bindView() {
        sceduleViewPager = (ViewPager) schedule_layout.findViewById(R.id.schedule_viewpager);
        swipeRefreshLayout = (SwipeRefreshLayout) schedule_layout.findViewById(R.id.schedule_SwipeRefreshLayout);
        tabLayout = (TabLayout) schedule_layout.findViewById(R.id.schedule_tablayout);

    }

    void initView() {
        setSceduleViewPager();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        tabLayout.setupWithViewPager(sceduleViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        SALog.d("ScheduleFm", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        iSchedulePresenter.initData();
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        studyYeayChageReciver = new StudyYeayChageReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("study_date_change");
        localBroadcastManager.registerReceiver(studyYeayChageReciver, intentFilter);
    }

    private void setSceduleViewPager() {
        /*设置适配器*/
        ScheduleViewpagerAdapter scheduleViewpagerAdapter = new ScheduleViewpagerAdapter(
                getChildFragmentManager(),
                scheduleTabblefragmentList
        );
        sceduleViewPager.setAdapter(scheduleViewpagerAdapter);
        /*设置预加载页面数*/
        sceduleViewPager.setOffscreenPageLimit(1);
        /*第一次打开展示当前周的课表*/
        sceduleViewPager.setCurrentItem(CurrentWeek.getweek(), true);
        sceduleViewPager.setOnTouchListener(new View.OnTouchListener() {
            /*避免scrollow没在顶部就允许下拉刷新*/
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {
        if (iSchedulePresenter.getUsername() != null && !iSchedulePresenter.getUsername().equals("")) {
            iSchedulePresenter.getSchedule(iSchedulePresenter.getUsername(), iSchedulePresenter.getPassword(), iSchedulePresenter.getXnm(), iSchedulePresenter.getXqm());
        } else {
            Toast.makeText(getActivity(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
            showDialog(false);
        }
    }

    @Override
    public void showDialog(Boolean isShow) {
        if (isShow) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showResult() {
/*获得课程表成功,发送广播要求所有页面加载课表*/
        Intent intent = new Intent("com.swuos.ALLFragment.swujw.schedule.SCHEDULEDATECHANGE");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        schedule_layout = null;
    }

    class StudyYeayChageReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            iSchedulePresenter.setXnmAndXqm();
            onRefresh();
        }
    }
}