package com.wills.help.login.model;

import com.wills.help.net.HttpManager;

import rx.Observable;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class ConfigModelImpl {
    ConfigModel configModel;

    public ConfigModelImpl() {
        configModel = HttpManager.getInstance().create(ConfigModel.class);
    }

    public Observable<Point> getPoint(){
        return configModel.getPoint();
    }

    public Observable<OrderType> getOrderType(){
        return configModel.getOrderType();
    }
}
