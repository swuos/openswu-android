package com.swuos.mobile.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

import com.swuos.mobile.R;
import com.swuos.mobile.entity.LibBookshelfItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 图书馆模块工具类
 * Created by youngkaaa on 2018/5/20
 */
public class LibUtils {
    public static String[] COLORS;

    static {
        COLORS = new String[]{"#8987FF", "#737EFF", "#FF89DA", "#ACE048", "#2BE386"};
    }

    /**
     * 获取 我的书架 剩余XX天数
     * @param context
     * @param item
     * @return
     */
    public static SpannableString getBookRemainDay(Context context, LibBookshelfItem item) {
        if (item == null || TextUtils.isEmpty(item.borrowTime) || TextUtils.isEmpty(item.backTime)) {
            return null;
        }
        if (context == null) {
            return null;
        }
        try {
            long borrowTime = Long.parseLong(item.borrowTime);
            long backTime = Long.parseLong(item.backTime);
            Calendar cBorrow = Calendar.getInstance();
            cBorrow.setTimeInMillis(borrowTime);
            Calendar cBack = Calendar.getInstance();
            cBack.setTimeInMillis(backTime);
            int borrowDay = cBorrow.get(Calendar.DAY_OF_YEAR);
            int backDay = cBack.get(Calendar.DAY_OF_YEAR);
            int dayDelta = backDay - borrowDay;
            String remain = context.getResources().getString(R.string.lib_remian, String.valueOf(dayDelta));
            SpannableString spannableString = new SpannableString(remain);
            int digIndex = findFirstDig(spannableString);
            if (digIndex == -1) {
                return null;
            }
            // 3.5 = 120px/34px
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(3.5f);
            spannableString.setSpan(sizeSpan, digIndex, remain.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannableString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 我的书架 应还书时间
     * @param context
     * @param item
     * @return
     */
    public static String getBackTime(Context context, LibBookshelfItem item) {
        if (context == null) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
            String formatTime = format.format(new Date(Long.parseLong(item.backTime)));
            return context.getResources().getString(R.string.lib_back, formatTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 随机产生一个背景色 用来填充 我的书架 中的每一项
     * @param pos
     * @return
     */
    public static String getBookBg(int pos) {
        try {
            Random r = new Random(System.currentTimeMillis());
            int i = r.nextInt(5);
            return COLORS[i];
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 找到字符串中第一个数字的索引返回
     *
     * @param str
     * @return
     */
    private static int findFirstDig(SpannableString str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
}
