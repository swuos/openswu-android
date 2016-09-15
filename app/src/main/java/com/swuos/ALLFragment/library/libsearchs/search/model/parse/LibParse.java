package com.swuos.ALLFragment.library.libsearchs.search.model.parse;


import com.swuos.ALLFragment.library.libsearchs.bookdetail.model.BookLocationInfo;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchBookItem;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchResult;
import com.swuos.ALLFragment.library.libsearchs.search.model.douabn.DoubanApi;
import com.swuos.ALLFragment.library.libsearchs.search.model.douabn.DoubanParse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.functions.Func1;


/**
 * Created by 张孟尧 on 2016/9/3.
 */
public class LibParse {
    public static SearchResult getSearchList(String resulthtml) {
        SearchResult searchResult = new SearchResult();
        List<SearchBookItem> searchBookItemList = new ArrayList<>();
        Document document = Jsoup.parse(resulthtml);
        String sizetemp = document.getElementsByAttributeValue("id", "LblpageInfor").get(0).text();
        int size = Integer.parseInt(sizetemp.substring(6, sizetemp.indexOf(" 耗时")));
        searchResult.setBookSize(size);
        Elements elements = document.getElementsByAttributeValue("bordercolor", "#76BCD6");
        Elements ll = elements.get(0).getElementsByAttributeValueEnding("id", "LabeBookName");
        for (Element pp : ll) {
            final SearchBookItem searchBookItem = new SearchBookItem();
            searchBookItem.setBookName(pp.text().replace("》        [点击查看详细信息]", "").replaceAll("《", ""));
            Element ee = pp.parent().parent();
            Elements rr = ee.siblingElements();
            String suoShuHao = rr.get(0).text();
            searchBookItem.setBookSuoshuhao("索书号: " + suoShuHao.substring(4, suoShuHao.indexOf("&nbsp")));
            searchBookItem.setISBN("I S B N: " + suoShuHao.substring(suoShuHao.indexOf("ISBN/ISSN：") + 10).replace(" ", ""));
            searchBookItem.setPublisher(rr.get(1).getElementsByAttributeValueEnding("id", "Label1").text());
            searchBookItem.setSummary("摘    要: " + rr.get(2).text());
            searchBookItem.setWriter("作    者: " + rr.get(3).text().replace("著", ""));
            searchBookItem.setBookNumber(rr.get(5).text());
            DoubanApi.getDoubanSearch().doubanSearch(searchBookItem.getISBN().substring(9).replace("-", "")).map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    return DoubanParse.getbookcover(s);
                }
            }).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(String s) {
                    searchBookItem.setBookCoverUrl(s);
                }

            });
            searchBookItemList.add(searchBookItem);
        }
        searchResult.setSearchbookItemList(searchBookItemList);
        return searchResult;
    }

    public static List<BookLocationInfo> getBookDetail(String resulthtml) {
        List<BookLocationInfo> bookLocationInfoList = new ArrayList<>();
        Document documen = Jsoup.parse(resulthtml);
        Elements elements = documen.getElementsByAttributeValue("id", "DataGrid1");
        Elements bro = elements.get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr").get(0).siblingElements();
        for (Element cc : bro) {
            BookLocationInfo bookLocationInfo = new BookLocationInfo();

            Elements dd = cc.getElementsByTag("td");
            bookLocationInfo.setAddress("藏址: " + dd.get(2).text());

            bookLocationInfo.setFrameState("在架: " + dd.get(3).text());
            bookLocationInfo.setShelfUrl(dd.get(7).getElementsByTag("a").get(0).attr("href"));
            bookLocationInfoList.add(bookLocationInfo);
        }
        return bookLocationInfoList;
    }

    public static String getBookLocation(String locationHtml) {
        String s1 = locationHtml.substring(locationHtml.indexOf("var strWZxxxxxx = \"") + 19, locationHtml.indexOf("\";") + 1);
        String s = "非自助借还图书无法定位";
        if (!s1.equals("\"")) {
            s = "架位: " + s1.substring(s1.indexOf("|") + 1);
        }
        return s;
    }
}
