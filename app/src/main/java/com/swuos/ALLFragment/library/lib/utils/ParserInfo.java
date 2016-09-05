package com.swuos.ALLFragment.library.lib.utils;

import android.os.Bundle;

import com.google.gson.JsonObject;
import com.swuos.ALLFragment.library.lib.model.BookBean1;
import com.swuos.ALLFragment.library.lib.model.BookBean2;
import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * Created by : youngkaaa on 2016/9/3.
 * Contact me : 645326280@qq.com
 */
public class ParserInfo {
    private static OkhttpNet okhttpNet = new OkhttpNet();
    private static RequestBody requestBody;
    private static Headers headers;
    private static JsonObject json;


    public static String loginLibrary(String name, String pd) {
        String url = "http://202.202.121.3/netweb/ReaderLogin.aspx";
        String temp1 = "/wEPDwULLTEyMTk3ODg5MTUPZBYCAgMPZBYCAgUPZBYCZg9kFggCAQ9kFgQCAQ8WAh4JaW5uZXJodG1sBQzljJfkuqzph5Hnm5hkAgMPDxYCHgRUZXh0BawFPHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J2RlZmF1bHQuYXNweCc+PHNwYW4+6aaW6aG1PC9zcGFuPjwvQT48L3RkPjx0ZCBzdHlsZT0iaGVpZ2h0OiAyMXB4Ij48QSBocmVmPSdkZWZhdWx0LmFzcHgnPjxzcGFuPuS5puebruafpeivojwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nTWFnYXppbmVDYW50b1NjYXJjaC5hc3B4Jz48c3Bhbj7mnJ/liIrnr4flkI08L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J1Jlc2VydmVkTGlzdC5hc3B4Jz48c3Bhbj7pooTnuqbliLDppoY8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J0V4cGlyZWRMaXN0LmFzcHgnPjxzcGFuPui2heacn+WFrOWRijwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nTmV3Qm9vS1NjYXJjaC5hc3B4Jz48c3Bhbj7mlrDkuabpgJrmiqU8L3NwYW4+PC9BPjwvdGQ+PHRkIHN0eWxlPSJoZWlnaHQ6IDIxcHgiPjxBIGhyZWY9J1JlYWRlckxvZ2luLmFzcHgnPjxzcGFuPuivu+iAheeZu+W9lTwvc3Bhbj48L0E+PC90ZD48dGQgc3R5bGU9ImhlaWdodDogMjFweCI+PEEgaHJlZj0nUmVhZGVyVGFibGUuYXNweCc+PHNwYW4+6K+76ICF566h55CGPC9zcGFuPC9hPjwvdGQ+ZGQCAw8PFgIfAQUG6YeR55uYZGQCBQ9kFgICAg8PFgIfAQU0PHNwYW4+IOasoui/juaCqDrmnajlrr0g6K+36YCJ5oup5L2g55qE5pON5L2cPC9zcGFuPmRkAgcPZBYCAgEPZBYCAgMPZBYCZg8QZBAVBAzor7vogIXmnaHnoIEM5YCf5Lmm6K+B5Y+3DOi6q+S7veivgeWPtwblp5PlkI0VBAzor7vogIXmnaHnoIEM5YCf5Lmm6K+B5Y+3DOi6q+S7veivgeWPtwblp5PlkI0UKwMEZ2dnZ2RkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQxJbWFnZUJ1dHRvbjG6zB2UVCMz7FE0gKZ/4G/VuifxdQ==";
        String temp2 = "/wEWCgKqh+2dDQL7hryJDwLgnZ70BALntNySDgLrr+CHBALXkt+6BALwuLirBQLs0bLrBgLs0fbZDALSwpnTCKCbnYG7seHjYV1Lg2Vs+NhuRKAW";
        requestBody = new FormBody.Builder()
                .add("ScriptManager1", "UpdatePanel1|ImageButton1")
                .add("__EVENTTARGET", "")
                .add("__EVENTARGUMENT", "")
                .add("__VIEWSTATE", temp1)
                .add("__EVENTVALIDATION", temp2)
                .add("DropDownList1", "读者条码")
                .add("TextBox1", name)
                .add("TextBox2", pd)
                .add("__ASYNCPOST", "true")
                .add("ImageButton1.x", "15")
                .add("ImageButton1.y", "8")
                .build();
        headers = new Headers.Builder()
                .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                .build();
        String s = okhttpNet.doPost(url, requestBody, headers);

        SALog.d("kklog", s);
        return s;
    }

    public static String readTable() {
        String readUri = "http://202.202.121.3/netweb/ReaderTable.aspx";
        requestBody = new FormBody.Builder().build();
        String s = okhttpNet.doPost(readUri, requestBody);
        return s;
    }

    public static String getBorrowInfo() {
        String borrowUri = "http://202.202.121.3/netweb/HisdoryList.aspx";
        requestBody = new FormBody.Builder().build();
        String s = okhttpNet.doPost(borrowUri, requestBody);
        return s;
    }

