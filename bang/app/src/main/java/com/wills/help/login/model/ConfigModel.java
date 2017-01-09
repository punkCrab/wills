package com.wills.help.login.model;

import com.wills.help.net.HttpManager;

import rx.Observable;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class ConfigModel {

    public Observable<Point> getPoint(){
        return HttpManager.getApiInterface().getPoint();
    }

    public Observable<OrderType> getOrderType(){
        return HttpManager.getApiInterface().getOrderType();
    }
}
