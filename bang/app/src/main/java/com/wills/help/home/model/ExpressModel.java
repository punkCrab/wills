package com.wills.help.home.model;

import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class ExpressModel {

    public Observable<Express> getExpress(Map<String ,String> map){
        return HttpManager.getApiInterface().getExpress(map);
    }
}
