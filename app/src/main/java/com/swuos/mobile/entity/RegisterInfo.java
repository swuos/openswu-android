package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.gallops.mobile.jmvclibrary.utils.json.JsonField;


/**
 * 测试用户信息
 * Created by wangyu on 2018/1/20.
 */

public class RegisterInfo implements Parcelable, Cloneable {
    @JsonField("nickname")
    private String nickname;
    @JsonField("avatar")
    private String avatar;
    @JsonField("acToken")
    private String acToken;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAcToken() {
        return acToken;
    }

    public void setAcToken(String acToken) {
        this.acToken = acToken;
    }

    @Override
    public RegisterInfo clone() {
        try {
            return (RegisterInfo) super.clone();
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
        dest.writeString(this.nickname);
        dest.writeString(this.avatar);
        dest.writeString(this.acToken);
    }

    public RegisterInfo() {
    }

    protected RegisterInfo(Parcel in) {
        this.nickname = in.readString();
        this.avatar = in.readString();
        this.acToken = in.readString();
    }

    public static final Creator<RegisterInfo> CREATOR = new Creator<RegisterInfo>() {
        @Override
        public RegisterInfo createFromParcel(Parcel source) {
            return new RegisterInfo(source);
        }

        @Override
        public RegisterInfo[] newArray(int size) {
            return new RegisterInfo[size];
        }
    };
}
