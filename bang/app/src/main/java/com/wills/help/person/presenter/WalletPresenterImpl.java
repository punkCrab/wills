package com.wills.help.person.presenter;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.person.model.Wallet;
import com.wills.help.person.model.WalletModel;
import com.wills.help.person.view.WalletView;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.person.presenter
 * Created by lizhaoyong
 * 2017/1/13.
 */

public class WalletPresenterImpl implements WalletPresenter{

    WalletModel walletModel;
    WalletView walletView;

    public WalletPresenterImpl(WalletView walletView) {
        this.walletView = walletView;
        walletModel = new WalletModel();
    }

    @Override
    public void getMoney(Map<String, String> map) {
        walletModel.getMoney(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Wallet>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Wallet money) {
                        walletView.setMoney(money.getData());
                    }
                });
    }
}
