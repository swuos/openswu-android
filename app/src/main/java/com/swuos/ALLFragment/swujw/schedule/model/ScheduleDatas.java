package com.swuos.ALLFragment.swujw.schedule.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/22.
 */
public class ScheduleDatas {
    /**
     * kblx : 7
     * xsxx : {"XH_ID":"222014327012166","XQMMC":"1","XNM":"2016","XH":"222014327012166","XKKG":"1","XKKGXQ":"1~('3')","KXKXXQ":"('3')","XNMC":"2016-2017","XQM":"3","XM":"张孟尧"}
     * sjkList : []
     * kbList : [{"cd_id":"31-0302","cdmc":"31-0302","jc":"1-3节","jcor":"1-3","jcs":"1-3","jgh_id":"20063525","jgpxzd":"1","jxb_id":"337E2586C01DC9ACE050C6DE977E668F","jxbmc":"植物化学保护学-0003","kch_id":"27313588","kcmc":"植物化学保护学","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"肖伟","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"1","xqjmc":"星期一","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"6-10周","zcmc":"副教授","zyfxmc":"无方向"},{"cd_id":"31-0302","cdmc":"31-0302","jc":"1-3节","jcor":"1-3","jcs":"1-3","jgh_id":"20136017","jgpxzd":"1","jxb_id":"337E2586C01DC9ACE050C6DE977E668F","jxbmc":"植物化学保护学-0003","kch_id":"27313588","kcmc":"植物化学保护学","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"申光茂","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"1","xqjmc":"星期一","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"13-18周","zyfxmc":"无方向"},{"cd_id":"31-0302","cdmc":"31-0302","jc":"1-3节","jcor":"1-3","jcs":"1-3","jgh_id":"20063343","jgpxzd":"1","jxb_id":"337E2586C01DC9ACE050C6DE977E668F","jxbmc":"植物化学保护学-0003","kch_id":"27313588","kcmc":"植物化学保护学","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"何林","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"1","xqjmc":"星期一","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"1-2周,4-5周,11-12周","zcmc":"教授","zyfxmc":"无方向"},{"cd_id":"30-0421","cdmc":"30-0421","jc":"8-9节","jcor":"8-10","jcs":"8-9","jgh_id":"20110725","jgpxzd":"1","jxb_id":"36BCCD15973B5E11E050C6DE977E7978","jxbmc":"农业昆虫学-0002C","kch_id":"27313586","kcmc":"农业昆虫学","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"袁国瑞","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"1","xqjmc":"星期一","xqm":"3","xqmc":"南区","xsdm":"02","xslxbj":"◆","zcd":"9-11周,13-14周,16周","zcmc":"讲师（高校）","zyfxmc":"无方向"},{"cd_id":"30-0421","cdmc":"30-0421","jc":"8-10节","jcor":"8-10","jcs":"8-10","jgh_id":"20063339","jgpxzd":"1","jxb_id":"36BCCD15973B5E11E050C6DE977E7978","jxbmc":"农业昆虫学-0002C","kch_id":"27313586","kcmc":"农业昆虫学","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"邓永学","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"1","xqjmc":"星期一","xqm":"3","xqmc":"南区","xsdm":"02","xslxbj":"◆","zcd":"6周","zcmc":"研究员（自然科学）","zyfxmc":"无方向"},{"cd_id":"30-0421","cdmc":"30-0421","jc":"8-10节","jcor":"8-10","jcs":"8-10","jgh_id":"20110725","jgpxzd":"1","jxb_id":"36BCCD15973B5E11E050C6DE977E7978","jxbmc":"农业昆虫学-0002C","kch_id":"27313586","kcmc":"农业昆虫学","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"袁国瑞","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"1","xqjmc":"星期一","xqm":"3","xqmc":"南区","xsdm":"02","xslxbj":"◆","zcd":"8周","zcmc":"讲师（高校）","zyfxmc":"无方向"},{"cd_id":"33-0505","cdmc":"33-0505","jc":"12-14节","jcor":"12-14","jcs":"12-14","jgh_id":"20063339","jgpxzd":"1","jxb_id":"337E2586B3CDC9ACE050C6DE977E668F","jxbmc":"生物安全概论-0001","kch_id":"27320880","kcmc":"生物安全概论","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"邓永学","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"1","xqjmc":"星期一","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"1-12周","zcmc":"研究员（自然科学）","zyfxmc":"无方向"},{"cd_id":"30-0412","cdmc":"30-0412","jc":"8-10节","jcor":"8-10","jcs":"8-10","jgh_id":"20110717","jgpxzd":"1","jxb_id":"3655DB5D79CEFD59E050C6DE977E36D5","jxbmc":"农产品安全-0002G","kch_id":"27321210","kcmc":"农产品安全","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"钱坤","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"2","xqjmc":"星期二","xqm":"3","xqmc":"南区","xsdm":"02","xslxbj":"◆","zcd":"11-12周","zcmc":"副教授","zyfxmc":"无方向"},{"cd_id":"32-0213","cdmc":"32-0213","jc":"4-5节","jcor":"4-5","jcs":"4-5","jgh_id":"20120702","jgpxzd":"1","jxb_id":"337E2586C12AC9ACE050C6DE977E668F","jxbmc":"资源昆虫开发与利用-0002","kch_id":"27323520","kcmc":"资源昆虫开发与利用","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"王佳","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"3","xqjmc":"星期三","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"8-11周","zcmc":"讲师（高校）","zyfxmc":"无方向"},{"cd_id":"32-0213","cdmc":"32-0213","jc":"4-5节","jcor":"4-5","jcs":"4-5","jgh_id":"20063322","jgpxzd":"1","jxb_id":"337E2586C12AC9ACE050C6DE977E668F","jxbmc":"资源昆虫开发与利用-0002","kch_id":"27323520","kcmc":"资源昆虫开发与利用","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"白耀宇","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"3","xqjmc":"星期三","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"1-7周,12-13周","zcmc":"副教授","zyfxmc":"无方向"},{"cd_id":"30-0421","cdmc":"30-0421","jc":"4-5节","jcor":"4-5","jcs":"4-5","jgh_id":"20120702","jgpxzd":"1","jxb_id":"3655D266E0AD621CE050C6DE977E36D9","jxbmc":"资源昆虫开发与利用-0001F","kch_id":"27323520","kcmc":"资源昆虫开发与利用","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"王佳","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"3","xqjmc":"星期三","xqm":"3","xqmc":"南区","xsdm":"02","xslxbj":"◆","zcd":"14-15周","zcmc":"讲师（高校）","zyfxmc":"无方向"},{"cd_id":"30-0412","cdmc":"30-0412","jc":"8-10节","jcor":"8-10","jcs":"8-10","jgh_id":"20063315","jgpxzd":"1","jxb_id":"3638EDA705003CA1E050C6DE977E7285","jxbmc":"植物化学保护学实验-0006","kch_id":"27313589","kcmc":"植物化学保护学实验","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"厉阗","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"3","xqjmc":"星期三","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"6-14周","zcmc":"讲师（高校）","zyfxmc":"无方向"},{"cd_id":"33-0202","cdmc":"33-0202","jc":"4-5节","jcor":"4-6","jcs":"4-5","jgh_id":"20063140","jgpxzd":"1","jxb_id":"34100529479887E9E050C6DE977E3857","jxbmc":"烟草学-0004","kch_id":"26321377","kcmc":"烟草学","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"戴秀梅","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"4","xqjmc":"星期四","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"1-9周","zcmc":"副教授","zyfxmc":"无方向"},{"cd_id":"30-0410","cdmc":"30-0410","jc":"4-6节","jcor":"4-6","jcs":"4-6","jgh_id":"20140502","jgpxzd":"1","jxb_id":"3410E2689CF308A6E050C6DE977E0495","jxbmc":"农业植物病理学-0002C","kch_id":"27313587","kcmc":"农业植物病理学","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"吴根土","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"4","xqjmc":"星期四","xqm":"3","xqmc":"南区","xsdm":"02","xslxbj":"◆","zcd":"10-15周","zcmc":"讲师（高校）","zyfxmc":"无方向"},{"cd_id":"33-0305","cdmc":"33-0305","jc":"12-14节","jcor":"12-14","jcs":"12-14","jgh_id":"20110717","jgpxzd":"1","jxb_id":"337E2586C1C6C9ACE050C6DE977E668F","jxbmc":"农产品安全-0001","kch_id":"27321210","kcmc":"农产品安全","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"钱坤","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"5","xqjmc":"星期五","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"1-3周(单),4-10周","zcmc":"副教授","zyfxmc":"无方向"},{"cd_id":"33-0305","cdmc":"33-0305","jc":"8-10节","jcor":"8-10","jcs":"8-10","jgh_id":"20110717","jgpxzd":"1","jxb_id":"337E2586C1C6C9ACE050C6DE977E668F","jxbmc":"农产品安全-0001","kch_id":"27321210","kcmc":"农产品安全","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"钱坤","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"6","xqjmc":"星期六","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"3周","zcmc":"副教授","zyfxmc":"无方向"},{"cd_id":"35-0314","cdmc":"35-0314","jc":"8-10节","jcor":"8-10","jcs":"8-10","jgh_id":"20062461","jgpxzd":"1","jxb_id":"34485A5BEA6E7E4AE050C6DE977E5881","jxbmc":"资源微生物开发与利用-0001A","kch_id":"27323599","kcmc":"资源微生物开发与利用","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"代先祝","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"7","xqjmc":"星期日","xqm":"3","xqmc":"南区","xsdm":"02","xslxbj":"◆","zcd":"3-7周","zcmc":"副教授","zyfxmc":"无方向"},{"cd_id":"32-0213","cdmc":"32-0213","jc":"12-14节","jcor":"12-14","jcs":"12-14","jgh_id":"20062461","jgpxzd":"1","jxb_id":"34485A5BEA6C7E4AE050C6DE977E5881","jxbmc":"资源微生物开发与利用-0001","kch_id":"27323599","kcmc":"资源微生物开发与利用","listnav":"false","localeKey":"zh_CN","pageable":true,"pkbj":"1","queryModel":{"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0},"rangeable":true,"rsdzjs":0,"sxbj":"1","totalResult":"0","xm":"代先祝","xnm":"2016","xqdm":"0","xqh_id":"1","xqj":"7","xqjmc":"星期日","xqm":"3","xqmc":"南区","xsdm":"01","xslxbj":"◇","zcd":"1-7周","zcmc":"副教授","zyfxmc":"无方向"}]
     * xkkg : true
     * xqjmcMap : {"1":"星期一","2":"星期二","3":"星期三","4":"星期四","5":"星期五","6":"星期六","7":"星期日"}
     */

