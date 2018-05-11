package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 账户信息
 * Created by wangyu on 2018/1/20.
 */

public class AccountInfo implements Parcelable ,Cloneable{

    private String phoneNumber;//手机号
    private String password;//密码
    private String acToken;//账号token
    private String swuId;//校园网账号
    private String nickName;//昵称

    public String getSwuId() {
        return swuId;
    }

    public void setSwuId(String swuId) {
        this.swuId = swuId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAcToken() {
        return acToken;
    }

    public void setAcToken(String acToken) {
        this.acToken = acToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phoneNumber);
        dest.writeString(this.password);
        dest.writeString(this.acToken);
        dest.writeString(this.nickName);
        dest.writeString(this.swuId);
    }

    @Override
    public AccountInfo clone() {
        try {
            return (AccountInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AccountInfo() {
    }

    protected AccountInfo(Parcel in) {
        this.phoneNumber = in.readString();
        this.password = in.readString();
        this.acToken = in.readString();
        this.nickName = in.readString();
        this.swuId = in.readString();

    }

    public static final Parcelable.Creator<AccountInfo> CREATOR = new Parcelable.Creator<AccountInfo>() {
        @Override
        public AccountInfo createFromParcel(Parcel source) {
            return new AccountInfo(source);
        }

        @Override
        public AccountInfo[] newArray(int size) {
            return new AccountInfo[size];
        }
    };
}
