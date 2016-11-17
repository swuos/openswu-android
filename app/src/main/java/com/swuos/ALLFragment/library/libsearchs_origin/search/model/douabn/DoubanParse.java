package com.swuos.ALLFragment.library.libsearchs_origin.search.model.douabn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by 张孟尧 on 2016/9/10.
 */
public class DoubanParse {
    public static String getbookcover(String html) {
        String coverImg = "0";


        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByAttributeValueEnding("src", ".jpg");
        for (Element e :
                elements) {
            coverImg = e.attr("src");
        }
        return coverImg;

    }
}
