package com.swuos.swuassistant;

/**
 * Created by 张孟尧 on 2016/1/10.
 */
public class Constant {
    public static final long TIMEOUT = 8 * 1000;
    public static final long LIBTIMEOUT = 20 * 1000;

    //    设置Message标记
    public static final int SHOW_RESPONSE = 0;
    public static final int ERROR = 1;
    public static final int DELETE = 2;

    public static final int LOGIN_SUCCESE = 3;
    public static final int LOGIN_FAILED = 4;
    public static final int LOGIN_TIMEOUT = 5;
    public static final int LOGIN_CLIENT_ERROR = 6;
    public static final int LOGIN_NO_NET = 7;
    public static final int SCHOOL_SERVER_BOOM = 8;
    public static final boolean DEBUG = true;


    public static final int GRADES_OK = 9;
    public static final int UPDATA = 10;
    public static final int MAIN = 11;
    public static final int SHOW = 12;
    public static final int DISSHOW = 13;
    public static final int SCHEDULE_OK = 14;
    public static final int SCHEDULE__LOADING = 15;
    public static final int SHOW_NOTIFYCATION = 16;
    public static final long DEFAULHEADWAY = 1000 * 60;
    public static final long DEFAULTIME = 1000 * 60;//60秒

    public static final long ONE_CLASS_TIME = 1000 * 60 * 40;
    public static final long ONE_WEEK_TIME = 1000 * 60 * 60 * 24 * 7; //一天的秒数
    public static final long ONE_DAY_TIME = 1000 * 60 * 60 * 24; //一天的秒数
    public static final long ONE_HOUR_TIME = 1000 * 60 * 60; //一小时的毫毫秒秒数
    public static final long ONE_MIN_TIME = 1000 * 60; //一分钟的毫秒秒数
    public static final int LOGIN_RESULT_CODE = 17;
    public static final int EMPTY = 18;
    public static final int XNMPOSITION = 3;
    public static final int XQMPOSITION = 1;


    public static final String NO_NET = "网络出现了问题";
    public static final String CLIENT_OK = "成功";
    public static final String CLIENT_ERROR = "连接出错";
    public static final String CLIENT_TIMEOUT = "连接超时";
    public static final String USERNAMEORPSWERRR = "用户名或密码错误";


