package com.wills.help.net;

import com.wills.help.home.model.Banner;
import com.wills.help.home.model.Express;
import com.wills.help.home.model.News;
import com.wills.help.login.model.OrderType;
import com.wills.help.login.model.Point;
import com.wills.help.login.model.User;
import com.wills.help.message.model.Contacts;
import com.wills.help.person.model.Avatar;
import com.wills.help.person.model.Wallet;
import com.wills.help.release.model.Appraise;
import com.wills.help.release.model.OrderDetail;
import com.wills.help.release.model.Release;
import com.wills.help.release.model.OrderList;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
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
     * Banner.java
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

    /**
     * 发布
     * @param map
     * @return
     */
    @POST("updateorder")
    @FormUrlEncoded
    Observable<Empty> updateOrder(@FieldMap Map<String , String > map);

    /**
     * 获取订单列表
     * @param map
     * @return
     */
    @POST("getorder")
    @FormUrlEncoded
    Observable<OrderList> getReleaseList(@FieldMap Map<String , String > map);

    /**
     * 获取订单详情
     * @param map
     * @return
     */
    @POST("getorderbyid")
    @FormUrlEncoded
    Observable<OrderDetail> getOrderInfo(@FieldMap Map<String , String > map);

    /**
     * 获取订单详情
     * @param map
     * @return
     */
    @POST("setuserinfo")
    @FormUrlEncoded
    Observable<Empty> setUserInfo(@FieldMap Map<String , String > map);

    /**
     * 上传头像
     * @param avatar
     * @param username
     * @return
     */
    @Multipart
    @POST("setavatar")
    Observable<Avatar> setAvatar(@Part MultipartBody.Part avatar, @Part("userid") RequestBody username);

    /**
     * 接单
     * @param map
     * @return
     */
    @POST("accept")
    @FormUrlEncoded
    Observable<Empty> accept(@FieldMap Map<String , String > map);

    /**
     * 接单列表
     * @param map
     * @return
     */
    @GET("getmaporder")
    Observable<OrderList> getAssistList(@QueryMap Map<String , String > map);

    /**
     * 我的钱包
     * @param map
     * @return
     */
    @POST("getmymoney")
    @FormUrlEncoded
    Observable<Wallet> getMoney(@FieldMap Map<String , String > map);

    /**
     * 获取评价标签
     * @return
     */
    @GET("appraiselabellist")
    Observable<Appraise> getAppraiseLabel();

    /**
     * 评价
     * @param map
     * @return
     */
    @POST("appraise")
    @FormUrlEncoded
    Observable<Empty> Appraise(@FieldMap Map<String , String > map);

    @GET("getinfobyusernames")
    Observable<Contacts> getContacts(@QueryMap Map<String , String > map);
}
