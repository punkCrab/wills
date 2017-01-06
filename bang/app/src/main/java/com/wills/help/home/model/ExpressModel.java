package com.wills.help.home.model;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public interface ExpressModel {
    @POST("deliverylist")
    @FormUrlEncoded
    Observable<Express> getExpress(@FieldMap Map<String , String > map);
}
