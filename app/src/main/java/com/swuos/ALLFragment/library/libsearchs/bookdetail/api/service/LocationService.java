package com.swuos.ALLFragment.library.libsearchs.bookdetail.api.service;

import com.swuos.ALLFragment.library.libsearchs.bookdetail.model.BookLocationItem;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by : youngkaaa on 2016/11/5.
 * Contact me : 645326280@qq.com
 */

public interface LocationService {
    @GET("http://202.202.121.3:99/gold/opac/book/getHoldingsInformation/{id}")
    Observable<List<BookLocationItem>> getBookLocation(@Path("id") String id, @Query("_") long time);
}
