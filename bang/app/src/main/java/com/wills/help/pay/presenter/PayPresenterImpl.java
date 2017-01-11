package com.wills.help.pay.presenter;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.pay.model.PayModel;
import com.wills.help.pay.view.PayView;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.release.model.ReleaseInfo;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.pay.presenter
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class PayPresenterImpl implements PayPresenter{
    PayModel payModel;
    PayView payView;

    public PayPresenterImpl(PayView payView) {
        this.payView = payView;
        payModel = new PayModel();
    }

    @Override
    public void getOrderInfo(Map<String, String> map) {
        payModel.getOrderInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<OrderInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(OrderInfo orderInfo) {
                        payView.setOrderInfo(orderInfo.getData());
                    }
                });
    }
}
