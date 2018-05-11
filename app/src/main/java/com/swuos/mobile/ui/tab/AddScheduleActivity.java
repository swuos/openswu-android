package com.swuos.mobile.ui.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gallops.mobile.jmvclibrary.app.BaseActivity;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;

import java.util.ArrayList;

public class AddScheduleActivity extends BaseActivity {
    @FindViewById(R.id.root)
    LinearLayout rootLinearLayou;
    @FindViewById(R.id.top_bar)
    RelativeLayout topBarRl;
    @FindViewById(R.id.back_im)
    Button backIm;
    @FindViewById(R.id.commit)
    Button commitTv;
    @FindViewById(R.id.lesson_name)
    EditText lessonNameEd;
    @FindViewById(R.id.lesson_room_address)
    EditText roomAddressEd;
    @FindViewById(R.id.lesson_weeks)
    TextView lessonWeeksTv;
    @FindViewById(R.id.lesson_time)
    TextView lessonTimeTv;
    @FindViewById(R.id.lesson_teacher)
    TextView lessonTeacherTv;


    @Override
    protected int getLayoutResId() {
        return R.layout.a_add_schedule;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.back_im, R.id.commit, R.id.lesson_name, R.id.lesson_room_address, R.id.lesson_weeks, R.id.lesson_time, R.id.lesson_teacher})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_im:
                checkFinish();
                break;
            case R.id.commit:
                commitSchedule();
                break;
            case R.id.lesson_name:
                break;
            case R.id.lesson_room_address:
                break;
            case R.id.lesson_weeks:
                showSelectWeeksWindow();
                break;
            case R.id.lesson_time:
                showSelectTimesWindow();

                break;
            case R.id.lesson_teacher:
                break;
            default:
                break;

        }

    }

    void checkFinish() {
        finish();
    }

    void commitSchedule() {

    }

    PopupWindow weeksPopupWindow;
    ArrayList<TextView> weeksItems = new ArrayList<>();
    PopupWindow timesPopuWindow;

    void showSelectWeeksWindow() {
        FrameLayout fullFrameLayout = new FrameLayout(this);
        fullFrameLayout.setBackgroundColor(0x33000000);
        fullFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weeksPopupWindow != null) {
                    weeksPopupWindow.dismiss();
                }
            }
        });
        FrameLayout.LayoutParams fllp = new FrameLayout.LayoutParams((int) (topBarRl.getWidth() * 0.8f), FrameLayout.LayoutParams.WRAP_CONTENT);
        fllp.gravity = Gravity.CENTER_HORIZONTAL;
        fllp.topMargin = rootLinearLayou.getHeight() / 8;
        if (weeksPopupWindow == null) {
            View contentView = LayoutInflater.from(getContext()).inflate(R.layout.v_select_weeks_dialog, null);
            LinearLayout weeksItemContainerLinearLayout = contentView.findViewById(R.id.weeks_item_container);
            contentView.findViewById(R.id.single_week).setTag(false);
            contentView.findViewById(R.id.single_week).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Button) contentView.findViewById(R.id.all_week)).setTextColor(0xFF565656);
                    contentView.findViewById(R.id.all_week).setBackgroundColor(0xffffffff);
                    contentView.findViewById(R.id.all_week).setTag(false);

                    ((Button) contentView.findViewById(R.id.double_week)).setTextColor(0xFF565656);
                    contentView.findViewById(R.id.double_week).setBackgroundColor(0xffffffff);
                    contentView.findViewById(R.id.double_week).setTag(false);

                    if (((Boolean) contentView.findViewById(R.id.single_week).getTag())) {
                        ((Button) contentView.findViewById(R.id.single_week)).setTextColor(0xFF565656);
                        contentView.findViewById(R.id.single_week).setBackgroundColor(0xffffffff);
                        contentView.findViewById(R.id.single_week).setTag(false);
                        clearWeeks();
                    } else {
                        ((Button) contentView.findViewById(R.id.single_week)).setTextColor(0xffffffff);
                        contentView.findViewById(R.id.single_week).setBackgroundColor(0xff6c9bff);
                        contentView.findViewById(R.id.single_week).setTag(true);
                        selectSingleWeeks();
                    }
                }
            });

            contentView.findViewById(R.id.double_week).setTag(false);
            contentView.findViewById(R.id.double_week).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ((Button) contentView.findViewById(R.id.all_week)).setTextColor(0xFF565656);
                    contentView.findViewById(R.id.all_week).setBackgroundColor(0xffffffff);
                    contentView.findViewById(R.id.all_week).setTag(false);

                    ((Button) contentView.findViewById(R.id.single_week)).setTextColor(0xFF565656);
                    contentView.findViewById(R.id.single_week).setBackgroundColor(0xffffffff);
                    contentView.findViewById(R.id.single_week).setTag(false);

                    if (((Boolean) contentView.findViewById(R.id.double_week).getTag())) {
                        ((Button) contentView.findViewById(R.id.double_week)).setTextColor(0xFF565656);
                        contentView.findViewById(R.id.double_week).setBackgroundColor(0xffffffff);
                        contentView.findViewById(R.id.double_week).setTag(false);
                        clearWeeks();
                    } else {
                        ((Button) contentView.findViewById(R.id.double_week)).setTextColor(0xffffffff);
                        contentView.findViewById(R.id.double_week).setBackgroundColor(0xff6c9bff);
                        contentView.findViewById(R.id.double_week).setTag(true);
                        selectDoubleWeeks();
                    }

                }
            });

            contentView.findViewById(R.id.all_week).setTag(false);
            contentView.findViewById(R.id.all_week).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((Button) contentView.findViewById(R.id.single_week)).setTextColor(0xFF565656);
                    contentView.findViewById(R.id.single_week).setBackgroundColor(0xffffffff);
                    contentView.findViewById(R.id.single_week).setTag(false);

                    ((Button) contentView.findViewById(R.id.double_week)).setTextColor(0xFF565656);
                    contentView.findViewById(R.id.double_week).setBackgroundColor(0xffffffff);
                    contentView.findViewById(R.id.double_week).setTag(false);

                    if (((Boolean) contentView.findViewById(R.id.all_week).getTag())) {
                        ((Button) contentView.findViewById(R.id.all_week)).setTextColor(0xFF565656);
                        contentView.findViewById(R.id.all_week).setBackgroundColor(0xffffffff);
                        contentView.findViewById(R.id.all_week).setTag(false);
                        clearWeeks();
                    } else {
                        ((Button) contentView.findViewById(R.id.all_week)).setTextColor(0xffffffff);
                        contentView.findViewById(R.id.all_week).setBackgroundColor(0xff6c9bff);
                        contentView.findViewById(R.id.all_week).setTag(true);
                        selectAllWeeks();
                    }
                }
            });

            contentView.findViewById(R.id.negetive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (weeksPopupWindow != null) {
                        weeksPopupWindow.dismiss();
                    }
                }
            });
            contentView.findViewById(R.id.positive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (weeksPopupWindow != null) {
                        settlementWeeks();
                        weeksPopupWindow.dismiss();
                    }
                }
            });
            for (int i = 0; i < 4; i++) {
                LinearLayout columnsLinearLayout = new LinearLayout(getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                columnsLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                columnsLinearLayout.setLayoutParams(layoutParams);
                for (int j = 0; j < 5; j++) {
                    TextView textView = new TextView(getContext());
                    LinearLayout.LayoutParams tlayoutParam = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setTag(false);
                    weeksItems.add(textView);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ((Boolean) textView.getTag()) {
                                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.FF565656));
                                textView.setTag(false);
                                textView.setBackgroundColor(0xffffffff);
                            } else {
                                textView.setTextColor(0xffffffff);
                                textView.setTag(true);
                                textView.setBackgroundColor(0xff6c9bff);
                            }

                        }
                    });
                    tlayoutParam.weight = 1;
                    tlayoutParam.setMargins(1, 1, 1, 1);
                    textView.setLayoutParams(tlayoutParam);
                    textView.setText(5 * i + j + 1 + "");
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.FF565656));
                    textView.setTextSize(20);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(0xffffffff);
                    columnsLinearLayout.addView(textView);
                }
                weeksItemContainerLinearLayout.addView(columnsLinearLayout);
            }

            contentView.setLayoutParams(fllp);
            fullFrameLayout.addView(contentView);
            weeksPopupWindow = new PopupWindow(fullFrameLayout, topBarRl.getWidth(), rootLinearLayou.getHeight());
        }
        weeksPopupWindow.showAsDropDown(rootLinearLayou, 0, -rootLinearLayou.getHeight());
    }

    void showSelectTimesWindow() {
        FrameLayout fullFrameLayout = new FrameLayout(this);
        fullFrameLayout.setBackgroundColor(0x33000000);
        fullFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weeksPopupWindow != null) {
                    weeksPopupWindow.dismiss();
                }
            }
        });
        FrameLayout.LayoutParams fllp = new FrameLayout.LayoutParams((int) (topBarRl.getWidth() * 0.8f), 400);
        fllp.gravity = Gravity.CENTER_HORIZONTAL;
        fllp.topMargin = rootLinearLayou.getHeight() / 8;
        if (timesPopuWindow == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.v_select_times_dialog, null);
            view.setLayoutParams(fllp);
            fullFrameLayout.addView(view);
            timesPopuWindow = new PopupWindow(fullFrameLayout, rootLinearLayou.getWidth(), rootLinearLayou.getHeight());
        }
        timesPopuWindow.showAsDropDown(rootLinearLayou, 0, -rootLinearLayou.getHeight());

    }

    void clearWeeks() {
        for (int i = 0; i < weeksItems.size(); i++) {
            weeksItems.get(i).setTextColor(ContextCompat.getColor(getContext(), R.color.FF565656));
            weeksItems.get(i).setTag(false);
            weeksItems.get(i).setBackgroundColor(0xffffffff);
            ;
        }
    }

    void selectAllWeeks() {
        for (int i = 0; i < weeksItems.size(); i++) {
            weeksItems.get(i).setTextColor(0xffffffff);
            weeksItems.get(i).setTag(true);
            weeksItems.get(i).setBackgroundColor(0xff6c9bff);
        }
    }

    void selectDoubleWeeks() {
        for (int i = 0; i < weeksItems.size(); i++) {
            if (i % 2 == 0) {
                weeksItems.get(i).setTextColor(0xffffffff);
                weeksItems.get(i).setTag(true);
                weeksItems.get(i).setBackgroundColor(0xff6c9bff);
            } else {
                weeksItems.get(i).setTextColor(ContextCompat.getColor(getContext(), R.color.FF565656));
                weeksItems.get(i).setTag(false);
                weeksItems.get(i).setBackgroundColor(0xffffffff);
            }
        }
    }

    void selectSingleWeeks() {
        for (int i = 0; i < weeksItems.size(); i++) {
            if (i % 2 != 0) {
                weeksItems.get(i).setTextColor(0xffffffff);
                weeksItems.get(i).setTag(true);
                weeksItems.get(i).setBackgroundColor(0xff6c9bff);
            } else {
                weeksItems.get(i).setTextColor(ContextCompat.getColor(getContext(), R.color.FF565656));
                weeksItems.get(i).setTag(false);
                weeksItems.get(i).setBackgroundColor(0xffffffff);
            }
        }
    }

    void settlementWeeks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < weeksItems.size(); i++) {
            if ((Boolean) weeksItems.get(i).getTag()) {
                stringBuilder.append(weeksItems.get(i).getText()).append(",");
            }
        }
        if (TextUtils.isEmpty(stringBuilder.toString())) {
            lessonWeeksTv.setText("");
        } else
            lessonWeeksTv.setText(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
    }

    void selectTimes() {
        showSelectTimesWindow();

    }
}
