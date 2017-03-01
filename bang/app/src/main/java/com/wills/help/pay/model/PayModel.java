package com.wills.help.pay.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;
import com.wills.help.release.model.OrderDetail;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.pay.model
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class PayModel {

    public Observable<OrderDetail> getOrderInfo(Map<String , String> map){
        return HttpManager.getApiInterface().getOrderInfo(map);
    }

    public Observable<AliPaySign> AliPaySign(Map<String , String> map){
        return HttpManager.getApiInterface().AliPaySign(map);
    }

    public Observable<Empty> balancePay(Map<String , String> map){
        return HttpManager.getApiInterface().balancePay(map);
    }

    public  Observable<WXPaySign> WXPaySign(Map<String , String> map){
        return HttpManager.getApiInterface().WXPaySign(map);
    }
}
