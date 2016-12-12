package com.swuos.ALLFragment.swujw.grade;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.swuos.ALLFragment.BaseFragment;
import com.swuos.ALLFragment.swujw.grade.adapter.GradeDetaiAdapter;
import com.swuos.ALLFragment.swujw.grade.adapter.GradesRecycleviewAdapter;
import com.swuos.ALLFragment.swujw.grade.adapter.RecycleviewItemDecoration;
import com.swuos.ALLFragment.swujw.grade.model.GradeItem;
import com.swuos.ALLFragment.swujw.grade.persenter.GradePresenterCompl;
import com.swuos.ALLFragment.swujw.grade.persenter.IGradePersenter;
import com.swuos.ALLFragment.swujw.grade.view.IGradeview;
import com.swuos.swuassistant.BaseApplication;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class GradesNewFragment extends BaseFragment implements IGradeview, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener, GradesRecycleviewAdapter.OnRecyclerItemClickedListener, View.OnTouchListener, View.OnClickListener {

    View gradesLayout;
    IGradePersenter iGradePersenter;
    RecyclerView recyclerView;
    GradesRecycleviewAdapter gradesRecycleviewAdapter;
    ImageButton filterButton;
    HorizontalScrollView horizontalScrollView;
    PopupWindow popupWindow;
    View filterView;
    Button normalExamButton;
    Button makeupExamButton;
    Button professionalRequiredCourseButton;
    Button professionalElectiveCourseButton;
    Button generalRequiredCourseButton;
    Button generalElectiveCourseButton;
    Button subjectRequiredCourseButton;
    Button cancleButton;
    Button enterButton;
    Button judegmentButton;

    EditText gradeMinEditText;
    EditText gradeMaxEditText;
    EditText gradePointMinEditText;
    EditText gradePointMaxEditText;
    boolean isCheckedNormalExam = false;
    boolean isCheckedMakeupExam = false;

    boolean isCheckedProfessionalRequiredCourse = false;
    boolean isCheckedProfessionalElectiveCourse = false;
    boolean isCheckedGeneralRequiredCourse = false;
    boolean isCheckedGeneralElectiveCourse = false;
    boolean isCheckedSubjectRequiredCourse = false;
    float gradeMin = 0;
    float gradeMax = 100;
    float gradePointMin = 0;
    float gradePointMax = 5;
    /*选择学年的下拉列表*/
    private Spinner spinnerXnm;
    /*选择学期的下拉列表*/
    private Spinner spinnerXqm;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        gradesLayout = inflater.inflate(R.layout.grades_new_fragment_layout, container, false);
        iGradePersenter = new GradePresenterCompl(getContext(), this);
        iGradePersenter.initData();
        bindView();
        initView();
        return gradesLayout;
    }

    void bindView() {
        spinnerXnm = (Spinner) gradesLayout.findViewById(R.id.grade_new_fragment_xnm);
        spinnerXqm = (Spinner) gradesLayout.findViewById(R.id.grade_new_fragment_xqm);
        swipeRefreshLayout = (SwipeRefreshLayout) gradesLayout.findViewById(R.id.grade_new_fragment_swiperefresh);
        recyclerView = (RecyclerView) gradesLayout.findViewById(R.id.grade_new_fragment_recycle);
        horizontalScrollView = (HorizontalScrollView) gradesLayout.findViewById(R.id.grade_new_fragment_horizontalScroll);
        filterButton = (ImageButton) gradesLayout.findViewById(R.id.grade_new_fragment_filter_button);
        filterView = LayoutInflater.from(getActivity()).inflate(R.layout.grades_filter, null, false);

        normalExamButton = (Button) filterView.findViewById(R.id.grade_filter_normal_exam);
        makeupExamButton = (Button) filterView.findViewById(R.id.grade_filter_makeup_exam);

        professionalRequiredCourseButton = (Button) filterView.findViewById(R.id.grade_filter_professional_required_course);
        professionalElectiveCourseButton = (Button) filterView.findViewById(R.id.grade_filter_professional_elective_course);
        generalRequiredCourseButton = (Button) filterView.findViewById(R.id.grade_filter_general_required_course);
        generalElectiveCourseButton = (Button) filterView.findViewById(R.id.grade_filter_general_elective_course);
        subjectRequiredCourseButton = (Button) filterView.findViewById(R.id.grade_filter_subject_required_course);
        judegmentButton = (Button) filterView.findViewById(R.id.grade_filter_judegment);
        cancleButton = (Button) filterView.findViewById(R.id.grade_filter_cancle);
        enterButton = (Button) filterView.findViewById(R.id.grade_filter_enter);
        gradeMinEditText = (EditText) filterView.findViewById(R.id.grade_filter_grades_min);
        gradeMaxEditText = (EditText) filterView.findViewById(R.id.grade_filter_grades_max);
        gradePointMinEditText = (EditText) filterView.findViewById(R.id.grade_filter_grades_point_min);
        gradePointMaxEditText = (EditText) filterView.findViewById(R.id.grade_filter_grades_point_max);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在教评...");

    }

    private void initView() {

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        ArrayAdapter<CharSequence> arrayAdapterxnm = ArrayAdapter.createFromResource(getActivity(), R.array.xnm, R.layout.grades_spinner_layout);
        arrayAdapterxnm.setDropDownViewResource(R.layout.grades_spinnerdown_layout);
        spinnerXnm.setAdapter(arrayAdapterxnm);

        ArrayAdapter<CharSequence> arrayAdapterxqm = ArrayAdapter.createFromResource(getActivity(), R.array.xqm, R.layout.grades_spinner_layout);
        arrayAdapterxqm.setDropDownViewResource(R.layout.grades_spinnerdown_layout);
        spinnerXqm.setAdapter(arrayAdapterxqm);

         /*设置下拉列表的选择监听*/
        spinnerXnm.setOnItemSelectedListener(this);
        spinnerXqm.setOnItemSelectedListener(this);
        /*学年下拉列表的默认值*/
        spinnerXnm.setSelection(iGradePersenter.getLastxnmPosition(), true);
        /*学期下拉列表的默认值*/
        spinnerXqm.setSelection(iGradePersenter.getLastxqmPosition(), true);

        gradesRecycleviewAdapter = new GradesRecycleviewAdapter();
        gradesRecycleviewAdapter.setOnRecyclerItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext()));
        recyclerView.setAdapter(gradesRecycleviewAdapter);
        recyclerView.addItemDecoration(new RecycleviewItemDecoration(BaseApplication.getContext(), 0));
        horizontalScrollView.setOnTouchListener(this);

        filterButton.setOnClickListener(this);
        popupWindow = new PopupWindow(filterView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xb37a7b7a));
        normalExamButton.setOnClickListener(this);
        makeupExamButton.setOnClickListener(this);
        professionalRequiredCourseButton.setOnClickListener(this);
        professionalElectiveCourseButton.setOnClickListener(this);
        generalRequiredCourseButton.setOnClickListener(this);
        generalElectiveCourseButton.setOnClickListener(this);
        subjectRequiredCourseButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);
        enterButton.setOnClickListener(this);
        judegmentButton.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*选择了xnm的下拉列表*/
        if (parent == spinnerXnm) {
            iGradePersenter.setXnm(Constant.ALL_XNM[position]);
            iGradePersenter.setXnmPosition(position);
        } else if (parent == spinnerXqm)/*选择了xqm的下拉列表*/ {
            iGradePersenter.setXqm(Constant.ALL_XQM[position]);
            iGradePersenter.setXqmPosition(position);
        }
        if (iGradePersenter.getUsername() != null && !iGradePersenter.getUsername().equals("")) {
            if (iGradePersenter.getXqm() != null && iGradePersenter.getXnm() != null) {
                iGradePersenter.saveUserLastCLick(iGradePersenter.getXnmPosition(), iGradePersenter.getXqmPosition());
                iGradePersenter.getGrades(iGradePersenter.getUsername(), iGradePersenter.getPassword(), iGradePersenter.getXqm(), iGradePersenter.getXnm(), true);
            }
        } else
            Toast.makeText(getActivity(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void showDialog(Boolean isShow) {
        if (isShow) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            progressDialog.dismiss();

            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showResult(List<GradeItem> gradeItemList) {
        showDialog(false);
        gradesRecycleviewAdapter.addData(gradeItemList);
    }

    @Override
    public void showError(String error) {
        showDialog(false);
        //        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        Snackbar.make(gradesLayout, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showGradeDetial(GradeItem gradeItem) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.grade_detail_layout, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        ListView gradeDetailListview = (ListView) view.findViewById(R.id.grade_detail_list);
        GradeDetaiAdapter gradeDetaiAdapter = new GradeDetaiAdapter(gradesLayout.getContext(), R.layout.grade_detail_item, gradeItem.getDetial());
        gradeDetailListview.setAdapter(gradeDetaiAdapter);
        alertDialog.setTitle(gradeItem.getKcmc());
        alertDialog.setView(view);
        AlertDialog adl = alertDialog.create();
        adl.show();
    }

    @Override
    public void onRefresh() {
        if (iGradePersenter.getUsername() != null && !iGradePersenter.getUsername().equals("")) {
            iGradePersenter.saveUserLastCLick(iGradePersenter.getXnmPosition(), iGradePersenter.getXqmPosition());
            iGradePersenter.getGrades(iGradePersenter.getUsername(), iGradePersenter.getPassword(), iGradePersenter.getXqm(), iGradePersenter.getXnm(), true);
        } else {
            Toast.makeText(getActivity(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
            showDialog(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gradesLayout = null;
    }

    @Override
    public void onItemClick(View view, int position, GradeItem dataItem) {
        if (position < gradesRecycleviewAdapter.getItemCount() - 2 && position != 0) {
            iGradePersenter.getGradeDetial(iGradePersenter.getUsername(), iGradePersenter.getPassword(), position);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (v.getScrollY() != 0) {
                    swipeRefreshLayout.setEnabled(false);
                } else {
                    swipeRefreshLayout.setEnabled(true);

                }

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.grade_new_fragment_filter_button:
                popupWindow.showAsDropDown(filterButton);
                break;
            case R.id.grade_filter_normal_exam:
                isCheckedNormalExam = (isCheckedNormalExam == false) ? true : false;
                if (isCheckedNormalExam) {
                    normalExamButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

                    normalExamButton.setBackgroundResource(R.drawable.filter_select_button_bg);
                } else {
                    normalExamButton.setBackgroundResource(R.drawable.filter_button_bg);
                    normalExamButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.textcolorblack));

                }
                break;
            case R.id.grade_filter_makeup_exam:
                isCheckedMakeupExam = (!isCheckedMakeupExam);
                if (isCheckedMakeupExam) {
                    makeupExamButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

                    makeupExamButton.setBackgroundResource(R.drawable.filter_select_button_bg);
                } else {
                    makeupExamButton.setBackgroundResource(R.drawable.filter_button_bg);
                    makeupExamButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.textcolorblack));

                }
                break;
            case R.id.grade_filter_professional_required_course:
                isCheckedProfessionalRequiredCourse = (!isCheckedProfessionalRequiredCourse);
                if (isCheckedProfessionalRequiredCourse) {
                    professionalRequiredCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

                    professionalRequiredCourseButton.setBackgroundResource(R.drawable.filter_select_button_bg);
                } else {
                    professionalRequiredCourseButton.setBackgroundResource(R.drawable.filter_button_bg);
                    professionalRequiredCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.textcolorblack));

                }
                break;
            case R.id.grade_filter_professional_elective_course:
                isCheckedProfessionalElectiveCourse = (!isCheckedProfessionalElectiveCourse);
                if (isCheckedProfessionalElectiveCourse) {
                    professionalElectiveCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

                    professionalElectiveCourseButton.setBackgroundResource(R.drawable.filter_select_button_bg);
                } else {
                    professionalElectiveCourseButton.setBackgroundResource(R.drawable.filter_button_bg);
                    professionalElectiveCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.textcolorblack));

                }
                break;
            case R.id.grade_filter_general_required_course:
                isCheckedGeneralRequiredCourse = (!isCheckedGeneralRequiredCourse);
                if (isCheckedGeneralRequiredCourse) {
                    generalRequiredCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

                    generalRequiredCourseButton.setBackgroundResource(R.drawable.filter_select_button_bg);
                } else {
                    generalRequiredCourseButton.setBackgroundResource(R.drawable.filter_button_bg);
                    generalRequiredCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.textcolorblack));

                }
                break;
            case R.id.grade_filter_general_elective_course:
                isCheckedGeneralElectiveCourse = (!isCheckedGeneralElectiveCourse);
                if (isCheckedGeneralElectiveCourse) {
                    generalElectiveCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

                    generalElectiveCourseButton.setBackgroundResource(R.drawable.filter_select_button_bg);
                } else {
                    generalElectiveCourseButton.setBackgroundResource(R.drawable.filter_button_bg);
                    generalElectiveCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.textcolorblack));

                }
                break;
            case R.id.grade_filter_subject_required_course:
                isCheckedSubjectRequiredCourse = (!isCheckedSubjectRequiredCourse);
                if (isCheckedSubjectRequiredCourse) {
                    subjectRequiredCourseButton.setBackgroundResource(R.drawable.filter_select_button_bg);
                    subjectRequiredCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                } else {
                    subjectRequiredCourseButton.setBackgroundResource(R.drawable.filter_button_bg);
                    subjectRequiredCourseButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.textcolorblack));

                }
                break;
            case R.id.grade_filter_cancle:
                popupWindow.dismiss();
                break;
            case R.id.grade_filter_enter:
                popupWindow.dismiss();
                String sgradeMin = gradeMinEditText.getText().toString();
                String sgradeMax = gradeMaxEditText.getText().toString();
                String sgradePointMin = gradePointMinEditText.getText().toString();
                String sgradePointMax = gradePointMaxEditText.getText().toString();
                if (!TextUtils.isEmpty(sgradeMin))
                    gradeMin = Float.parseFloat(sgradeMin);
                else
                    gradeMin = 0;
                if (!TextUtils.isEmpty(sgradeMax))
                    gradeMax = Float.parseFloat(sgradeMax);
                else
                    gradeMax = 100;
                if (!TextUtils.isEmpty(sgradePointMin))
                    gradePointMin = Float.parseFloat(sgradePointMin);
                else
                    gradePointMin = 0;
                if (!TextUtils.isEmpty(sgradePointMax))
                    gradePointMax = Float.parseFloat(sgradePointMax);
                else
                    gradePointMax = 5;
                iGradePersenter.filterGrades(
                        isCheckedNormalExam,
                        isCheckedMakeupExam,
                        isCheckedProfessionalRequiredCourse,
                        isCheckedProfessionalElectiveCourse,
                        isCheckedGeneralRequiredCourse,
                        isCheckedGeneralElectiveCourse,
                        isCheckedSubjectRequiredCourse,
                        gradeMin, gradeMax, gradePointMin, gradePointMax
                );
                break;
            case R.id.grade_filter_judegment:
                popupWindow.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("一键教评").setMessage(R.string.judgement_message).setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        iGradePersenter.judgement(iGradePersenter.getUsername(), iGradePersenter.getPassword(), "1");
                    }
                }).setNegativeButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        iGradePersenter.judgement(iGradePersenter.getUsername(), iGradePersenter.getPassword(), "0");
                    }
                }).setNeutralButton("我再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setCancelable(true).show();
            default:
                break;

        }
    }
}