    private int kblx;
    /**
     * XH_ID : 222014327012166
     * XQMMC : 1
     * XNM : 2016
     * XH : 222014327012166
     * XKKG : 1
     * XKKGXQ : 1~('3')
     * KXKXXQ : ('3')
     * XNMC : 2016-2017
     * XQM : 3
     * XM : 张孟尧
     */

    private XsxxBean xsxx;
    private boolean xkkg;
    /**
     * 1 : 星期一
     * 2 : 星期二
     * 3 : 星期三
     * 4 : 星期四
     * 5 : 星期五
     * 6 : 星期六
     * 7 : 星期日
     */

    private XqjmcMapBean xqjmcMap;
    private List<?> sjkList;
    /**
     * cd_id : 31-0302
     * cdmc : 31-0302
     * jc : 1-3节
     * jcor : 1-3
     * jcs : 1-3
     * jgh_id : 20063525
     * jgpxzd : 1
     * jxb_id : 337E2586C01DC9ACE050C6DE977E668F
     * jxbmc : 植物化学保护学-0003
     * kch_id : 27313588
     * kcmc : 植物化学保护学
     * listnav : false
     * localeKey : zh_CN
     * pageable : true
     * pkbj : 1
     * queryModel : {"currentPage":1,"currentResult":0,"entityOrField":false,"showCount":10,"totalPage":0,"totalResult":0}
     * rangeable : true
     * rsdzjs : 0
     * sxbj : 1
     * totalResult : 0
     * xm : 肖伟
     * xnm : 2016
     * xqdm : 0
     * xqh_id : 1
     * xqj : 1
     * xqjmc : 星期一
     * xqm : 3
     * xqmc : 南区
     * xsdm : 01
     * xslxbj : ◇
     * zcd : 6-10周
     * zcmc : 副教授
     * zyfxmc : 无方向
     */