    public static final String XQM_ONE = "3";
    public static final String XQM_TWO = "12";
    public static final String XQM_THREE = "16";
    public static final String XQM_ALL = "";
    public static final String[] ALL_XQM = {XQM_ALL, XQM_ONE, XQM_TWO, XQM_THREE};
    public static final String[] ALL_XNM = {"", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010"};
    public static final String NET_TIMEOUT = "NET_TIMEOUT";
    public static final String[] FRAGMENTTAG = {"mainPageFragment", "scheduleFragment", "gradesFragment", "studyMaterialsFragment", "findLostFragment", "chargeFragment", "libraryFragrment", "WifiFragment", "ecardFragment"};
    //    校内门户地址
    public static final String urlUrp = "http://urp6.swu.edu.cn/login.portal";
    public static final String urlRedrictjw = "https://uaaap.swu.edu.cn/cas/login?service=http%3A%2F%2Fjw.swu.edu.cn%2Fssoserver%2Flogin%3Fywxt%3Djw";
    //    用户信息发送目标地址
    //    public static final String urlLogin = "http://urp6.swu.edu.cn/userPasswordValidate.portal";
    public static final String urlLogin = "http://i.swu.edu.cn/remote/service/process";

    //    登陆后跳转网页
    public static final String urlPortal = "http://urp6.swu.edu.cn/index.portal";
    //    #教务系统网站 Ems意为swu Educational management system
    public static final String urlEms = "http://jw.swu.edu.cn/jwglxt/idstar/index.jsp";
    public static final String urlJW = "http://jw.swu.edu.cn/jwglxt/xtgl/index_initMenu.html";
    /*登陆校内门户是post的两个重要参数*/
    public static final String gotos = "http://urp6.swu.edu.cn/loginSuccess.portal";
    public static final String gotoOnFail = "http://urp6.swu.edu.cn/loginFailure.portal";

    //登陆图书馆的地址
    public static final String loginLibrary = "http://www.lib.swu.edu.cn/webs/user_loginLib.action";
    //登录我的图书馆的地址
    public static final String loginMyLibrary = "http://mylib.swu.edu.cn/do.jsp?ac=c64db6a3accfd5d750e9b26c1ad46d76&ref";
    //我的图书馆主页
    public static final String libraryIndexUri = "http://mylib.swu.edu.cn/space.jsp?do=home";
    //我的书架
    public static final String libraryBorrowUri = "http://mylib.swu.edu.cn/borry.jsp";
    //我的借阅历史
    public static final String libraryBorrowHistoryUri = "http://mylib.swu.edu.cn/borry.jsp?ac=userHistory";
    //我的借阅信息
    public static final String libraryBorrowInfo = "http://mylib.swu.edu.cn/borry.jsp?ac=borryInofor";

    public static final String urlSwuDormWifi = "http://222.198.120.8:8080/loginPhoneServlet";
    public static final String urlSwuWifi = "http://202.202.96.57:9060/login/login1.jsp";
    public static final String urlWifiLogout = "http://service.swu.edu.cn/fee/remote_logout2.jsp";

    public static final String swuWifiLoginSuccessed = "您已通过登录审核";
    public static final String swuWifiLoginNameOrPasswordError = "密码错误";
    public static final String swuWifiLoginAnotherDeviceLogined = "账号已在其他计算机上登录";
    public static final String swuWifiLoginSomeThError = "未知错误";

    public static final String swuWifiLogoutPasswordError = "密码输入有误";
    public static final String swuWifiLogoutSuccessed = "成功退出";
    public static final String swuWifiLogoutNoLogined = "没有登录";

    public static final String dormWifiLoginSuccessed = "登录成功";
    public static final String dormWifiAnotherDeviceLoginedE = "You are already logged in";
    public static final String dormWifiAnotherDeviceLoginedC = "账号已经在其他地方登录,请退出";
    public static final String dormWifiLoginReject = "请求被拒绝";
    public static final String dormWifiLoginShortE = "short";
    public static final String dormWifiLoginShortC = "登陆频繁,请稍后重试";
    public static final String dormWifiLoginEmptyE = "不能为空";
    public static final String dormWifiLoginEmptyC = "用户名不能为空";
    public static final String dormWifiLoginPasswordWrongE = "Password check failed";
    public static final String dormWifiLoginPasswordWrongC = "密码错误";
    public static final String dormWifiLoginNotExistE = "exist";
    public static final String dormWifiLoginNotExistC = "用户名不存在";
    public static final String dormWifiLoginSomeTHError = "未知错误";
    public static final String noWifi = "请连接swu-dorm-wifi或者swu-wifi后尝试登录";

    public static final String WIDGET_LOGIN = "WIDGET_LOGIN";
    public static final String WIDGET_LOGOUT = "WIDGET_LOGOUT";
    public static final String WIDGET_LOGINFO = "WIDGET_LOGINFO";

    public static final String NOTIFICATION_LOGIN = "NOTIFICATION_LOGIN";
    public static final String NOTIFICATION_LOGOUT = "NOTIFICATION_LOGOUT";
    public static final int NOTIFICATION_WIFI_ID = 100001;


    //内网服务器反馈地址
    public static final String urlReportIssue = "http://opensource.desu.pub/openswu/reportIssue";
    //内网定时退出网络地址
    public static final String urlQuitnet = "http://opensource.desu.pub/openswu/quitnet";



    public static final int[] background = {R.color.colorclass1, R.color.colorclass2, R.color.colorclass3, R.color.colorclass4, R.color.colorclass5, R.color.colorclass6};
    public static final String[] SCHEDULE_WEEK_TITLE = {"整学期", "第一周", "第二周", "第三周", "第四周", "第五周", "第六周", "第七周", "第八周", "第九周", "第十周", "第十一周", "第十二周", "第十三周", "第十四周", "第十五周", "第十六周", "第十七周", "第十八周", "第十九周", "第二十周"};
    public static final String[] STARTIME = {"8:00", "8:50", "9:40", "10:40", "11:30", "12:30", "13:50", "14:30", "15:20", "16:10", "17:00", "19:00", "19:50", "20:40"};
    public static final int[] STARTtIMEHOUR = {8, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 19, 20};
    /*上课时间*/
    public static final int[] STARTtIMES = {8 * 60, 8 * 60 + 50, 9 * 60 + 40, 10 * 60 + 40, 11 * 60 + 30, 12 * 60 + 30, 13 * 60 + 20, 14 * 60 + 30, 15 * 60 + 20, 16 * 60 + 10, 17 * 60, 19 * 60, 19 * 60 + 50, 20 * 60 + 40};

    public static final int[] STARTtIMEMIN = {0, 50, 40, 40, 30, 30, 50, 30, 20, 10, 0, 0, 50, 40};
    public static final int REQUEST_CODE_ASK_CALL_PHONE = 123;

    public static final String TIMING_SUCCESS = "定时退网排队成功";
    public static final String TIMING_USER_ERROR = "用户名或密码错误。";
    public static final String TIMING_USER_NOLOGIN = "您的账户当前没有登陆。";
    public static final int[] BUILDING = {100022, 100042, 100041, 100040, 100038, 100039, 100043,
            100023, 100030, 100031, 100032, 100033, 100034,
            100024, 100025, 100026, 100027, 100028, 100029,
            100001, 100002, 100003, 100004, 100005, 100006,
            100021, 100054, 100055, 100056,
            100057, 100058, 100059, 100060, 100061,100063,
            100044, 100045, 100046, 100047, 100048, 100049, 100050, 100062, 100052, 100053};
    // 优秀
    public static final String A = "252BD170CD67B480E050C6DE977E4AD2";
    // 良好;
    public static final String B = "252BD170CD68B480E050C6DE977E4AD2";
    // 中;
    public static final String C = "252BD170CD69B480E050C6DE977E4AD2";
    // 合格;
    public static final String D = "252BD170CD6AB480E050C6DE977E4AD2";
    // 不合格;
    public static final String E = "252BD170CD6BB480E050C6DE977E4AD2";
    public static final String pfdjdmb_id = "19E17A2CBFD3BC83E050007F01005F6D";
    // modelList[0].pjmbmcb_id;
    public static final String pjmbmcb_id = "41678A818DA2F101E050C6DE977E5279";
    // modelList[0].xspfb_id;
    public static final String xspfb_id = "43332D2E730847F4E050C6DE977E517F";
    public static final String F = "41678A818DA3F101E050C6DE977E5279";
    // 能根据教学实际科学、熟练利用各种教学媒体辅助教学，使用方法恰当，效果明显;
    public static final String F1 = "41678A818DA7F101E050C6DE977E5279";
    // 板书设计合理、工整，层次清晰。;
    public static final String F2 = "41678A818DA8F101E050C6DE977E5279";
    // 普通话标准。;
    public static final String F3 = "41678A818DA9F101E050C6DE977E5279";
    // 因材施教，注重启发学生；能适时、恰当地指导学生。;
    public static final String F4 = "41678A818DAAF101E050C6DE977E5279";
    // 教学内容;
    public static final String G = "41678A818DA4F101E050C6DE977E5279";
    // 教学内容充实，信息量大；新知识与新思路兼备。;
    public static final String G1 = "41678A818DABF101E050C6DE977E5279";
    // 基本概念、理论讲授正确，重点、难点突出，解析透彻。;
    public static final String G2 = "41678A818DACF101E050C6DE977E5279";
    // 教学目的明确，课堂讲授内容与教学大纲要求相吻合;
    public static final String G3 = "41678A818DADF101E050C6DE977E5279";
    // 讲课理论联系实际，例证恰当、贴切、生动，问题设计合理;
    public static final String G4 = "41678A818DAEF101E050C6DE977E5279";
    // 教学内容没有政治性错误;
    public static final String G5 = "41678A818DAFF101E050C6DE977E5279";
    // 教学内容没有科学性错误。;
    public static final String G6 = "41678A818DB0F101E050C6DE977E5279";
    public static final String H = "41678A818DA5F101E050C6DE977E5279";
    // 教学态度端正，仪态得体；情绪饱满，富有感染力;
    public static final String H1 = "41678A818DB1F101E050C6DE977E5279";
    // 作业批改及时、认真，辅导耐心。;
    public static final String H2 = "41678A818DB2F101E050C6DE977E5279";
    // 教学效果;
    public static final String I = "41678A818DA6F101E050C6DE977E5279";
    // 使学生自学能力、分析与解决问题的能力得到提高。;
    public static final String I1 = "41678A818DB3F101E050C6DE977E5279";
    // 使学生较好地掌握了授课内容；提高了学生对该课程的学习兴趣。;
    public static final String I2 = "41678A818DB4F101E050C6DE977E5279";
    // 课堂气氛活跃，学生参与积极。;
    public static final String I3 = "41678A818DB5F101E050C6DE977E5279";
}

