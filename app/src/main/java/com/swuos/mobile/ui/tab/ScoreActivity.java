package com.swuos.mobile.ui.tab;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gallops.mobile.jmvclibrary.app.BaseActivity;
import com.gallops.mobile.jmvclibrary.http.ErrorCode;
import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;
import com.swuos.mobile.app.App;
import com.swuos.mobile.entity.ScoreItem;
import com.swuos.mobile.models.http.requester.GetScoreRequester;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.adapter.ScoreRecycleviewAdapter;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class ScoreActivity extends BaseActivity {
    @FindViewById(R.id.root)
    LinearLayout root;
    @FindViewById(R.id.back_im)
    Button backButton;
    @FindViewById(R.id.ranksContainer)
    LinearLayout ranksContainer;
    @FindViewById(R.id.academic)
    TextView academicTv;
    @FindViewById(R.id.term)
    TextView termTv;
    @FindViewById(R.id.filter)
    TextView filterTv;
    @FindViewById(R.id.score_container_rv)
    RecyclerView scoreContainerRv;
    @FindViewById(R.id.score_container)
    LinearLayout scoreContainer;
    ScoreRecycleviewAdapter scoreRecycleviewAdapter;
    String academic;
    String term;
    UserModel userModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.a_score;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        userModel = App.getInstance().getModel(UserModel.class);
        scoreRecycleviewAdapter = new ScoreRecycleviewAdapter();
        scoreContainerRv.setLayoutManager(new LinearLayoutManager(getContext()));
        scoreContainerRv.setAdapter(scoreRecycleviewAdapter);
        setAcademic();
        academicTv.setText(academics[0]);
        academic = academicValue[0];
        term = "1";
    }

    private void getScore(String academic, String term) {
        GetScoreRequester getScoreRequester = new GetScoreRequester(userModel.getSwuId(), academic, term, new OnResultListener<ArrayList<ScoreItem>>() {
            @Override
            public void onResult(int code, ArrayList<ScoreItem> allScoreItem, String msg) {
                if (code == ErrorCode.RESULT_DATA_OK) {
                    getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            scoreRecycleviewAdapter.addData(allScoreItem);

                        }
                    });
                } else {
                    showToast(msg);
                }

            }


        });
        getScoreRequester.execute();
    }

    @OnClick({R.id.back_im, R.id.ranksContainer, R.id.academic, R.id.term, R.id.filter})

    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_im:
                finish();
                break;
            case R.id.ranksContainer:
                break;
            case R.id.academic:
                dismissAllPopuWindow();
                showAcademicPopuwindow();
                break;
            case R.id.term:
                dismissAllPopuWindow();

                showTermPopuwindow();
                break;
            case R.id.filter:
                dismissAllPopuWindow();
                showFilterPopuwindow();
                break;
            default:
                break;
        }
    }

    private void showFilterPopuwindow() {
        if (filterPopuwindow == null) {
            String filterContainer = "";
            FrameLayout fullFrameLayout = new FrameLayout(getContext());
            FrameLayout.LayoutParams frameLayoutLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            fullFrameLayout.setBackgroundColor(0x33000000);
            fullFrameLayout.setLayoutParams(frameLayoutLp);
            fullFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllPopuWindow();
                }
            });

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) (ranksContainer.getWidth() * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.RIGHT;
            View view = LayoutInflater.from(getContext()).inflate(R.layout.v_filter_score, null);
            view.setLayoutParams(layoutParams);
            view.findViewById(R.id.major_must).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()) {
                        ((Button) v).setSelected(false);
                        ((Button) v).setTextColor(0xff565656);
                    } else {
                        ((Button) v).setSelected(true);
                        ((Button) v).setTextColor(0xffffffff);

                    }

                }
            });
            view.findViewById(R.id.major_option).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()) {
                        ((Button) v).setSelected(false);
                        ((Button) v).setTextColor(0xff565656);
                    } else {
                        ((Button) v).setSelected(true);
                        ((Button) v).setTextColor(0xffffffff);

                    }


                }
            });
            view.findViewById(R.id.normal_must).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()) {
                        ((Button) v).setSelected(false);
                        ((Button) v).setTextColor(0xff565656);
                    } else {
                        ((Button) v).setSelected(true);
                        ((Button) v).setTextColor(0xffffffff);

                    }


                }
            });
            view.findViewById(R.id.normal_option).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()) {
                        ((Button) v).setSelected(false);
                        ((Button) v).setTextColor(0xff565656);
                    } else {
                        ((Button) v).setSelected(true);
                        ((Button) v).setTextColor(0xffffffff);

                    }

                }
            });
            view.findViewById(R.id.subject_must).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()) {
                        ((Button) v).setSelected(false);
                        ((Button) v).setTextColor(0xff565656);
                    } else {
                        ((Button) v).setSelected(true);
                        ((Button) v).setTextColor(0xffffffff);

                    }

                }
            });
            fullFrameLayout.addView(view);
            filterPopuwindow = new PopupWindow(fullFrameLayout, scoreContainer.getWidth(), scoreContainer.getHeight());
            filterPopuwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            filterPopuwindow.setFocusable(true);
        }
        filterPopuwindow.showAsDropDown(ranksContainer, 0, 0, Gravity.BOTTOM);

    }

    void dismissAllPopuWindow() {
        if (academicPopuwindow != null)
            academicPopuwindow.dismiss();
        if (filterPopuwindow != null)
            filterPopuwindow.dismiss();
        if (termPopuwindow != null) {
            termPopuwindow.dismiss();
        }
    }

    PopupWindow academicPopuwindow;
    PopupWindow filterPopuwindow;
    PopupWindow termPopuwindow;
    String academics[] = {"2018-2019", "2017-2018", "2016-2017", "2015-2016"};
    String academicValue[] = {
            "2018",
            "2017",
            "2016",
            "2015"

    };

    private void showAcademicPopuwindow() {

        if (academicPopuwindow == null) {
            FrameLayout fullFrameLayout = new FrameLayout(getContext());
            FrameLayout.LayoutParams frameLayoutLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            fullFrameLayout.setBackgroundColor(0x33000000);
            fullFrameLayout.setLayoutParams(frameLayoutLp);
            fullFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllPopuWindow();
                }
            });
            LinearLayout contentLinearLayout = new LinearLayout(getContext());

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(academicTv.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.LEFT;
            contentLinearLayout.setOrientation(VERTICAL);
            contentLinearLayout.setElevation(10);
            contentLinearLayout.setBackgroundColor(0xffffffff);
            contentLinearLayout.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            for (int i = 0; i < 4; i++) {
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(params);
                textView.setPadding(5, 5, 5, 5);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setText(academics[i]);
                textView.setTextColor(0xff565656);
                int finalI = i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int j = 0; j < contentLinearLayout.getChildCount(); j++) {
                            ((TextView) contentLinearLayout.getChildAt(j)).setTextColor(0xff565656);

                        }
                        academicTv.setText(academics[finalI]);
                        textView.setTextColor(0xff4f87ff);
                        academic = academicValue[finalI];
                        dismissAllPopuWindow();
                        getScore(academic, term);
                    }
                });
                contentLinearLayout.addView(textView);
            }
            contentLinearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.weeks_select_dialog_bg));

            fullFrameLayout.addView(contentLinearLayout);
            academicPopuwindow = new PopupWindow(fullFrameLayout, scoreContainer.getWidth(), scoreContainer.getHeight());

        }
        academicPopuwindow.showAsDropDown(ranksContainer, 0, 0, Gravity.BOTTOM);

    }

    private void showTermPopuwindow() {
        if (termPopuwindow == null) {
            FrameLayout fullFrameLayout = new FrameLayout(getContext());
            FrameLayout.LayoutParams frameLayoutLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            fullFrameLayout.setBackgroundColor(0x33000000);
            fullFrameLayout.setLayoutParams(frameLayoutLp);
            fullFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllPopuWindow();
                }
            });
            LinearLayout contentLinearLayout = new LinearLayout(getContext());

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(termTv.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = academicTv.getWidth();

            contentLinearLayout.setOrientation(VERTICAL);
            contentLinearLayout.setElevation(10);
            contentLinearLayout.setBackgroundColor(0xffffffff);
            contentLinearLayout.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            String academics[] = {"第一学期", "第二学期"};
            for (int i = 0; i < 2; i++) {
                TextView textView = new TextView(getContext());

                textView.setLayoutParams(params);
                textView.setPadding(5, 5, 5, 5);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextColor(0xff565656);
                int finalI = i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        term = String.valueOf(finalI + 1);
                        for (int j = 0; j < contentLinearLayout.getChildCount(); j++) {
                            ((TextView) contentLinearLayout.getChildAt(j)).setTextColor(0xff565656);

                        }
                        termTv.setText("第" + (finalI + 1) + "学期");
                        textView.setTextColor(0xff4f87ff);
                        dismissAllPopuWindow();
                        getScore(academic, term);
                    }
                });
                textView.setText(academics[i]);
                contentLinearLayout.addView(textView);
            }
            contentLinearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.weeks_select_dialog_bg));
            fullFrameLayout.addView(contentLinearLayout);
            termPopuwindow = new PopupWindow(fullFrameLayout, root.getWidth(), scoreContainer.getHeight());

        }
        termPopuwindow.showAsDropDown(ranksContainer, academicTv.getWidth(), 0);
    }

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
}
