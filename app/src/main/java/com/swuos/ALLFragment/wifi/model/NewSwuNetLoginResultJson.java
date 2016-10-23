package com.swuos.ALLFragment.wifi.model;

/**
 * Created by 张孟尧 on 2016/10/18.
 */
public class NewSwuNetLoginResultJson {
    /**
     * userIndex : 66396462623366653131613166346533623563636534613635666337396366395f3137322e32342e32332e3131345f6d72616e
     * result : success
     * message :
     * keepaliveInterval : 0
     * validCodeUrl :
     */

    private String userIndex;
    private String result;
    private String message;
    private int keepaliveInterval;
    private String validCodeUrl;

    public String getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(String userIndex) {
        this.userIndex = userIndex;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getKeepaliveInterval() {
        return keepaliveInterval;
    }

    public void setKeepaliveInterval(int keepaliveInterval) {
        this.keepaliveInterval = keepaliveInterval;
    }

    public String getValidCodeUrl() {
        return validCodeUrl;
    }

    public void setValidCodeUrl(String validCodeUrl) {
        this.validCodeUrl = validCodeUrl;
    }
}
