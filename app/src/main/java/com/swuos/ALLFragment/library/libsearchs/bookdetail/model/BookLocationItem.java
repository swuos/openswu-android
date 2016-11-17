package com.swuos.ALLFragment.library.libsearchs.bookdetail.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by : youngkaaa on 2016/11/5.
 * Contact me : 645326280@qq.com
 */

public class BookLocationItem {

    /**
     * 索书号 : B972/A244L
     * 单位名 : 图书馆
     * 年卷期 :
     * bookstatus : 不外借
     * 外借状态 : 0
     * 条形码 : B1095648
     * borrowstatus : 不外借
     * 虚拟库室 : 0
     * 登录号 : 1215649
     * 馆藏地址 : 049
     * 区分号 :
     * canExtracted : false
     * 架位号 :
     * 刊价 : 35
     * 备注 : 待合并到阅览室储备书库用
     * 部门名称 : 弘文馆临时储备书库
     */

    @SerializedName("索书号")
    private String suoShuNum;
    @SerializedName("单位名")
    private String apart;
    @SerializedName("年卷期")
    private String nianJuanQi;
    private String bookstatus;
    @SerializedName("外借状态")
    private String borrowStatus;
    @SerializedName("条形码")
    private String barCode;
    private String borrowstatus;
    @SerializedName("虚拟库室")
    private int virtualRoom;
    @SerializedName("登录号")
    private String loginNum;
    @SerializedName("馆藏地址")
    private String location;
    @SerializedName("区分号")
    private String dividerNum;
    private boolean canExtracted;
    @SerializedName("架位号")
    private String jiaWei;
    @SerializedName("刊价")
    private float price;
    @SerializedName("备注")
    private String tip;
    @SerializedName("部门名称")
    private String apartName;

    public String getSuoShuNum() {
        return suoShuNum;
    }

    public void setSuoShuNum(String suoShuNum) {
        this.suoShuNum = suoShuNum;
    }

    public String getApart() {
        return apart;
    }

    public void setApart(String apart) {
        this.apart = apart;
    }

    public String getNianJuanQi() {
        return nianJuanQi;
    }

    public void setNianJuanQi(String nianJuanQi) {
        this.nianJuanQi = nianJuanQi;
    }

    public String getBookstatus() {
        return bookstatus;
    }

    public void setBookstatus(String bookstatus) {
        this.bookstatus = bookstatus;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBorrowstatus() {
        return borrowstatus;
    }

    public void setBorrowstatus(String borrowstatus) {
        this.borrowstatus = borrowstatus;
    }

    public int getVirtualRoom() {
        return virtualRoom;
    }

    public void setVirtualRoom(int virtualRoom) {
        this.virtualRoom = virtualRoom;
    }

    public String getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(String loginNum) {
        this.loginNum = loginNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDividerNum() {
        return dividerNum;
    }

    public void setDividerNum(String dividerNum) {
        this.dividerNum = dividerNum;
    }

    public boolean isCanExtracted() {
        return canExtracted;
    }

    public void setCanExtracted(boolean canExtracted) {
        this.canExtracted = canExtracted;
    }

    public String getJiaWei() {
        return jiaWei;
    }

    public void setJiaWei(String jiaWei) {
        this.jiaWei = jiaWei;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getApartName() {
        return apartName;
    }

    public void setApartName(String apartName) {
        this.apartName = apartName;
    }
}
