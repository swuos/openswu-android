package com.swuos.ALLFragment.library.lib.model;

import java.util.List;

/**
 * Created by : youngkaaa on 2016/9/4.
 * Contact me : 645326280@qq.com
 */
public class BookBean2 {
    private String bookName;  //书名
    private String loginId;   //登录号
    private String barCode;   //条形码
    private List<String> opTimes;    //操作时间集合
    private List<String> opKinds;   //操作种类集合

    public BookBean2(){}


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public List<String> getOpKinds() {
        return opKinds;
    }

    public void setOpKinds(List<String> opKinds) {
        this.opKinds = opKinds;
    }

    public void setOpTimes(List<String> opTimes) {
        this.opTimes = opTimes;
    }

    public List<String> getOpTimes() {
        return opTimes;
    }
}
