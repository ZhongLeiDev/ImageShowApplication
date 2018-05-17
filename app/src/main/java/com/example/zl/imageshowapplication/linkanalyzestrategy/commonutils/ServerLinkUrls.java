package com.example.zl.imageshowapplication.linkanalyzestrategy.commonutils;

/**
 * Created by ZhongLeiDev on 2018/05/17
 * 所有的带解析的资源入口连接地址
 */
public class ServerLinkUrls {

    /**Bcy热门推荐*/
    public static final String BCY_COSER = "https://bcy.net/coser";

    /**Bcy本周热门*/
    public static final String BCY_TOPPOST100 = "https://bcy.net/coser/toppost100";

    /**Bcy查询某一日的本周热门，后面添加日期，如【20180506】*/
    public static final String BCY_WHICHDAY_TOPPOST100 = "https://bcy.net/coser/toppost100?type=week&date=";

    /**Bcy今日热门*/
    public static final String BCY_TODAYTOP = "https://bcy.net/coser/toppost100?type=lastday";

    /**Bcy查询某一日的今日热门，后面添加日期，如【20180507】*/
    public static final String BCY_WHICHDAY_TODAYTOP = "https://bcy.net/coser/toppost100?type=lastday&date=";

    /**Bcy新人榜*/
    public static final String BCY_NEWPEOPLE = "https://bcy.net/coser/toppost100?type=newPeople";

    /**Bcy查询某一日的新人榜，后面添加日期，如【20180507】*/
    public static final String BCY_WHICHDAY_NEWPEOPLE ="https://bcy.net/coser/toppost100?type=newPeople&date=";

    /**Bcy最新正片*/
    public static final String BCY_ALLWORKS = "https://bcy.net/coser/allwork";

    /**Bcy最新正片第几页，后面接查询的页数，如第二页就接【2】*/
    public static final String BCY_WHICHPAGE_ALLWORKS = "https://bcy.net/coser/allwork?&p=";

}