    private List<KbListBean> kbList;

    public int getKblx() {
        return kblx;
    }

    public void setKblx(int kblx) {
        this.kblx = kblx;
    }

    public XsxxBean getXsxx() {
        return xsxx;
    }

    public void setXsxx(XsxxBean xsxx) {
        this.xsxx = xsxx;
    }

    public boolean isXkkg() {
        return xkkg;
    }

    public void setXkkg(boolean xkkg) {
        this.xkkg = xkkg;
    }

    public XqjmcMapBean getXqjmcMap() {
        return xqjmcMap;
    }

    public void setXqjmcMap(XqjmcMapBean xqjmcMap) {
        this.xqjmcMap = xqjmcMap;
    }

    public List<?> getSjkList() {
        return sjkList;
    }

    public void setSjkList(List<?> sjkList) {
        this.sjkList = sjkList;
    }

    public List<KbListBean> getKbList() {
        return kbList;
    }

    public void setKbList(List<KbListBean> kbList) {
        this.kbList = kbList;
    }

    public static class XsxxBean {
        private String XH_ID;
        private String XQMMC;
        private String XNM;
        private String XH;
        private String XKKG;
        private String XKKGXQ;
        private String KXKXXQ;
        private String XNMC;
        private String XQM;
        private String XM;

        public String getXH_ID() {
            return XH_ID;
        }