    public static String getBorrowInfo(int index) {
        String borrowUri = "http://202.202.121.3/netweb/HisdoryList.aspx?PageNo=" + index;

        SALog.d("kklog", "getBorrowInfo borrowUri===>" + borrowUri);
        requestBody = new FormBody.Builder().build();
        String s = okhttpNet.doPost(borrowUri, requestBody);
        SALog.d("kklog", "getBorrowInfo() s===>" + s);
        return s;
    }

    public static List<String> parserHomePageHtml(String html) {
        Bundle bundle = new Bundle();
        List<String> vals = new ArrayList<>();
        vals.add(matchHomePageItem(html, Constant.LIB_NO));
        vals.add(matchHomePageItem(html, Constant.LIB_CARD_NO));
        vals.add(matchHomePageItem(html, Constant.LIB_READER_BAR));
        vals.add(matchHomePageItem(html, Constant.LIB_READER_NAME));
        vals.add(matchHomePageItem(html, Constant.LIB_READER_SEX));
        vals.add(matchHomePageItem(html, Constant.LIB_READER_UNIT));
        vals.add(matchHomePageItem(html, Constant.LIB_DEBT));
        vals.add(matchHomePageItem(html, Constant.LIB_PAY));
        vals.add(matchHomePageItem(html, Constant.LIB_BORROW_COUNT));
        return vals;
    }


    public static List<BookBean2> parserBorrowHtml(String html) {
        SALog.d("kklog", "################# parserBorrowHtml() start####################");
        List<BookBean1> bookBeen = matchBorrowItem(html);


        for (BookBean1 bean : bookBeen) {
            SALog.d("kklog", "parserBorrowHtml() bean.getBookName()" + bean.getBookName());
            SALog.d("kklog", "parserBorrowHtml() bean.getLoginId()" + bean.getLoginId());
            SALog.d("kklog", "parserBorrowHtml() bean.getBarCode()" + bean.getBarCode());
            SALog.d("kklog", "parserBorrowHtml() bean.getOpKind()" + bean.getOpKind());
            SALog.d("kklog", "parserBorrowHtml() bean.getOpTime()" + bean.getOpTime());
        }
        if (bookBeen.isEmpty()) {
            SALog.d("kklog", "parserBorrowHtml bookBeen isEmpty");
        } else {
            SALog.d("kklog", "parserBorrowHtml bookBeen not empty");
        }
        SALog.d("kklog", "################# parserBorrowHtml() end####################");
        return makeBookBeen(bookBeen);
    }

