package com.wills.help.home.model;

import com.wills.help.net.HttpManager;

import rx.Observable;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class HomeModel {

    public Observable<Banner> getBanner(){
        return HttpManager.getApiInterface().getBanner();
    }
    public Observable<News> getNews(){
        return HttpManager.getApiInterface().getNews();
    }
}
