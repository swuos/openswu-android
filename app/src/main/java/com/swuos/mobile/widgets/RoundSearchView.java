package com.swuos.mobile.widgets;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.swuos.mobile.R;
import com.swuos.mobile.ui.lib.LibSearchActivity;
import com.swuos.mobile.utils.DisplayUtils;

/**
 * Created by youngkaaa on 2018/5/18
 */
public class RoundSearchView implements View.OnClickListener, TextWatcher {
    private View mRootView;
    private ImageView mIvIcon;
    private TextView mTvHint;
    private EditText mEtInput;
    private TextWatcher mListener;

    public RoundSearchView(View root) {
        if (root == null) {
            return;
        }
        mRootView = root;
        initView();
    }

    private void initView() {
        mIvIcon = mRootView.findViewById(R.id.iv_lib_search_icon);
        mTvHint = mRootView.findViewById(R.id.tv_lib_search_hint);
        mEtInput = mRootView.findViewById(R.id.et_lib_search_input);

        mRootView.setOnClickListener(this);
        mEtInput.addTextChangedListener(this);
        // 默认不展示UI 直到调用update()方法来展示
        mRootView.setVisibility(View.GONE);
    }


    public void setTextChangeListener(TextWatcher l) {
        mListener = l;
    }

    /**
     * 左边没有搜索按钮 中间文字居左
     */
    public void setSearchMode(String hint) {
        mRootView.setVisibility(View.VISIBLE);
        mIvIcon.setVisibility(View.GONE);
        mTvHint.setVisibility(View.GONE);
        mEtInput.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(hint)) {
            mEtInput.setHint(R.string.lib_search_hint);
        } else {
            mEtInput.setHint(hint);
        }
    }

    /**
     * 左边有搜索按钮 中间文字居中
     */
    public void setHintMode(String hint) {
        mRootView.setVisibility(View.VISIBLE);
        mIvIcon.setVisibility(View.VISIBLE);
        mTvHint.setVisibility(View.VISIBLE);
        mTvHint.setGravity(Gravity.CENTER_HORIZONTAL);
        mEtInput.setVisibility(View.GONE);
        if (TextUtils.isEmpty(hint)) {
            mTvHint.setText(R.string.lib_search_hint);
        } else {
            mTvHint.setText(hint);
        }
    }

    public String getInput() {
        if (mEtInput.getVisibility() == View.VISIBLE) {
            return mEtInput.getText().toString();
        }
        return "";
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.linear_lib_search_root
                && mEtInput.getVisibility() == View.GONE) {
            Context context = mRootView.getContext();
            context.startActivity(new Intent(context, LibSearchActivity.class));
        }
    }

    /**
     * 搜索状态中 需要处理的事情
     */
    public void onSearch() {
        if (mEtInput != null) {
            DisplayUtils.hideSoftInput(mEtInput);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mListener != null) {
            mListener.afterTextChanged(s);
        }
    }
}
