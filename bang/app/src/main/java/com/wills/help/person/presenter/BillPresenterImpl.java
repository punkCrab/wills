package com.wills.help.person.presenter;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.person.model.Bill;
import com.wills.help.person.model.BillModel;
import com.wills.help.person.view.BillView;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.person.presenter
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class BillPresenterImpl implements BillPresenter{

    BillModel billModel;
    BillView billView;

    public BillPresenterImpl(BillView billView) {
        this.billView = billView;
        billModel = new BillModel();
    }

    @Override
    public void getBill(Map<String, String> map) {
        billModel.getBill(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Bill>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Bill bill) {
                        billView.setBill(bill);
                    }
                });
    }
}
