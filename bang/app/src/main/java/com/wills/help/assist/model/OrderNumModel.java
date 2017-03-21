package com.wills.help.assist.model;

import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.assist.model
 * Created by lizhaoyong
 * 2017/3/21.
 */

public class OrderNumModel {

    public Observable<OrderNum> getOrderNum(){
        return HttpManager.getApiInterface().getOrderNum();
    }
}
