package com.wills.help.net;

import com.wills.help.home.model.Banner;
import com.wills.help.home.model.Express;
import com.wills.help.home.model.News;
import com.wills.help.login.model.OrderType;
import com.wills.help.login.model.Point;
import com.wills.help.login.model.User;
import com.wills.help.release.model.Release;
import com.wills.help.release.model.ReleaseList;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 数据接口
 * com.wills.help.net
 * Created by lizhaoyong
 * 2017/1/9.
 */

public interface ApiInterface {
    /**
     * 位置点
     * @return
     */
    @GET("poslist")
    Observable<Point> getPoint();

    /**
     * 发布求助类型
     * @return
     */
    @GET("ordertypelist")
    Observable<OrderType> getOrderType();

    /**
     * 登录
     * @param map
     * @return
     */
    @POST("login")
    @FormUrlEncoded
    Observable<User> login(@FieldMap Map<String , String> map);

    /**
     * 注册获取验证码
     * @param phone
     * @return
     */
    @POST("register")
    @FormUrlEncoded
    Observable<Empty> getRegisterCode(@Field("username") String phone);

    /**
     * 注册
     * @param map
     * @return
     */
    @POST("registercheck")
    @FormUrlEncoded
    Observable<User> submitRegister(@FieldMap Map<String , String > map);

    /**
     * 重置密码获取验证码
     * @param phone
     * @return
     */
    @POST("forget")
    @FormUrlEncoded
    Observable<Empty> getResetCode(@Field("username") String phone);

    /**
     * 重置密码提交
     * @param map
     * @return
     */
    @POST("forgetcheck")
    @FormUrlEncoded
    Observable<Empty> reset(@FieldMap Map<String , String > map);

    /**
     * 快递
     * @param map
     * @return
     */
    @POST("deliverylist")
    @FormUrlEncoded
    Observable<Express> getExpress(@FieldMap Map<String , String > map);

    /**
     * banner
     * @return
     */
    @GET("bannerlist")
    Observable<Banner> getBanner();

    /**
     * 新闻资讯
     * @return
     */
    @GET("newslist")
    Observable<News> getNews();

    /**
     * 发布
     * @param map
     * @return
     */
    @POST("release")
    @FormUrlEncoded
    Observable<Release> release(@FieldMap Map<String , String > map);


    @POST("getorder")
    @FormUrlEncoded
    Observable<ReleaseList> getReleaseList(@FieldMap Map<String , String > map);
}
