package com.swuos.ALLFragment.library.library.utils;

import android.util.Log;

import com.swuos.ALLFragment.library.library.model.BookItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by : youngkaaa on 2016/10/27.
 * Contact me : 645326280@qq.com
 */
public class Parser {
    private static final String TAG="Parser";
    public static List<BookItem> makeBookItems(String s) {
        List<BookItem> bookItems = new ArrayList<>();
        Document parse = Jsoup.parse(s);
        Elements td = parse.getElementsByTag("td");
        for (int i = 14; i < td.size(); i += 5) {
            BookItem item = new BookItem(td.get(i).text(), td.get(i + 1).text(),
                    td.get(i + 2).text(), td.get(i + 3).text(), Float.valueOf(td.get(i + 4).text()));
//            System.out.println("td.get("+i+").text()"+td.get(i).text());
            bookItems.add(item);
        }
//        for(BookItem item:bookItems){
//            System.out.println();
//            System.out.println("****************************************************");
//            System.out.println("** item.getBookName()==>"+item.getBookName());
//            System.out.println("** item.getBookIsbn()==>"+item.getBookIsbn());
//            System.out.println("** item.getKind()==>"+item.getKind());
//            System.out.println("** item.getTime()==>"+item.getTime());
//            System.out.println("** item.getFine()==>"+item.getFine());
//            System.out.println("****************************************************");
//            System.out.println();
//        }
//        System.out.println("bookItems.size()==>"+bookItems.size());
        return bookItems;
    }

    public static List<String> makeAdapterKeys(List<BookItem> bookItems) {
        List<String> keys = new ArrayList<>();
        String temp = null;
        for (BookItem item : bookItems) {
            String[] split = item.getTime().split("-");
            if (!keys.contains(split[0])) {
                keys.add(split[0]);
            }
        }
        keys=keysSort(keys);
        return keys;
    }

    /**
     * 按年份从大到小排序，由于List.sort()方法在Android上调用有要求：即api24以上才可以
     * @param keys
     * @return
     */
    private static List<String> keysSort(List<String> keys) {
//        System.out.println(keys.size());
        List<String> tempList=new ArrayList<>();
        int [] temp=new int[keys.size()];
        for(int i=0;i<keys.size();i++){
            temp[i]= Integer.parseInt(keys.get(i));
        }
        for(int i=0;i<temp.length-1;i++){
            for(int j=i+1;j<temp.length;j++){
                if(temp[i]<=temp[j]){
                    int temp1=temp[i];
                    temp[i]=temp[j];
                    temp[j]=temp1;
                }
            }
        }

        for(int i=0;i<temp.length;i++){
//            System.out.println(temp[i]);
            tempList.add(String.valueOf(temp[i]));
        }
        return tempList;
    }

    public static Map<String, List<BookItem>> makeAdapterMap(List<BookItem> bookItems){
        Map<String, List<BookItem>> data=new HashMap<>();
        List<String> keys = makeAdapterKeys(bookItems);
        for(String s:keys){
            data.put(s,findListByKey(s,bookItems));
        }

        for(Map.Entry<String, List<BookItem>> entry:data.entrySet()){
            System.out.println();
            System.out.println("####################################");
            System.out.println();
            System.out.println(entry.getKey());
            List<BookItem> value = entry.getValue();
            for(BookItem item:value){
                System.out.println("## item.getBookName()==>"+item.getBookName());
                System.out.println("## item.getBookIsbn()==>"+item.getBookIsbn());
                System.out.println("## item.getKind()==>"+item.getKind());
                System.out.println("## item.getTime()==>"+item.getTime());
                System.out.println("## item.getFine()==>"+item.getFine());
            }
            System.out.println("####################################");
            System.out.println();
        }
        return data;
    }

    public static int getTotalPage(String s){
        int totalPage=0;
//        System.out.println(s);
        Document parse = Jsoup.parse(s);
        Elements span = parse.getElementsByTag("span");
//        for(int i=0;i<span.size();i++){
//            System.out.println("span.get(i).text()==>"+span.get(i).text());
//        }
//        System.out.println(span.get(span.size()-1));
        String text = span.get(span.size() - 1).text();

        return findInt(text);
    }

    /**
     *  从一个字符串中找到其中的数值输出，匹配最前面的数字
     *
     * @param s
     * @return
     */
    private static int findInt(String s) {
        int result=0;
        StringBuffer buffer=new StringBuffer();
        for(int i=0;i<s.length();i++){
            char temp=s.charAt(i);
            if(temp>='0' && temp<='9'){
                buffer.append(temp);
            }
        }
        result= Integer.valueOf(buffer.toString());
        return result;
    }

    private static List<BookItem> findListByKey(String s, List<BookItem> bookItems) {
        List<BookItem> bookItemList=new ArrayList<>();
        for(BookItem item:bookItems){
            if(item.getTime().startsWith(s)){
                bookItemList.add(item);
            }
        }
//        for(BookItem item:bookItemList){
//            System.out.println();
//            System.out.println("####################################");
//            System.out.println("## item.getBookName()==>"+item.getBookName());
//            System.out.println("## item.getBookIsbn()==>"+item.getBookIsbn());
//            System.out.println("## item.getKind()==>"+item.getKind());
//            System.out.println("## item.getTime()==>"+item.getTime());
//            System.out.println("## item.getFine()==>"+item.getFine());
//            System.out.println("####################################");
//        }
        return sortDate(bookItemList);
//        return bookItemList;
    }

    private static List<BookItem> sortDate(List<BookItem> list) {
        List<BookItem> bookItems=new ArrayList<>();
        BookItem [] items=new BookItem[list.size()];
        for(int i=0;i<list.size();i++){
            items[i]=list.get(i);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.d(TAG,"list.size()==>"+list.size());
        for(int i=0;i<list.size()-1;i++){
            for(int j=i+1;j<list.size();j++){
                BookItem itemI=list.get(i);
                BookItem itemJ=list.get(j);
                Date dateI= null;
                Date dateJ= null;
                try {
                    dateI = sdf.parse(itemI.getTime());
                    dateJ = sdf.parse(itemJ.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(dateI.before(dateJ)){
                    BookItem itemTemp=items[i];
                    items[i]=items[j];
                    items[j]=itemTemp;
                }
            }
        }
        for(int i=0;i<list.size();i++){
            bookItems.add(items[i]);
            Tools.log("kklog",items[i]);
        }
        return bookItems;
    }
}