        public void setXH_ID(String XH_ID) {
            this.XH_ID = XH_ID;
        }

        public String getXQMMC() {
            return XQMMC;
        }

        public void setXQMMC(String XQMMC) {
            this.XQMMC = XQMMC;
        }

        public String getXNM() {
            return XNM;
        }

        public void setXNM(String XNM) {
            this.XNM = XNM;
        }

        public String getXH() {
            return XH;
        }

        public void setXH(String XH) {
            this.XH = XH;
        }

        public String getXKKG() {
            return XKKG;
        }

        public void setXKKG(String XKKG) {
            this.XKKG = XKKG;
        }

        public String getXKKGXQ() {
            return XKKGXQ;
        }

        public void setXKKGXQ(String XKKGXQ) {
            this.XKKGXQ = XKKGXQ;
        }

        public String getKXKXXQ() {
            return KXKXXQ;
        }

        public void setKXKXXQ(String KXKXXQ) {
            this.KXKXXQ = KXKXXQ;
        }

        public String getXNMC() {
            return XNMC;
        }

        public void setXNMC(String XNMC) {
            this.XNMC = XNMC;
        }

        public String getXQM() {
            return XQM;
        }

        public void setXQM(String XQM) {
            this.XQM = XQM;
        }

        public String getXM() {
            return XM;
        }

        public void setXM(String XM) {
            this.XM = XM;
        }
    }

    public static class XqjmcMapBean {
        @SerializedName("1")
        private String value1;
        @SerializedName("2")
        private String value2;
        @SerializedName("3")
        private String value3;
        @SerializedName("4")
        private String value4;
        @SerializedName("5")
        private String value5;
        @SerializedName("6")
        private String value6;
        @SerializedName("7")
        private String value7;

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }

        public String getValue3() {
            return value3;
        }

        public void setValue3(String value3) {
            this.value3 = value3;
        }

        public String getValue4() {
            return value4;
        }

        public void setValue4(String value4) {
            this.value4 = value4;
        }

        public String getValue5() {
            return value5;
        }

        public void setValue5(String value5) {
            this.value5 = value5;
        }

        public String getValue6() {
            return value6;
        }

        public void setValue6(String value6) {
            this.value6 = value6;
        }

        public String getValue7() {
            return value7;
        }

