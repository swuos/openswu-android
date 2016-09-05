package com.swuos.ALLFragment.library.lib.utils;

import android.content.Context;
import android.os.Bundle;


import com.swuos.ALLFragment.library.lib.model.BookBean2;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by : youngkaaa on 2016/9/3.
 * Contact me : 645326280@qq.com
 */
public class LibTools {
    private static String s;

    private static List<BookBean2> bookBeens;


    public static Bundle libLogin(String id, String pd) {
        int flag = Constant.LIB_LOGIN_FAILED;
        Bundle bundle = new Bundle();
        String s = ParserInfo.loginLibrary(id, pd);
        flag = checkLoginStatus(s);
        bundle.putString(Constant.LIB_LOGIN_RESULT_CONTENT, s);
        bundle.putInt(Constant.LIB_LOGIN_RESULT_FLAG, flag);
        return bundle;
    }


    private static int checkLoginStatus(String s) {
        if (s.contains("密码错误！请重新输入")) {
            return Constant.LIB_LOGIN_INVALID_PD;   //password is wrong!
        } else if (s.contains("数据库中不存在")) {
            return Constant.LIB_LOGIN_INVALID_ID;   //this id does not existed!
        } else if (s.contains("pageRedirect")) {
            return Constant.LIB_LOGIN_SUCCESS;  //login success
        } else {
            return Constant.LIB_LOGIN_FAILED;  //failed result
        }
    }


    private static String readLibTable(int flag) {
        Bundle bundle = null;
        if (flag == Constant.LIB_LOGIN_SUCCESS) {
//            bundle = new Bundle();
//            String s = ParserInfo.readTable();
            return ParserInfo.readTable();
        } else {
            return null;
        }
    }

    public static List<BookBean2> getBorrowInfo() {
        s = ParserInfo.getBorrowInfo();
        String totalPage = ParserInfo.getBorrowTotalPage(s);
        bookBeens = ParserInfo.parserBorrowHtml(s);
        for (int i = 2; i <= Integer.valueOf(totalPage); i++) {
            SALog.d("kklog", "getBorrowInfo i==>" + i);
            s = ParserInfo.getBorrowInfo(i);
            List<BookBean2> bookBean2s = ParserInfo.parserBorrowHtml(s);
            for (BookBean2 bean2 : bookBean2s) {
                bookBeens.add(bean2);
            }
        }
        return bookBeens;
    }

    public static List<String> getPersonKeys() {
        List<String> keys = new ArrayList<>();
        keys.add(ParserInfo.tranKeyToCH(Constant.LIB_NO));
        keys.add(ParserInfo.tranKeyToCH(Constant.LIB_CARD_NO));
        keys.add(ParserInfo.tranKeyToCH(Constant.LIB_READER_BAR));
        keys.add(ParserInfo.tranKeyToCH(Constant.LIB_READER_NAME));
        keys.add(ParserInfo.tranKeyToCH(Constant.LIB_READER_SEX));
        keys.add(ParserInfo.tranKeyToCH(Constant.LIB_READER_UNIT));
        keys.add(ParserInfo.tranKeyToCH(Constant.LIB_DEBT));
        keys.add(ParserInfo.tranKeyToCH(Constant.LIB_PAY));
        keys.add(ParserInfo.tranKeyToCH(Constant.LIB_BORROW_COUNT));

        return keys;
    }

    public static List<String> getPersonVals() {
        String s = ParserInfo.readTable();
        return ParserInfo.parserHomePageHtml(s);
    }
}
