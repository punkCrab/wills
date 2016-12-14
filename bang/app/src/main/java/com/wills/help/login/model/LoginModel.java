package com.wills.help.login.model;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * com.lzy.mmd.mvptest.model
 * Created by lizhaoyong
 * 2016/10/20.
 */

public interface LoginModel {

    @POST("login")
    @FormUrlEncoded
    Observable<User> login(@FieldMap Map<String , String> map);

}
