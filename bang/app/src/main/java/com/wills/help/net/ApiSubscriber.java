package com.wills.help.net;

import com.google.gson.JsonParseException;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.NetUtils;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.ToastUtils;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * com.wills.help.net
 * Created by lizhaoyong
 * 2016/11/7.
 */

public abstract class ApiSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;
        /**
         * 获取根源 异常
         */
        while (throwable.getCause() != null){
            e = throwable;
            throwable = throwable.getCause();
        }
        if(e instanceof HttpException){//对网络异常 弹出相应的toast
            HttpException httpException = (HttpException) e;
            if(StringUtils.isNullOrEmpty(httpException.getMessage())){
                ToastUtils.toast(R.string.error_net_fail);
            }else {
                String errorMsg = httpException.getMessage();
                if(StringUtils.isNullOrEmpty(errorMsg)){
                    ToastUtils.toast(R.string.app_name);
                }else {
                    ToastUtils.toast(errorMsg);
                }

            }
        }else if(e instanceof ApiException){//服务器返回的错误
            onResultError((ApiException) e);
        }else if(e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){//解析异常
            ToastUtils.toast(R.string.error_net_parse);
        }else if(e instanceof UnknownHostException){
            ToastUtils.toast(R.string.error_network_is_not_available);
        }else if(e instanceof SocketTimeoutException) {
            ToastUtils.toast(R.string.error_net_timeout);
        }else {//未知错误
            if(NetUtils.isConnected(App.getApp())){
                ToastUtils.toast(R.string.error_inner_error);
                e.printStackTrace();//对于未知错误进行打印,大部分为程序逻辑错误
            }else {
                ToastUtils.toast(R.string.error_network_is_not_available);
            }
        }
    }
    /**
     * 服务器返回的错误
     * @param ex
     */
    protected  void onResultError(ApiException ex){
        //default 打印 服务器返回
        String msg = ex.getMessage();
        if(StringUtils.isNullOrEmpty(msg)){
            ToastUtils.toast("服务器未返回具体错误信息");
        }else {
            ToastUtils.toast(ex.getMessage());
        }
        if(AppConfig.TOKEN_EXPRIED == ex.getErrorCode()){

        }
    }
}
