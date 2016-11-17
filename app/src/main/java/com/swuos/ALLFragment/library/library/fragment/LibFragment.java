package com.swuos.ALLFragment.library.library.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.swuos.ALLFragment.library.library.adapter.FooterAdapter;
import com.swuos.ALLFragment.library.library.adapter.LibRecyclerAdapter;
import com.swuos.ALLFragment.library.library.model.BookItem;
import com.swuos.ALLFragment.library.library.presenter.ILibPresenter;
import com.swuos.ALLFragment.library.library.presenter.Imp.LibPresenterImp;
import com.swuos.ALLFragment.library.library.utils.NetWorkUtils;
import com.swuos.ALLFragment.library.library.utils.Parser;
import com.swuos.ALLFragment.library.library.view.ILibView;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by : youngkaaa on 2016/10/27.
 * Contact me : 645326280@qq.com
 */

public class LibFragment extends BaseFragment implements ILibView, View.OnTouchListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "LibFragment";

    @BindView(R.id.recyclerViewLib)
    RecyclerView mRecyclerView;


    @BindView(R.id.progressBarLib)
    ProgressBar mProgressBar;

    @BindView(R.id.fabMenuLib)
    FloatingActionMenu mFabMenu;

    @BindView(R.id.fabSearch)
    FloatingActionButton mFabSearch;

    @BindView(R.id.imageViewLibError)
    ImageView mImageViewError;
    @BindView(R.id.swipeRefreshLib)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.relativeLayoutError)
    RelativeLayout mRelativeLayoutError;

    @BindView(R.id.relativeLayoutLib)
    RelativeLayout mRelativeLayoutMain;

    private ILibPresenter mLibPresenter;
    private List<BookItem> mBookItems;
    private LibRecyclerAdapter mRecyclerAdapter;
    private FooterAdapter mFooterAdapter;
    private String userName;
    private String passWord;
    private int lastY;
    private SharedPreferences sharedPreferences;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lib;
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this, getRootView());
    }

    @Override
    public void initData() {
        mLibPresenter = new LibPresenterImp(getContext(), this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "nothing");
        passWord = sharedPreferences.getString("password", "nothing");

        SALog.d(TAG,"userName==>"+userName);
        SALog.d(TAG,"passwd==>"+passWord);
        mLibPresenter.getBorrowListByLogin(userName, passWord);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @OnClick(R.id.fabSearch)
    void fabSearchEvent() {
        Toast.makeText(getContext(), "fabSearch", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), com.swuos.ALLFragment.library.libsearchs.search.SearchActity.class));
    }

    @OnClick(R.id.imageViewLibError)
    void onImageErrorEvent() {
        if (NetWorkUtils.isNetConnected(getContext())) {
            Toast.makeText(getContext(), "network connected ", Toast.LENGTH_SHORT).show();
            mRelativeLayoutError.setVisibility(View.INVISIBLE);
            mRelativeLayoutMain.setVisibility(View.VISIBLE);

            mLibPresenter.getBorrowListByLogin(userName, passWord);
        } else {
            Toast.makeText(getContext(), "没网别乱点 ~~(╯﹏╰) ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void checkNetWork() {
        if (!NetWorkUtils.isNetConnected(getContext())) {
            Toast.makeText(getContext(), "没网哦！", Toast.LENGTH_SHORT).show();
            hideProgressDialog();
            mRelativeLayoutError.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateData(List<BookItem> bookItems) {
        mBookItems = bookItems;
        List<String> keys = Parser.makeAdapterKeys(mBookItems);
        Map<String, List<BookItem>> map = Parser.makeAdapterMap(mBookItems);
        mRecyclerAdapter = new LibRecyclerAdapter(getContext(), keys, map);
        mFooterAdapter = new FooterAdapter(getContext(), mRecyclerAdapter);
        mFooterAdapter.setFooterResId(R.layout.list_footer_no_more);
        mRecyclerView.setAdapter(mFooterAdapter);
        mRecyclerView.setOnTouchListener(this);
    }

    @Override
    public void hindFab() {
        if (!mFabMenu.isMenuHidden()) {
            mFabMenu.hideMenu(false);
        }
    }

    @Override
    public void showFab() {
        if (mFabMenu.isMenuHidden()) {
            mFabMenu.showMenu(false);
        }
    }

    @Override
    public void showRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshLayout() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void showProgressDialog() {
        if (mProgressBar != null && mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressBar != null && mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showErrorView() {
        mRelativeLayoutError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), "LibFragment:" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - lastY;
                if (deltaY >= 0) {  //down
                    mFabMenu.showMenu(true);
                } else {  //up
                    mFabMenu.hideMenu(true);
                }
                break;
        }
        lastY = y;
        return false;
    }

    @Override
    public void onRefresh() {
        mLibPresenter.getBorrowList();
        showRefreshLayout();
    }
}
