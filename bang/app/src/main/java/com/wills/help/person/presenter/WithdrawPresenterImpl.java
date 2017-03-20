package com.wills.help.person.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.person.model.WithdrawModel;
import com.wills.help.person.view.WithdrawView;
import com.wills.help.utils.NetUtils;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.person.presenter
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class WithdrawPresenterImpl implements WithdrawPresenter{

    WithdrawModel withdrawModel;
    WithdrawView withdrawView;

    public WithdrawPresenterImpl(WithdrawView withdrawView) {
        this.withdrawView = withdrawView;
        withdrawModel = new WithdrawModel();
    }

    @Override
    public void withdraw(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog((Context) withdrawView);
        withdrawModel.withdraw(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Empty empty) {
                        withdrawView.setWithdraw();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }
}
