package com.wills.help.login.model;

import com.wills.help.net.Empty;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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
    @FormUrlEncoded
    Observable<Empty> submitRegister(@FieldMap Map<String , String > map);

}
