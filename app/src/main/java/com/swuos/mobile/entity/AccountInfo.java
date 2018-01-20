package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 账户信息
 * Created by wangyu on 2018/1/20.
 */

public class AccountInfo implements Parcelable {

    private String userName;
    private String userPwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userPwd);
    }

    public AccountInfo() {
    }

    protected AccountInfo(Parcel in) {
        this.userName = in.readString();
        this.userPwd = in.readString();
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
