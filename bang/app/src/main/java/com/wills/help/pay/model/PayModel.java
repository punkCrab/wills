package com.wills.help.pay.model;

import com.wills.help.net.HttpManager;
import com.wills.help.release.model.OrderInfo;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.pay.model
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class PayModel {

    public Observable<OrderInfo> getOrderInfo(Map<String , String> map){
        return HttpManager.getApiInterface().getOrderInfo(map);
    }
}
