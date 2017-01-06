package com.wills.help.login.presenter;

import com.wills.help.db.manager.OrderTypeInfoHelper;
import com.wills.help.db.manager.PointInfoHelper;
import com.wills.help.login.model.ConfigModelImpl;
import com.wills.help.login.model.OrderType;
import com.wills.help.login.model.Point;
import com.wills.help.net.ApiSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.login.presenter
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class ConfigPresenterImpl implements ConfigPresenter{

    ConfigModelImpl configModel;

    public ConfigPresenterImpl() {
        configModel = new ConfigModelImpl();
    }

    @Override
    public void getPoint() {
        configModel.getPoint()
                .doOnNext(new Action1<Point>() {
                    @Override
                    public void call(Point o) {
                        PointInfoHelper.getInstance().deleteAll().subscribe();
                        PointInfoHelper.getInstance().insertData(o.getData()).subscribe();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Point>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Point point) {
                    }
                });
    }

    @Override
    public void getOrderType() {
        configModel.getOrderType()
                .doOnNext(new Action1<OrderType>() {
                    @Override
                    public void call(final OrderType o) {
                        OrderTypeInfoHelper.getInstance().deleteAll().subscribe();
                        OrderTypeInfoHelper.getInstance().insertData(o.getData()).subscribe();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<OrderType>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(OrderType orderType) {

                    }
                });
    }
}