    public static String getBorrowTotalPage(String html) {
        String result = null;
        String totalPageRegex = "if\\(b>(.+?)\\)";
        Pattern pattern = Pattern.compile(totalPageRegex);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            SALog.d("kklog", "matchHomePageItem() groupCount=>" + matcher.groupCount());
            for (int i = 1; i <= matcher.groupCount(); i++) {
                result = matcher.group(i);
                SALog.d("kklog", "matchHomePageItem() matcher.group(" + i + ")=>" + result);
            }
        }
        return result;
    }

    public static Bundle makeBorrowInfoToBundle(BookBean2 bookBean2) {
        String latestOpTime = bookBean2.getOpTimes().get(0);
        String firstOpTime = bookBean2.getOpTimes().get(bookBean2.getOpTimes().size() - 1);
        String latestOpKind = bookBean2.getOpKinds().get(0);
        String firstOpKind = bookBean2.getOpKinds().get(bookBean2.getOpKinds().size() - 1);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.LIB_BOOK_LATEST_OP_TIME, latestOpTime);
        bundle.putString(Constant.LIB_BOOK_LATEST_OP_KIND, latestOpKind);
        bundle.putString(Constant.LIB_BOOK_FIRST_OP_TIME, firstOpTime);
        bundle.putString(Constant.LIB_BOOK_FIRST_OP_KIND, firstOpKind);
        return bundle;
    }

    public static boolean checkIfCanRenew(BookBean2 bookBean2) {
        List<String> opKinds = bookBean2.getOpKinds();
        if (opKinds.contains("续借")) {
            return false;
        } else {
            return true;
        }
    }


    private static List<BookBean1> matchBorrowItem(String html) {
        String bookRegex = "<td>(.+?)</td><td>(.+?)</td><td>(.+?)</td><td>(.+?)</td><td>(.+?)</td>";
        List<BookBean1> bookBeen = new ArrayList<>();
        BookBean1 bookBean1;
        String result = null;
        int count = 0;
        Pattern pattern = Pattern.compile(bookRegex);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            if (count != 0) {
                bookBean1 = new BookBean1();
                SALog.d("kklog", "matchHomePageItem() groupCount=>" + matcher.groupCount());
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    result = matchStringToVaild(matcher.group(i));
                    SALog.d("kklog", "matchHomePageItem() matcher.group(" + i + ")=>" + result);
                }
                bookBean1.setBookName(matchStringToVaild(matcher.group(1)));
                bookBean1.setLoginId(matchStringToVaild(matcher.group(2)));
                bookBean1.setBarCode(matchStringToVaild(matcher.group(3)));
                bookBean1.setOpTime(matchStringToVaild(matcher.group(4)));
                bookBean1.setOpKind(matchStringToVaild(matcher.group(5)));
                bookBeen.add(bookBean1);
            }
            count++;
        }
        return bookBeen;
    }

    private static List<BookBean2> makeBookBeen(List<BookBean1> bookBean1List) {
        List<BookBean2> bookBeen = new ArrayList<>();
        List<Integer> hasJudged = new ArrayList<>();
        for (int i = 0; i < bookBean1List.size(); i++) {
            BookBean2 bookBean2 = new BookBean2();
            List<String> opTimes = new ArrayList<>();
            List<String> opKinds = new ArrayList<>();
            if (checkBookExist(bookBeen, bookBean1List.get(i).getLoginId())) {
                continue;
            }
//            if (hasJudged.contains(i)) {
//                break;
//            }
            for (int j = i; j < bookBean1List.size(); j++) {
                if (bookBean1List.get(i).getLoginId().equals(bookBean1List.get(j).getLoginId())) {
                    bookBean2.setBookName(bookBean1List.get(j).getBookName());
                    bookBean2.setLoginId(bookBean1List.get(j).getLoginId());
                    bookBean2.setBarCode(bookBean1List.get(j).getBarCode());
                    opTimes.add(bookBean1List.get(j).getOpTime());
                    opKinds.add(bookBean1List.get(j).getOpKind());
                }
            }
            bookBean2.setOpTimes(opTimes);
            bookBean2.setOpKinds(opKinds);
            bookBeen.add(bookBean2);
        }
//        for (BookBean2 book : bookBeen) {
//            SALog.d("kklog", "makeBookBeen book.getBookName()==>" + book.getBookName());
//            SALog.d("kklog", "makeBookBeen book.getLoginId() ==>" + book.getLoginId());
//            SALog.d("kklog", "makeBookBeen book.getBarCode() ==>" + book.getBarCode());
//            for (String s : book.getOpKinds()) {
//                SALog.d("kklog", "makeBookBeen book.getOpKinds()=>" + s);
//            }
//            for (String s : book.getOpTimes()) {
//                SALog.d("kklog", "makeBookBeen book.getOpTimes()=>" + s);
//            }
//        }
        if (bookBeen.isEmpty()) {
            SALog.d("kklog", "makeBookBeen bookBeen isEmpty");
        } else {
            SALog.d("kklog", "makeBookBeen bookBeen not empty");
        }
        return bookBeen;
    }

    private static boolean checkBookExist(List<BookBean2> bookBeen, String loginId) {

        for (BookBean2 bookBean2 : bookBeen) {
            if (bookBean2.getLoginId().equals(loginId)) {
                return true;    //already existed!
            }
        }

        return false;
    }


    private static String filterChinese(String chin) {
        chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        return chin;
    }

    private static String matchStringToVaild(String s) {
        String regex = "<font color=\"#000066\">(.+?)</font>";
        String result = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            result = matcher.group(1);
            SALog.d("kklog", "matchStringToVaild() result=>" + result);
        }
        return result;

    }

    private static String matchHomePageItem(String html, String itemRegex) {
        String result = null;
        String regex = "<span id=\"" + itemRegex + "\">(.+?)</span>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            result = matcher.group(1);
            SALog.d("kklog", "matchHomePageItem() matcher.group(i)=>" + result);
        }
        return result;
    }

    //translation the bundle's key to chinese
    public static String tranKeyToCH(String key) {
        switch (key) {
            case Constant.LIB_NO:
                return "内部编号";
            case Constant.LIB_CARD_NO:
                return "借书证号";
            case Constant.LIB_READER_BAR:
                return "读者条码";
            case Constant.LIB_READER_NAME:
                return "姓名";
            case Constant.LIB_READER_SEX:
                return "性别";
            case Constant.LIB_READER_UNIT:
                return "单位";
            case Constant.LIB_DEBT:
                return "欠罚款";
            case Constant.LIB_PAY:
                return "欠赔款";
            case Constant.LIB_BORROW_COUNT:
                return "已外借";
            default:
                return "未知";
        }

    }

    public static String makeDialogText(BookBean2 bookBean2) {
        StringBuilder builder = new StringBuilder();
        List<String> opKinds = bookBean2.getOpKinds();
        List<String> opTimes = bookBean2.getOpTimes();
        int blankCount = 0;
        builder.append("                              \n");
        for (int i = 0; i < opKinds.size(); i++) {
            builder.append("  ");
            blankCount = 18 - opTimes.get(i).length();
            for (int j = blankCount; j >= 0; j--) {
                builder.append(" ");
            }
            builder.append(opTimes.get(i));

            for (int j = 20 - opTimes.get(i).length(); j >= 0; j--) {
                builder.append(" ");
            }

            builder.append(opKinds.get(i) + "\n");
            for (int j = blankCount; j >= 0; j--) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }


}
