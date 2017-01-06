package com.wills.help.home.model;

import retrofit2.http.GET;
import rx.Observable;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public interface HomeModel {

    @GET("bannerlist")
    Observable<Banner> getBanner();

    @GET("newslist")
    Observable<News> getNews();
}
