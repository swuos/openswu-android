package com.swuos.ALLFragment.library.lib.model;

/**
 * Created by : youngkaaa on 2016/9/4.
 * Contact me : 645326280@qq.com
 */
public class BookBean1 {
    private String bookName;  //书名
    private String loginId;   //登录号
    private String barCode;   //条形码
    private String opTime;    //操作时间
    private String opKind;   //操作种类

    public BookBean1(){}


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

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getOpKind() {
        return opKind;
    }

    public void setOpKind(String opKind) {
        this.opKind = opKind;
    }
}
