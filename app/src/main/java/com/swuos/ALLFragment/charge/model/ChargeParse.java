package com.swuos.ALLFragment.charge.model;

import com.mran.polylinechart.ChargeBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/19.
 */
public class  ChargeParse {
    public static String getBlnace(String html)
    {
        Document document= Jsoup.parse(html);
        Elements elements=document.getElementsByTag("tbody");
        Element balance=elements.get(0).getElementsByTag("tr").get(6).getElementsByTag("td").get(1);
        return balance.text().replace("（","").replace("）","");
    }
    public static List<ChargeBean> getChargeBean(String html){
        Document document=Jsoup.parse(html);
        List<ChargeBean> chargeBeanList=new ArrayList<>();
        Elements elements=document.getElementsByTag("tbody").get(1).getElementsByTag("tr");
        for (int i = 1; i < elements.size(); i++) {
            Element element=elements.get(i);
            Elements line=element.getElementsByTag("td");
            ChargeBean chargeBean=new ChargeBean();
            chargeBean.setBalance(line.get(2).text()+"元");
            chargeBean.setDate(line.get(0).text().substring(5).replace("-","."));
            chargeBean.setCoast(Float.parseFloat(line.get(13).text()));
            chargeBeanList.add(chargeBean);
        }
        return chargeBeanList;
    }
}
