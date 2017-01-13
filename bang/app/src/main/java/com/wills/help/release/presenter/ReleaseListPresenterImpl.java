package com.wills.help.release.presenter;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.release.model.OrderList;
import com.wills.help.release.model.ReleaseListModel;
import com.wills.help.release.view.ReleaseListView;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.release.presenter
 * Created by lizhaoyong
 * 2017/1/10.
 */

public class ReleaseListPresenterImpl implements ReleaseListPresenter{
    ReleaseListModel releaseListModel;
    ReleaseListView releaseListView;

    public ReleaseListPresenterImpl(ReleaseListView releaseListView) {
        this.releaseListView = releaseListView;
        releaseListModel = new ReleaseListModel();
    }

    @Override
    public void getReleaseList(Map<String, String> map) {
        releaseListModel.getReleaseList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<OrderList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(OrderList releaseList) {
                        releaseListView.setReleaseList(releaseList);
                    }
                });
    }
}
