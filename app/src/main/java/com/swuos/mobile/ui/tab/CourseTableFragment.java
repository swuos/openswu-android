package com.swuos.mobile.ui.tab;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gallops.mobile.jmvclibrary.app.BaseFragment;
import com.gallops.mobile.jmvclibrary.app.JApp;
import com.gallops.mobile.jmvclibrary.http.ErrorCode;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.gallops.mobile.jmvclibrary.utils.CommonUtils;
import com.gallops.mobile.jmvclibrary.utils.DateUtils;
import com.gallops.mobile.jmvclibrary.utils.injector.Model;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;
import com.swuos.mobile.app.App;
import com.swuos.mobile.app.Key;
import com.swuos.mobile.entity.BaseInfo;
import com.swuos.mobile.entity.WeekClasses;
import com.swuos.mobile.models.cache.CacheModel;
import com.swuos.mobile.models.http.requester.BindSwuIdRequester;
import com.swuos.mobile.models.http.requester.GetAcProfileRequester;
import com.swuos.mobile.models.http.requester.GetScheduleRequester;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.widgets.WeekClassPreview;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;


/**
 * 课表fragment
 * Created by wangyu on 2018/3/7.
 */

public class CourseTableFragment extends BaseFragment {
    @FindViewById(R.id.class_table)
    private FrameLayout mTableFrameLayout;
    @FindViewById(R.id.schedule_weeks)
    private TextView currentWeekTv;
    @FindViewById(R.id.schedule_years)
    private TextView schedule_years;
    @FindViewById(R.id.choose_week_container)
    private LinearLayout chooseWeekContainer;
    @FindViewById(R.id.choose_week)
    HorizontalScrollView chooseWeekHorizontalScrollView;
    @FindViewById(R.id.table_container)
    LinearLayout tableContainer;
    @FindViewById(R.id.schedule_weeks_im)
    ImageView addMoreScheduleIm;
    @FindViewById(R.id.table_frameLayout)
    FrameLayout tableFrameLayout;
    @FindViewById(R.id.schedule_table_top)
    LinearLayout scheduleTableTop;
    @FindViewById(R.id.logined_layout)
    LinearLayout loginedLayout;
    @FindViewById(R.id.unlogin)
    LinearLayout unloginLayout;
    @Model
    private CacheModel cacheModel;
    private List<WeekClasses> weeksClass;
    private String term;//当前课表所在的学期
    private String academicYear;//当前课表所在学年
    private String tempTerm;//临时保存当前课表所在的学期
    private String tempAcademicYear;//临时保存当前课表所在学年
    private UserModel userModel;
    private int mClazzWidth;//单个课节的宽度
    private int mClazzHeight;//单个课节的高度
    private int currentSelectWeek = 0;//当前显示的周
    private int trueCurrentWeek = 4;//实际的当前周
    private int tableMoveUp;//周课表的预览是移动量
    public static final int[] backgroundcolor = {
            0xFF81C1FF,
            0xFFB4D36C,
            0xFFBBA3F1,
            0xFF8CDFC9,
            0xff6D9FF6,
            0xfff8ce2f,
            0xff48B3FF,
            0xFFFF9E81};

    String academics[] = {"2018-2019", "2017-2018", "2016-2017", "2015-2016"};
    String academicValue[] = {
            "2018",
            "2017",
            "2016",
            "2015"

    };

