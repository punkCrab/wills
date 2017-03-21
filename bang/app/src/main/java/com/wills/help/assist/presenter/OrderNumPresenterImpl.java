package com.wills.help.assist.presenter;

import com.wills.help.assist.model.OrderNum;
import com.wills.help.assist.model.OrderNumModel;
import com.wills.help.assist.view.OrderNumView;
import com.wills.help.net.ApiSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.assist.presenter
 * Created by lizhaoyong
 * 2017/3/21.
 */

public class OrderNumPresenterImpl implements OrderNumPresenter{

    private OrderNumModel orderNumModel;
    private OrderNumView orderNumView;

    public OrderNumPresenterImpl(OrderNumView orderNumView) {
        this.orderNumView = orderNumView;
        orderNumModel = new OrderNumModel();
    }

    @Override
    public void getOrderNum() {
        orderNumModel.getOrderNum()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<OrderNum>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(OrderNum orderNum) {
                        orderNumView.setOrderNum(orderNum);
                    }
                });
    }
}
