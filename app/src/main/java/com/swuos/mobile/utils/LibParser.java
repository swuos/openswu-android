package com.swuos.mobile.utils;

import com.swuos.mobile.entity.LibBookshelfItem;
import com.swuos.mobile.entity.LibPopularItem;
import com.swuos.mobile.entity.LibSearchItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngkaaa on 2018/5/21
 */
public class LibParser {
    public static List<LibPopularItem> parserLibPopularList(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        try {
            JSONArray data = jsonObject.optJSONArray("data");
            if (data == null) {
                return new ArrayList<>(); // empty list，no more data
            }
            List<LibPopularItem> datas = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);
                if (object == null) {
                    continue;
                }
                LibPopularItem item = new LibPopularItem();
                item.bookName = object.optString("bookName");
                item.id = object.optString("id");
                item.author = object.optString("author");
                item.summary = object.optString("summary");
                item.thumb = object.optString("thumb");
                datas.add(item);
            }
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<LibBookshelfItem> parserLibBookshelfList(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        try {
            JSONArray data = jsonObject.optJSONArray("data");
            if (data == null) {
                return new ArrayList<>(); // empty list，no more data
            }
            List<LibBookshelfItem> datas = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);
                if (object == null) {
                    continue;
                }
                LibBookshelfItem item = new LibBookshelfItem();
                item.bookName = object.optString("bookName");
                item.id = object.optString("id");
                item.author = object.optString("author");
                item.borrowTime = object.optString("borrowTime");
                item.backTime = object.optString("backTime");
                item.thumb = object.optString("thumb");
                datas.add(item);
            }
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static List<LibSearchItem> parserLibSearchList(JSONObject jsonObject){
        if (jsonObject == null) {
            return null;
        }
        try {
            JSONArray data = jsonObject.optJSONArray("data");
            if (data == null) {
                return new ArrayList<>(); // empty list，no more data
            }
            List<LibSearchItem> datas = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);
                if (object == null) {
                    continue;
                }
                LibSearchItem item = new LibSearchItem();
                item.bookName = object.optString("bookName");
                item.id = object.optString("id");
                item.author = object.optString("author");
                item.isbn = object.optString("isbn");
                item.remainNum = object.optString("remainNum");
                item.publisher = object.optString("publisher");
                item.thumb = object.optString("thumb");
                datas.add(item);
            }
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }
}
