package com.wills.help.home.model;

import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class ExpressModelImpl {
    ExpressModel expressModel;
    public ExpressModelImpl() {
        expressModel = HttpManager.getInstance().create(ExpressModel.class);
    }

    public Observable<Express> getExpress(Map<String ,String> map){
        return expressModel.getExpress(map);
    }
}
