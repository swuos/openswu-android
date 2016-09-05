package com.swuos.ALLFragment.library.lib.presenter;


import android.os.Bundle;

import com.swuos.ALLFragment.library.lib.model.BookBean2;
import com.swuos.ALLFragment.library.lib.utils.LibTools;
import com.swuos.ALLFragment.library.lib.views.ILibView;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/27.
 * Email:  645326280@qq.com
 */
public class LibPresenterImp implements ILibPresenter {
    private ILibView iLibView;
    public static final int FAILED = 0;
    public static final int SUCCEED = 1;

    private int flag;

    public LibPresenterImp(ILibView iLibView) {
        this.iLibView = iLibView;

    }

    public int getUserInfos(String id, String pd) {
        Bundle bundle = LibTools.libLogin(id, pd);
        flag = bundle.getInt(Constant.LIB_LOGIN_RESULT_FLAG);

        if (flag == Constant.LIB_LOGIN_SUCCESS) {

        } else if (flag == Constant.LIB_LOGIN_FAILED) {

        } else if (flag == Constant.LIB_LOGIN_INVALID_ID) {

        } else if (flag == Constant.LIB_LOGIN_INVALID_PD) {

        }
        return flag;
    }

    @Override
    public void setRecyclerViewVisible(int visible) {
        iLibView.onSetRecyclerViewVisible(visible);
    }

    @Override
    public void setSwipeRefreshVisible(int visible) {
        iLibView.onSetSwipeRefreshVisible(visible);
    }

    @Override
    public void updateBookItems() {
        if (flag == Constant.LIB_LOGIN_SUCCESS) {
            SALog.d("kklog", "updateBookItems flag==LibTools.LIB_LOGIN_SUCCESS");
//            if(html.isEmpty() || html==null || html.equals("")){
//                SALog.d("kklog", "updateBookItems html is empty");
//            }else{
//                SALog.d("kklog", "updateBookItems html not empty");
//                SALog.d("kklog", "updateBookItems html ===>"+html);
//            }

//            List<BookBean2> bookHistory = ParserInfo.parserBorrowHtml(html);
            List<BookBean2> bookHistory = LibTools.getBorrowInfo();
            if (bookHistory.isEmpty()) {
                SALog.d("kklog", "updateBookItems flag==LibTools.LIB_LOGIN_SUCCESS isEmpty");
            }else{
                SALog.d("kklog", "updateBookItems flag==LibTools.LIB_LOGIN_SUCCESS not empty");
            }
            if (bookHistory==null) {
                SALog.d("kklog", "updateBookItems flag==LibTools.LIB_LOGIN_SUCCESS null");
            }else{
                SALog.d("kklog", "updateBookItems flag==LibTools.LIB_LOGIN_SUCCESS not null");
            }
            if (bookHistory == null || bookHistory.isEmpty()) {
                SALog.d("kklog", "updateBookItems flag==LibTools.LIB_LOGIN_SUCCESS if");
                iLibView.onUpdateBookItems(FAILED, null);
            } else {
                SALog.d("kklog", "updateBookItems flag==LibTools.LIB_LOGIN_SUCCESS else");
                iLibView.onUpdateBookItems(SUCCEED, bookHistory);
            }
        } else {
            SALog.d("kklog", "updateBookItems else");
            iLibView.onUpdateBookItems(FAILED, null);
        }
    }

    @Override
    public void setProgressDialogVisible(int visible) {
        iLibView.onSetProgressDialogVisible(visible);
    }

    @Override
    public void setTipDialogVisible(int visible) {
        iLibView.onSetTipDialogVisible(visible);
    }

    @Override
    public void setErrorLayoutVisible(int visible) {
        iLibView.onSetErrorLayoutVisible(visible);
    }
}
