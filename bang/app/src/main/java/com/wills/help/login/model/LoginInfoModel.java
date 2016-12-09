package com.wills.help.login.model;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * com.lzy.mmd.mvptest.model
 * Created by lizhaoyong
 * 2016/10/20.
 */

public interface LoginInfoModel {

//    @GET("contributors")
//    Observable<LoginInfo> net_getLoginInfo(@QueryMap Map<String, String> map);
    @GET("users/{user}")
    Observable<LoginInfo> net_getLoginInfo(@Path("user") String user);

}
