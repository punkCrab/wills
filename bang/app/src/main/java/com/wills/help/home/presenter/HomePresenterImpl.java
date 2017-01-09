package com.wills.help.home.presenter;

import com.wills.help.home.model.Banner;
import com.wills.help.home.model.HomeModel;
import com.wills.help.home.model.News;
import com.wills.help.home.view.HomeView;
import com.wills.help.net.ApiSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.home.presenter
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class HomePresenterImpl implements HomePresenter{
    HomeModel homeModel;
    HomeView homeView;

    public HomePresenterImpl(HomeView homeView) {
        this.homeView = homeView;
        homeModel = new HomeModel();
    }

    @Override
    public void getBanner() {
        homeModel.getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Banner>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Banner banner) {
                        homeView.setBanner(banner.getData());
                    }
                });
    }

    @Override
    public void getNews() {
        homeModel.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<News>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(News news) {
                        homeView.setNews(news.getData());
                    }
                });
    }

}
