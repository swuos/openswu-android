package com.swuos.ALLFragment.library.libsearchs.search.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/10/25.
 */
public interface LibSearch {
    /**
     * Search book observable. 返回搜索指定图书返回的网页
     *
     * @param query the query 参数如下.比如搜索三体,应该在querymap里填入以下参数.其中pager.offset为偏移量.0为第一页,10为第二页,依次类推              q	三体              flword	三体              searchType	oneSearch              field	title              searchModel	seg              oneSearchWord	title:三体              twoSearchWord              advSearchWold              combineSearchWold              exactSearch	yes              corename              gcbook	yes              pager.offset	0
     * @return the observable
     */
    @Headers({"Connection: keep-alive" ,
            "Upgrade-Insecure-Requests: 1" ,
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.113 Safari/537.36" ,
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" ,
            "DNT: 1"})
    @GET("http://202.202.121.3:99/gold/opac/search?")
    Observable<String> searchBook(@QueryMap Map<String, String> query);

    /**
     * Gets book location. 返回书定位信息
     *
     * @param id   the id 书的id
     * @param time the time 搜索时间 unix时间戳
     * @return the book location 返回的是json数据
     */
    @GET("http://202.202.121.3:99/gold/opac/book/getHoldingsInformation/{id}?_={time}")
    Observable<String> getBookLocation(@Path("id") String id, @Path("time") long time);

    /**
     * Gets bookstate num. 返回这本书的剩余状态
     *
     * @param id   the id 书的id
     * @param time the time unix时间戳
     * @return the bookstate num
     */
    @GET("http://202.202.121.3:99/gold/opac/book/getBookState/{id}/%E5%9B%BE%E4%B9%A6?")
    Observable<String> getBookstateNum(@Path("id") String id, @Query("_") long time);
}
