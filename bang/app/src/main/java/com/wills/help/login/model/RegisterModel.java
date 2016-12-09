package com.wills.help.login.model;

import com.wills.help.net.Empty;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2016/12/9.
 */

public interface RegisterModel {

    @POST("register")
    @FormUrlEncoded
    Observable<Empty> getCode(@Field("username") String phone);

    @POST("registercheck")
    Observable<Empty> submitRegister(@QueryMap Map<String , String > map);

}
