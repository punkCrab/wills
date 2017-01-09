package com.wills.help.home.presenter;

import com.wills.help.home.model.Express;
import com.wills.help.home.model.ExpressModel;
import com.wills.help.home.view.ExpressView;
import com.wills.help.net.ApiSubscriber;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.home.presenter
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class ExpressPresenterImpl implements ExpressPresenter{
    ExpressModel expressModel;
    ExpressView expressView;

    public ExpressPresenterImpl(ExpressView expressView) {
        this.expressView = expressView;
        expressModel = new ExpressModel();
    }

    @Override
    public void getExpress(Map<String, String> map) {
        expressModel.getExpress(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Express>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Express express) {
                        expressView.setExpress(express.getData());
                    }
                });
    }
}
