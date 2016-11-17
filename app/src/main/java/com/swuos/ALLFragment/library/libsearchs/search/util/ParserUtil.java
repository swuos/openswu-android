package com.swuos.ALLFragment.library.libsearchs.search.util;

import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchBookItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张孟尧 on 2016/10/25.
 */
public class ParserUtil {
    public static List<SearchBookItem> getSearchResult(String s) {

        List<SearchBookItem> lists = new ArrayList<>();
        Document document = Jsoup.parse(s);
        Elements elements0 = document.getElementsByClass("search_info_l");
        String bookNumber = elements0.first().getElementsByTag("span").first().text();
        if ("0".equals(bookNumber)) {
            return lists;
        }
        Elements elements = document.getElementsByClass("jp-searchList");
        Elements elements1 = elements.first().getElementsByTag("ul").first().getElementsByTag("li");
        for (Element aa : elements1) {
            SearchBookItem searchBookItem = new SearchBookItem();
            searchBookItem.setSearchResultNum(Integer.parseInt(bookNumber));
            Element bb = aa.getElementsByTag("h2").first().getElementsByTag("a").first();
            searchBookItem.setBookDetailUrl(bb.attr("href"));
            searchBookItem.setBookName(bb.text());
            Elements cc = aa.getElementsByTag("div").first().getElementsByTag("p");
            Element cc1 = aa.getElementsByTag("div").first().getElementsByTag("input").first();
            searchBookItem.setBookId(cc1.attr("value").replace("/图书", ""));
            for (Element dd : cc) {
                String classname = dd.className();
                switch (classname) {
                    case "creator":
                        searchBookItem.setWriter(dd.text());
                        break;
                    case "call_number":
                        searchBookItem.setBookSuoshuhao(dd.text());
                        break;
                    case "isbn":
                        searchBookItem.setISBN(dd.text());
                        break;
                    case "publisher":
                        searchBookItem.setPublisher(dd.text());
                        break;

                }

            }
            lists.add(searchBookItem);
        }
        return lists;
    }


}
