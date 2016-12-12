package com.swuos.ALLFragment.swujw.grade.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/12/10.
 */
public class SwuJwParse {
    public static List<TeacherJudgementItem> getTeachList(String s) {
        Document document = Jsoup.parse(s);
        List<TeacherJudgementItem> result = new ArrayList<>();
        Element elements = document.getElementById("jxb_body");
        Elements a = elements.getElementsByTag("tr");
        for (Element aa : a) {
            TeacherJudgementItem teacherJudgementItem = new TeacherJudgementItem();
            teacherJudgementItem.setKch_id(aa.attr("data-kch_id"));
            teacherJudgementItem.setJgh_id(aa.attr("data-jgh_id"));
            teacherJudgementItem.setXsdm(aa.attr("data-xsdm"));
            teacherJudgementItem.setJxb_id(aa.attr("data-jxb_id"));
            Elements bb = aa.getElementsByTag("td");
            teacherJudgementItem.setTeacherName(bb.get(3).text());
            teacherJudgementItem.setClassName(bb.get(1).text());
            result.add(teacherJudgementItem);
        }
        return result;
    }
}