    void setAcademic() {
        String swuId = App.getInstance().getModel(UserModel.class).getSwuId();
        if (!TextUtils.isEmpty(swuId)) {
            int startYear = Integer.parseInt(swuId.substring(2, 6));
            for (int i = 0; i < 4; i++) {
                academics[i] = "" + (startYear + i) + "-" + (startYear + i + 1);
                academicValue[i] = "" + (startYear + i);
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.f_coursetable;
    }


    @Override
    protected void onCreateView(View rootView, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        initData();
        if (TextUtils.isEmpty(userModel.getSwuId())) {
            GetAcProfileRequester getAcProfileRequester = new GetAcProfileRequester(new OnResultListener<BaseInfo>() {
                @Override
                public void onResult(int code, BaseInfo baseInfo, String msg) {
                    if (code == ErrorCode.RESULT_DATA_OK) {
                        if (!TextUtils.isEmpty(userModel.getSwuId())) {
                            unloginLayout.setVisibility(View.GONE);
                            loginedLayout.setVisibility(View.VISIBLE);
                            setAcademic();
                            initView();
                            getSchedule(false);
                        }
                    }

                }
            });
            getAcProfileRequester.execute();
            unloginLayout.setVisibility(View.VISIBLE);
            loginedLayout.setVisibility(View.GONE);
        } else {
            unloginLayout.setVisibility(View.GONE);
            loginedLayout.setVisibility(View.VISIBLE);
            initView();
            getSchedule(false);

        }

    }

    private void initView() {
        currentWeekTv.setTag(Boolean.FALSE);
        currentWeekTv.setText("第 "+(currentSelectWeek+1)+"周");
        tableContainer.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @OnClick({R.id.schedule_weeks, R.id.schedule_weeks_im, R.id.unlogin})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.unlogin:
                showBindPopuWindow();
                break;
            case R.id.schedule_weeks:
                if (((Boolean) currentWeekTv.getTag()).booleanValue()) {
                    currentWeekTv.setTag(Boolean.FALSE);
                    Log.d(TAG, "onCreateView: " + tableMoveUp);
                    tableContainer.animate().translationY(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            tableContainer.setPadding(0, 0, 0, 0);

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
                } else {
                    currentWeekTv.setTag(Boolean.TRUE);
                    tableMoveUp = chooseWeekContainer.getHeight();
                    Log.d(TAG, "onCreateView: " + tableMoveUp);
                    tableContainer.animate().translationY(tableMoveUp).setDuration(300).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            tableContainer.setPadding(0, 0, -0, tableMoveUp);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
                }
                break;
            case R.id.schedule_weeks_im:
                if (addMoreScheduleIm.getRotation() != 45) {
                    showMorePopuWindow();
                } else {

                    clearMorePopuWindow();
                }
                break;
            default:
                break;

        }
    }

    PopupWindow moreOptionsWindow;
    PopupWindow bindSwuIdWindow;
    PopupWindow selectTermWindow;

    private void showSelectTermPopuWindow() {
        if (selectTermWindow == null) {
            FrameLayout fullFrameLayout = new FrameLayout(getContext());
            FrameLayout.LayoutParams frameLayoutLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            fullFrameLayout.setBackgroundColor(0x33000000);
            fullFrameLayout.setLayoutParams(frameLayoutLp);
            fullFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTermWindow.dismiss();
                }
            });

            FrameLayout.LayoutParams contentViewFrameLayoutLp = new FrameLayout.LayoutParams((int) (loginedLayout.getWidth() * 0.8), FrameLayout.LayoutParams.WRAP_CONTENT);
            contentViewFrameLayoutLp.gravity = Gravity.CENTER_HORIZONTAL;
            contentViewFrameLayoutLp.topMargin = (int) (loginedLayout.getWidth() * 0.2);
            View contentView = LayoutInflater.from(getContext()).inflate(R.layout.v_select_week_term_dialog, null);
            contentView.setElevation(10);
            contentView.setLayoutParams(contentViewFrameLayoutLp);
            LinearLayout academicContainer = contentView.findViewById(R.id.academic_container);
            for (int i = 0; i < academicContainer.getChildCount(); i++) {
                ((TextView) academicContainer.getChildAt(i)).setText(academics[i]);
                ((TextView) academicContainer.getChildAt(i)).setTag(academicValue[i]);
                academicContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < academicContainer.getChildCount(); i++) {
                            ((TextView) academicContainer.getChildAt(i)).setTextColor(0xFF565656);
                        }
                        ((TextView) v).setTextColor(0xff4f87ff);
                        tempAcademicYear = (String) v.getTag();
                        Log.d(TAG, "onClick: " + tempAcademicYear);
                    }
                });
            }
            LinearLayout termContainer = contentView.findViewById(R.id.term_container);
            for (int i = 0; i < termContainer.getChildCount(); i++) {
                termContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < termContainer.getChildCount(); i++) {
                            ((TextView) termContainer.getChildAt(i)).setTextColor(0xFF565656);
                        }
                        ((TextView) v).setTextColor(0xff4f87ff);
                        tempTerm = (String) v.getTag();
                        Log.d(TAG, "onClick: " + tempTerm);
                    }

                });
            }
            contentView.findViewById(R.id.positive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    academicYear = tempAcademicYear;
                    term = tempTerm;
                    cacheModel.putString(Key.ACADEMICYEAR, academicYear);
                    cacheModel.putString(Key.TERM, term);
                    getSchedule(true);
                    String swuId = App.getInstance().getModel(UserModel.class).getSwuId();
                    if (!TextUtils.isEmpty(swuId)) {
                        int startYear = Integer.parseInt(swuId.substring(2, 6));
                        String academic="";
                        if (Integer.parseInt(academicYear) - startYear == 0) {
                            academic = "大一";
                        }
                        if (Integer.parseInt(academicYear) - startYear == 1) {
                            academic = "大二";
                        }
                        if (Integer.parseInt(academicYear) - startYear == 2) {
                            academic = "大三";
                        }
                        if (Integer.parseInt(academicYear) - startYear == 3) {
                            academic = "大四";
                        }
                        schedule_years.setText(academic+ " 第"+term+"学期");

                    }
                    selectTermWindow.dismiss();
                }
            });
            contentView.findViewById(R.id.negetive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTermWindow.dismiss();
                }
            });
            fullFrameLayout.addView(contentView);
            selectTermWindow = new PopupWindow(fullFrameLayout, loginedLayout.getWidth(), loginedLayout.getHeight());

        }
        selectTermWindow.showAsDropDown(loginedLayout, 0, -loginedLayout.getHeight());
    }

    private void showBindPopuWindow() {
        if (bindSwuIdWindow == null) {
            FrameLayout fullFrameLayout = new FrameLayout(getContext());
            FrameLayout.LayoutParams frameLayoutLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            fullFrameLayout.setBackgroundColor(0x33000000);
            fullFrameLayout.setLayoutParams(frameLayoutLp);
            fullFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bindSwuIdWindow.dismiss();
                }
            });

            FrameLayout.LayoutParams contentViewFrameLayoutLp = new FrameLayout.LayoutParams((int) (unloginLayout.getWidth() * 0.8), FrameLayout.LayoutParams.WRAP_CONTENT);
            contentViewFrameLayoutLp.gravity = Gravity.CENTER_HORIZONTAL;
            contentViewFrameLayoutLp.topMargin = (int) (unloginLayout.getWidth() * 0.2);
            View contentView = LayoutInflater.from(getContext()).inflate(R.layout.v_bind_swuid, null);
            contentView.setElevation(10);
            contentView.setLayoutParams(contentViewFrameLayoutLp);
            contentView.findViewById(R.id.positive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bindSwuId(((EditText) (contentView.findViewById(R.id.enter_swuid_ed))).getText().toString(), ((EditText) (contentView.findViewById(R.id.enter_swuid_password_ed))).getText().toString());
                }
            });
            contentView.findViewById(R.id.negetive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bindSwuIdWindow.dismiss();
                }
            });
            fullFrameLayout.addView(contentView);
            bindSwuIdWindow = new PopupWindow(fullFrameLayout, unloginLayout.getWidth(), unloginLayout.getHeight());
            bindSwuIdWindow.setFocusable(true);
            bindSwuIdWindow.update();
        }
        bindSwuIdWindow.showAsDropDown(unloginLayout, 0, -unloginLayout.getHeight());
    }

    private void bindSwuId(String swuId, String swuPassword) {
        if (TextUtils.isEmpty(swuId)) {
            showToast("校园网账号不能为空哦");
            return;
        }
        if (TextUtils.isEmpty(swuPassword)) {
            showToast("校园网密码不能为空哦");
            return;
        }

        showProgressDialog();
        BindSwuIdRequester bindSwuIdRequester = new BindSwuIdRequester(swuId, swuPassword, new OnResultListener<BaseInfo>() {
            @Override
            public void onResult(int code, BaseInfo baseInfo, String msg) {
                dismissProgressDialog();

                if (code == ErrorCode.RESULT_DATA_OK) {
                    getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            bindSwuIdWindow.dismiss();
                            userModel.getAccountInfo().setSwuId(swuId);
                            userModel.saveAccountInfo(userModel.getAccountInfo());
                            unloginLayout.setVisibility(View.GONE);
                            loginedLayout.setVisibility(View.VISIBLE);
                            initView();
                            getSchedule(false);
                        }
                    });
                } else {
                    showToast(msg);
                }
            }
        });
        bindSwuIdRequester.execute();
        Log.d(TAG, "bindSwuId: " + swuId + "swupassword=" + swuPassword);
    }

    private void showMorePopuWindow() {
        addMoreScheduleIm.animate().rotation(45).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animation.getCurrentPlayTime();
            }
        }).start();
