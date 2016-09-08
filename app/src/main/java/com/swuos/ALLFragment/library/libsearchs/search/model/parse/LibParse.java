package com.swuos.ALLFragment.library.libsearchs.search.model.parse;


import com.swuos.ALLFragment.library.libsearchs.bookdetail.model.BookLocationInfo;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchBookItem;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张孟尧 on 2016/9/3.
 */
public class LibParse {
    public static SearchResult getSearchList(String resulthtml) {
        SearchResult searchResult=new SearchResult();
        List<SearchBookItem> searchBookItemList = new ArrayList<>();
        Document document = Jsoup.parse(resulthtml);
        String sizetemp=document.getElementsByAttributeValue("id", "LblpageInfor").get(0).text();
        int size= Integer.parseInt(sizetemp.substring(6,sizetemp.indexOf(" 耗时")));
        searchResult.setBookSize(size);
        Elements elements = document.getElementsByAttributeValue("bordercolor", "#76BCD6");
        Elements ll = elements.get(0).getElementsByAttributeValueEnding("id", "LabeBookName");
        for (Element pp : ll) {
            SearchBookItem searchBookItem = new SearchBookItem();
            searchBookItem.setBookName(pp.text().replace("》        [点击查看详细信息]", "").replaceAll("《", ""));
            Element ee = pp.parent().parent();
            Elements rr = ee.siblingElements();
            String suoShuHao = rr.get(0).text();
            searchBookItem.setBookSuoshuhao("索书号: "+suoShuHao.substring(4, suoShuHao.indexOf("&nbsp")));
            searchBookItem.setISBN("I S B N: "+suoShuHao.substring(suoShuHao.indexOf("ISBN/ISSN：")+10));
            searchBookItem.setPublisher(rr.get(1).getElementsByAttributeValueEnding("id", "Label1").text());
            searchBookItem.setSummary("摘    要: "+rr.get(2).text());
            searchBookItem.setWriter("作    者: "+rr.get(3).text().replace("著",""));
            searchBookItem.setBookNumber(rr.get(5).text());
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
            bookLocationInfo.setAddress(dd.get(2).text());

            bookLocationInfo.setFrameState(dd.get(3).text());
            bookLocationInfo.setShelf(dd.get(7).getElementsByTag("a").get(0).attr("href"));
            bookLocationInfoList.add(bookLocationInfo);
        }
        return bookLocationInfoList;
    }

    public static String getBookLocation(String locationHtml) {
        String s = locationHtml.substring(locationHtml.indexOf("var strWZxxxxxx = \"") + 20, locationHtml.indexOf("\";"));
        return s;
    }
}
