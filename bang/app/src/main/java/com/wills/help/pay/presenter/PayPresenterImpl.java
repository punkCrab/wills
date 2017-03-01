package com.wills.help.pay.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.pay.model.PayModel;
import com.wills.help.pay.model.AliPaySign;
import com.wills.help.pay.model.WXPaySign;
import com.wills.help.pay.view.PayView;
import com.wills.help.release.model.OrderDetail;
import com.wills.help.utils.NetUtils;

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
                .subscribe(new ApiSubscriber<OrderDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(OrderDetail orderInfo) {
                        payView.setOrderInfo(orderInfo.getData());
                    }
                });
    }

    @Override
    public void AliPaySign(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog((Context) payView);
        payModel.AliPaySign(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AliPaySign>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(AliPaySign paySign) {
                        payView.setAliPaySign(paySign.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void balancePay(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog((Context) payView);
        payModel.balancePay(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Empty empty) {
                        payView.setBalancePay();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void WXPaySign(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog((Context) payView);
        payModel.WXPaySign(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<WXPaySign>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(WXPaySign wxPaySign) {
                        payView.setWXPaySign(wxPaySign.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }
}