//        tableFrameLayout.addShadow();
        if (moreOptionsWindow == null) {
            FrameLayout fullFrameLayout = new FrameLayout(getContext());
            FrameLayout.LayoutParams frameLayoutLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            fullFrameLayout.setBackgroundColor(0x33000000);
            fullFrameLayout.setLayoutParams(frameLayoutLp);
            fullFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearMorePopuWindow();
                }
            });
            LinearLayout contentLinearLayout = new LinearLayout(getContext());
            contentLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            contentLinearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.dot_line));
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(scheduleTableTop.getWidth() / 3, scheduleTableTop.getWidth() / 3);
            layoutParams.gravity = Gravity.RIGHT;
            contentLinearLayout.setOrientation(VERTICAL);
            contentLinearLayout.setElevation(10);
            contentLinearLayout.setBackgroundColor(0xffffffff);
            contentLinearLayout.setLayoutParams(layoutParams);
            String rightText[] = {"添加课程", "修改学期", "课表背景"};
            int leftIcon[] = {R.mipmap.add_schedule, R.mipmap.edit_term, R.mipmap.add_background};
            for (int i = 0; i < 3; i++) {
                View item = LayoutInflater.from(getContext()).inflate(R.layout.v_popuwindow_item, null);
                ((ImageView) (item.findViewById(R.id.left_icon))).setImageDrawable(ContextCompat.getDrawable(getContext(), leftIcon[i]));
                ((TextView) (item.findViewById(R.id.right_text))).setText(rightText[i]);
                item.setTag(i);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch ((int) v.getTag()) {
                            case 0:
                                addSchedule();
                                break;
                            case 1:
                                clearMorePopuWindow();
                                showSelectTermPopuWindow();
                                break;
                            case 2:
                                changeBackGround();
                                break;
                            default:
                                break;

                        }
                    }
                });
                contentLinearLayout.addView(item);
            }
            fullFrameLayout.addView(contentLinearLayout);
            moreOptionsWindow = new PopupWindow(fullFrameLayout, getView().getWidth(), tableContainer.getHeight());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                moreOptionsWindow.setElevation(10);
            }
        }
        moreOptionsWindow.showAsDropDown(scheduleTableTop, 0, 00);
    }


    private void addSchedule() {
        clearMorePopuWindow();
        getBaseActivity().postStartActivity(AddScheduleActivity.class, 0);
        Log.d(TAG, "addSchedule: ");

    }

    private void editTerm() {
        clearMorePopuWindow();

        Log.d(TAG, "editTerm: ");

    }

    private void changeBackGround() {

        clearMorePopuWindow();
        Log.d(TAG, "changeBackGround: ");
    }

    private void clearMorePopuWindow() {
        addMoreScheduleIm.animate().rotation(0).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        if (moreOptionsWindow != null && moreOptionsWindow.isShowing()) {
            moreOptionsWindow.dismiss();
        }
    }

    private void initData() {
        userModel = JApp.getInstance().getModel(UserModel.class);
        academicYear = cacheModel.getString(Key.ACADEMICYEAR, getCurrtAcademicYear());
        term = cacheModel.getString(Key.TERM, getCurrentTerm());
    }

    private void initChoosePreviewWeeks() {
        int weekPreviewWidth = CommonUtils.getScreenWidth() / 5;
        chooseWeekContainer.removeAllViews();
        for (int i = 0; i < weeksClass.size(); i++) {
            WeekClasses weekClasses = weeksClass.get(i);
            int[][] preview = new int[4][5];
            for (int j = 0; j < weekClasses.getWeekItem().size(); j++) {
                WeekClasses.WeekItem classItemDetail = weekClasses.getWeekItem().get(j);
                if (classItemDetail.getDay() > 5)
                    continue;
                int dayofweek = classItemDetail.getDay() - 1;
                int updown = 0;
                if (classItemDetail.getStartTime() >= 12)
                    updown = 3;
                else if (classItemDetail.getStartTime() >= 8)
                    updown = 2;
                else if (classItemDetail.getStartTime() >= 4)
                    updown = 1;
                else updown = 0;
                preview[updown][dayofweek]++;
                if (preview[updown][dayofweek] > 2)
                    preview[updown][dayofweek] = 2;

            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(weekPreviewWidth, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(5, 20, 5, 20);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.v_choose_week_item, null);
            if (i == trueCurrentWeek) {
                ((TextView) view.findViewById(R.id.show_flag)).setVisibility(View.VISIBLE);
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_preview__current_bg));
            } else if (i == currentSelectWeek) {
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_preview__select_bg));
            } else {
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_preview__normal_bg));
            }
            view.setLayoutParams(layoutParams);
            ((TextView) view.findViewById(R.id.week_of_schedule)).setText("" + (i + 1));
            ((WeekClassPreview) view.findViewById(R.id.week_class_preview)).setPreview(preview);
            int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: " + finalI);
                    currentSelectWeek = finalI;
                    for (int j = 0; j < chooseWeekContainer.getChildCount(); j++) {
                        if (j == finalI)
                            chooseWeekContainer.getChildAt(j).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_preview__select_bg));
                        else if (j == trueCurrentWeek)
                            chooseWeekContainer.getChildAt(j).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_preview__current_bg));
                        else
                            chooseWeekContainer.getChildAt(j).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_preview__normal_bg));
                    }
                    currentWeekTv.setText("第 "+(currentSelectWeek+1)+"周");

                    padingTable();
                }
            });
            chooseWeekContainer.addView(view);

        }
    }

    private void getSchedule(boolean isForce) {

//        if (weeksClass == null) {
//            weeksClass = new AllWeeksClass();
//            ArrayList<AllWeeksClass.WeekClasses> arrayList = new ArrayList<>();
//            for (int k = 0; k < 20; k++) {
//                ArrayList<ClassItemDetail> classItemDetails = new ArrayList<>();
//                AllWeeksClass.WeekClasses weekClasses = new AllWeeksClass.WeekClasses();
//                for (int i = 0; i < 5; i++) {
//                    for (int j = 0; j < 3; j++) {
//                        ClassItemDetail classItemDetail = new ClassItemDetail();
//                        classItemDetail.setDay(i + 1);
//                        classItemDetail.setClassRoom("38-201");
//                        classItemDetail.setCampus("南区");
//                        classItemDetail.setAcademicYear("2017");
//                        classItemDetail.setTerm("1");
//                        classItemDetail.setLessonId("2018-23-123");
//                        classItemDetail.setLessonName("植物化学保护");
//                        classItemDetail.setTeacher("肖伟");
//                        classItemDetail.setStartTime(j * 3 + 1);
//                        classItemDetail.setEndTime(j * 3 + 5);
//                        classItemDetail.setWeek("星期三");
//                        classItemDetails.add(classItemDetail);
//                    }
//                }
//                weekClasses.setWeekItem(classItemDetails);
//                weekClasses.setWeekSort(k + 1);
//                arrayList.add(weekClasses);
//            }
//
//            weeksClass.setWeekClasses(arrayList);
//            cacheModel.putObject(Key.SCHEDULE, weeksClass);
//        }

        if (isForce) {
            weeksClass = null;
        } else
            weeksClass = cacheModel.getList(Key.SCHEDULE, WeekClasses.class);

        if (weeksClass == null) {
            showProgressDialog("正在获取");
            GetScheduleRequester getScheduleRequester = new GetScheduleRequester(userModel.getSwuId(), academicYear, term, new OnResultListener<List<WeekClasses>>() {
                @Override
                public void onResult(int code, List<WeekClasses> weekClasses, String msg) {
                    dismissProgressDialog();
                    if (code == ErrorCode.RESULT_DATA_OK) {
                        weeksClass = weekClasses;
                        cacheModel.putObject(Key.SCHEDULE, weeksClass);
                        getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                initChoosePreviewWeeks();
                                padingTable();
                            }
                        });
                    }
                }
            });
            getScheduleRequester.execute();
        }


    }

    private void padingTable() {
        mTableFrameLayout.removeAllViews();
        mClazzWidth = (getActivity().getResources().getDisplayMetrics().widthPixels - getResources().getDimensionPixelSize(R.dimen.table_width)) / 7;
        mClazzHeight = getResources().getDimensionPixelSize(R.dimen.each_class_hight);
        WeekClasses currentWeekClasses = weeksClass.get(currentSelectWeek);
        for (int i = 0; i < currentWeekClasses.getWeekItem().size(); i++) {
            WeekClasses.WeekItem classItemDetail = currentWeekClasses.getWeekItem().get(i);
            /*设置新的布局参数*/
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams
                    .WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            /*建一个新的textview*/
            TextView textView = new TextView(getActivity());

            /*设置高度,用节数乘以一节课的高度*/
            textView.setHeight(mClazzHeight * (classItemDetail.getEndTime() + 1 - classItemDetail.getStartTime()) - 4);
            /*设置宽度*/
            textView.setWidth(mClazzWidth - 4);
            /*设置距离上边的距离,用一节课的固定高度乘以开始的节次*/
            layoutParams.topMargin = mClazzHeight * (classItemDetail.getStartTime() - 1);
            //                layoutParams.setMargins(width * (scheduleItem.getXqj()-1),hight * scheduleItem
            // .getEnd(),0,0);
            /*设置距离左边的距离,用固定宽度乘以该课的上课日*/
            layoutParams.leftMargin = mClazzWidth * (classItemDetail.getDay() - 1);

            textView.setLayoutParams(layoutParams);
            textView.setText(classItemDetail.getLessonName() + classItemDetail.getCampus() + classItemDetail.getClassRoom());
            /*设置背景色*/
            textView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_class_bg));
            textView.getBackground().setColorFilter(backgroundcolor[i % 7], PorterDuff.Mode.ADD);
            textView.setTag(currentWeekClasses.getWeekItem().size() - 1);
            textView.setTextColor(0xffffffff);
            textView.setTextSize(12);
            textView.setPadding(10, 0, 10, 0);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showSingaleClass((Integer) v.getTag());
                }
            });
            textView.setId(i);
//            ScheduleData.ScheduleDetail scheduleDetail = new ScheduleData.ScheduleDetail();
//            scheduleDetail.setScheduleItem(scheduleItem);
//            scheduleDetail.setTextView(textView);
//            scheduleDetail.setColor(Constant.backgroundcolor[i % 8]);
            /*将新建的textview加入列表*/
//            textViewList.add(scheduleDetail);
            /*将新建的textview加入布局*/

            mTableFrameLayout.addView(textView);
        }
    }


    private String getCurrtAcademicYear() {
        int currntYear = DateUtils.getCurrntYear();
        int month = DateUtils.getCurrntMonth();
        if (month < 9) {
            cacheModel.putString(Key.ACADEMICYEAR, "" + (currntYear - 1));
            return "" + (currntYear - 1);
        } else {
            cacheModel.putString(Key.ACADEMICYEAR, "" + currntYear);
            return currntYear + "";
        }
    }

    private String getCurrentTerm() {
        int month = DateUtils.getCurrntMonth();
        if (month < 9) {
            cacheModel.putString(Key.TERM, "" + 2);
            return "" + 2;
        } else {
            cacheModel.putString(Key.TERM, "" + 3);
            return "" + 3;
        }
    }
}


