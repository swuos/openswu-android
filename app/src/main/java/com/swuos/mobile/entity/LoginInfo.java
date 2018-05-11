package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.gallops.mobile.jmvclibrary.utils.json.JsonField;


/**
 * 测试用户信息
 * Created by wangyu on 2018/1/20.
 */

public class LoginInfo implements Parcelable, Cloneable {

    @JsonField("")
    private String acToken;

    public String getAcToken() {
        return acToken;
    }

    public void setAcToken(String acToken) {
        this.acToken = acToken;
    }

    @Override
    public LoginInfo clone() {
        try {
            return (LoginInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.acToken);
    }

    public LoginInfo() {
    }

    protected LoginInfo(Parcel in) {

        this.acToken = in.readString();
    }

    public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {
        @Override
        public LoginInfo createFromParcel(Parcel source) {
            return new LoginInfo(source);
        }

        @Override
        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };
}
