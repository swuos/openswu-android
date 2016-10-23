package com.swuos.ALLFragment.wifi.model;

import com.google.gson.Gson;

/**
 * Created by 张孟尧 on 2016/10/18.
 */
public class NewSwuNetParse {
    public static String getCurrentDeviceInfo(String s) {
        return s.substring(s.indexOf("wlanuserip"), s.lastIndexOf("'</script"));
    }

    public static <T> T str2json(String s, Class<T> T) {
        Gson gson = new Gson();

        return gson.fromJson(s, T);
    }
}