        public void setValue7(String value7) {
            this.value7 = value7;
        }
    }

    public static class KbListBean {
        private String cd_id;
        private String cdmc;
        private String jc;
        private String jcor;
        private String jcs;
        private String jgh_id;
        private String jgpxzd;
        private String jxb_id;
        private String jxbmc;
        private String kch_id;
        private String kcmc;
        private String listnav;
        private String localeKey;
        private boolean pageable;
        private String pkbj;
        /**
         * currentPage : 1
         * currentResult : 0
         * entityOrField : false
         * showCount : 10
         * totalPage : 0
         * totalResult : 0
         */

        private QueryModelBean queryModel;
        private boolean rangeable;
        private int rsdzjs;
        private String sxbj;
        private String totalResult;
        private String xm;
        private String xnm;
        private String xqdm;
        private String xqh_id;
        private String xqj;
        private String xqjmc;
        private String xqm;
        private String xqmc;
        private String xsdm;
        private String xslxbj;
        private String zcd;
        private String zcmc;
        private String zyfxmc;

        public String getCd_id() {
            return cd_id;
        }

        public void setCd_id(String cd_id) {
            this.cd_id = cd_id;
        }

        public String getCdmc() {
            return cdmc;
        }

        public void setCdmc(String cdmc) {
            this.cdmc = cdmc;
        }

        public String getJc() {
            return jc;
        }

        public void setJc(String jc) {
            this.jc = jc;
        }

        public String getJcor() {
            return jcor;
        }

        public void setJcor(String jcor) {
            this.jcor = jcor;
        }

        public String getJcs() {
            return jcs;
        }

        public void setJcs(String jcs) {
            this.jcs = jcs;
        }

        public String getJgh_id() {
            return jgh_id;
        }

        public void setJgh_id(String jgh_id) {
            this.jgh_id = jgh_id;
        }

        public String getJgpxzd() {
            return jgpxzd;
        }

        public void setJgpxzd(String jgpxzd) {
            this.jgpxzd = jgpxzd;
        }

        public String getJxb_id() {
            return jxb_id;
        }

        public void setJxb_id(String jxb_id) {
            this.jxb_id = jxb_id;
        }

        public String getJxbmc() {
            return jxbmc;
        }

        public void setJxbmc(String jxbmc) {
            this.jxbmc = jxbmc;
        }

        public String getKch_id() {
            return kch_id;
        }

        public void setKch_id(String kch_id) {
            this.kch_id = kch_id;
        }

        public String getKcmc() {
            return kcmc;
        }

        public void setKcmc(String kcmc) {
            this.kcmc = kcmc;
        }

        public String getListnav() {
            return listnav;
        }

        public void setListnav(String listnav) {
            this.listnav = listnav;
        }

        public String getLocaleKey() {
            return localeKey;
        }

        public void setLocaleKey(String localeKey) {
            this.localeKey = localeKey;
        }

        public boolean isPageable() {
            return pageable;
        }

        public void setPageable(boolean pageable) {
            this.pageable = pageable;
        }

        public String getPkbj() {
            return pkbj;
        }

        public void setPkbj(String pkbj) {
            this.pkbj = pkbj;
        }

        public QueryModelBean getQueryModel() {
            return queryModel;
        }

        public void setQueryModel(QueryModelBean queryModel) {
            this.queryModel = queryModel;
        }

        public boolean isRangeable() {
            return rangeable;
        }

        public void setRangeable(boolean rangeable) {
            this.rangeable = rangeable;
        }

        public int getRsdzjs() {
            return rsdzjs;
        }

        public void setRsdzjs(int rsdzjs) {
            this.rsdzjs = rsdzjs;
        }

        public String getSxbj() {
            return sxbj;
        }

        public void setSxbj(String sxbj) {
            this.sxbj = sxbj;
        }

        public String getTotalResult() {
            return totalResult;
        }

        public void setTotalResult(String totalResult) {
            this.totalResult = totalResult;
        }

        public String getXm() {
            return xm;
        }

        public void setXm(String xm) {
            this.xm = xm;
        }

        public String getXnm() {
            return xnm;
        }

        public void setXnm(String xnm) {
            this.xnm = xnm;
        }

        public String getXqdm() {
            return xqdm;
        }

        public void setXqdm(String xqdm) {
            this.xqdm = xqdm;
        }

        public String getXqh_id() {
            return xqh_id;
        }

        public void setXqh_id(String xqh_id) {
            this.xqh_id = xqh_id;
        }

        public String getXqj() {
            return xqj;
        }

        public void setXqj(String xqj) {
            this.xqj = xqj;
        }

        public String getXqjmc() {
            return xqjmc;
        }

        public void setXqjmc(String xqjmc) {
            this.xqjmc = xqjmc;
        }

        public String getXqm() {
            return xqm;
        }

        public void setXqm(String xqm) {
            this.xqm = xqm;
        }

        public String getXqmc() {
            return xqmc;
        }

        public void setXqmc(String xqmc) {
            this.xqmc = xqmc;
        }

        public String getXsdm() {
            return xsdm;
        }

        public void setXsdm(String xsdm) {
            this.xsdm = xsdm;
        }

        public String getXslxbj() {
            return xslxbj;
        }

        public void setXslxbj(String xslxbj) {
            this.xslxbj = xslxbj;
        }

        public String getZcd() {
            return zcd;
        }

        public void setZcd(String zcd) {
            this.zcd = zcd;
        }

        public String getZcmc() {
            return zcmc;
        }

        public void setZcmc(String zcmc) {
            this.zcmc = zcmc;
        }

        public String getZyfxmc() {
            return zyfxmc;
        }

        public void setZyfxmc(String zyfxmc) {
            this.zyfxmc = zyfxmc;
        }

        public static class QueryModelBean {
            private int currentPage;
            private int currentResult;
            private boolean entityOrField;
            private int showCount;
            private int totalPage;
            private int totalResult;

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getCurrentResult() {
                return currentResult;
            }

            public void setCurrentResult(int currentResult) {
                this.currentResult = currentResult;
            }

            public boolean isEntityOrField() {
                return entityOrField;
            }

            public void setEntityOrField(boolean entityOrField) {
                this.entityOrField = entityOrField;
            }

            public int getShowCount() {
                return showCount;
            }

            public void setShowCount(int showCount) {
                this.showCount = showCount;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public int getTotalResult() {
                return totalResult;
            }

            public void setTotalResult(int totalResult) {
                this.totalResult = totalResult;
            }
        }
    }
}
