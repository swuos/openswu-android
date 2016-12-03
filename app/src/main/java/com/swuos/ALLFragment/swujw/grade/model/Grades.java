package com.swuos.ALLFragment.swujw.grade.model;

import com.google.gson.Gson;
import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.swuassistant.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/1/23.
 */
public class Grades {


    public static List<GradeItem> getGradesList(TotalInfos totalInfo) {

        /*构建gson数据来解析json数据*/
        Gson gson = new Gson();
        totalInfo.setGrades(gson.fromJson(totalInfo.getGradesDataJson(), GradesData.class));
        /*储存成绩的列表*/
        List<GradeItem> gradeItemList = new ArrayList<>();
        /*成绩总和*/
        double cjCount = 0.0;
        /*学分总和*/
        double xfCount = 0.0;
        /*绩点总和*/
        double jdCount = 0.0;
        /*已获得的成绩信息进行整理*/
        GradesData gradesData = totalInfo.getGrades();
        /*设置列表的头部*/

        GradesData.Items items;
        GradeItem temgradeItem = new GradeItem();
        temgradeItem.setKcmc("科目名称");
        temgradeItem.setCj("成绩");
        temgradeItem.setXf("学分");
        temgradeItem.setJd("绩点");
        temgradeItem.setKsxzText("考试性质");
        temgradeItem.setKcxzText("课程性质");
        gradeItemList.add(temgradeItem);
        for (int i = 0; i < gradesData.getItems().size(); i++) {
            /*获得单个的科目成绩*/
            items = gradesData.getItems().get(i);
            String kcmc = items.getKcmc();
            String cj = items.getCj();
            String xf = items.getXf();
            String jd = items.getJd();
            String ksxz = items.getKsxz();
            String kcxz = items.getKcxzmc();
            String xh_id = items.getXh_id();
            String jxb_id = items.getJxb_id();
            String xnm = items.getXnm();
            String xqm = items.getXqm();
            /*用来处理成绩是按照ＡＢＣＤ评定的情况*/
            switch (cj) {
                case "A":
                    cjCount += 95;
                    break;
                case "B":
                    cjCount += 85;
                    break;
                case "C":
                    cjCount += 75;
                    break;
                case "D":
                    cjCount += 65;
                    break;
                case "E":
                    cjCount += 55;
                    break;
                default:
                    cjCount += Double.valueOf(cj);
                    break;
            }
            /*对成绩进行总和相加*/
            jdCount += Double.valueOf(jd);
            /*绩点不等于0时加学分*/
            if (!jd.contains("0")) {
                xfCount += Double.valueOf(xf);
            }
            GradeItem gradeItem = new GradeItem();
            gradeItem.setKcmc(kcmc);
            gradeItem.setCj(cj);
            gradeItem.setXf(xf);
            gradeItem.setJd(jd);
            gradeItem.setJxb_id(jxb_id);
            gradeItem.setXh_id(xh_id);
            gradeItem.setXnm(xnm);
            gradeItem.setXqm(xqm);
            gradeItem.setKsxzText(ksxz);
            gradeItem.setKcxzText(kcxz);
            /*添加到列表中*/
            gradeItemList.add(gradeItem);
        }
        /*设置列表的尾部，显示平均成绩和总成绩*/
        GradeItem gradeItemFooter1 = new GradeItem();
        gradeItemFooter1.setKcmc("平均");
        gradeItemFooter1.setCj(String.format("%.2f", cjCount / gradesData.getItems().size()));
        gradeItemFooter1.setXf(String.format("%.2f", xfCount / gradesData.getItems().size()));
        gradeItemFooter1.setJd(String.format("%.2f", jdCount / gradesData.getItems().size()));
        gradeItemList.add(gradeItemFooter1);

        GradeItem gradeItemFooter2 = new GradeItem();
        gradeItemFooter2.setKcmc("总和");
        gradeItemFooter2.setCj(String.format("%.2f", cjCount));
        gradeItemFooter2.setXf(String.format("%.2f", xfCount));
        gradeItemFooter2.setJd(String.format("%.2f", jdCount));
        gradeItemList.add(gradeItemFooter2);
        return gradeItemList;
    }

    public static GradeItem getGradeDetial(String respones, GradeItem gradeItem) {

        List<String[]> detial = new ArrayList<>();

        if (!respones.contains(Constant.NO_NET)) {
            Document body = Jsoup.parse(respones, "UTF-8");
            Element subtab = body.getElementById("subtab");
            Elements tbody = subtab.getElementsByTag("tbody");
            for (Element links : tbody) {
                Elements tr = links.getElementsByTag("tr");
                for (Element link1 : tr) {
                    Elements td = link1.getElementsByTag("td");
                    String[] tmp = {td.get(0).text(), td.get(1).text(), td.get(2).text(),};
                    detial.add(tmp);
                }
            }
        }
        gradeItem.setDetial(detial);
        return gradeItem;
    }

}
