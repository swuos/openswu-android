package com.swuos.ALLFragment.wifi.util;

/**
 * Created by 张孟尧 on 2016/11/17.
 */

public class Parse {
    public static String getSwuNetInfo(String s) {
        return s.substring(32, s.indexOf("'</script>"));
    }

    public static String getSwuNetInfoQueryString(String s) {
        return s.substring(s.indexOf("eportal/index.jsp?") + 18, s.indexOf("'</script>"));
    }
}
