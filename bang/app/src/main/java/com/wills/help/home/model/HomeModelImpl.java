package com.wills.help.home.model;

import com.wills.help.net.HttpManager;

import rx.Observable;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class HomeModelImpl {
    HomeModel homeModel;

    public HomeModelImpl() {
        homeModel = HttpManager.getInstance().create(HomeModel.class);
    }

    public Observable<Banner> getBanner(){
        return homeModel.getBanner();
    }
    public Observable<News> getNews(){
        return homeModel.getNews();
    }
}
