package com.wills.help.login.model;

import retrofit2.http.GET;
import rx.Observable;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public interface ConfigModel {

    @GET("poslist")
    Observable<Point> getPoint();

    @GET("ordertypelist")
    Observable<OrderType> getOrderType();
}
