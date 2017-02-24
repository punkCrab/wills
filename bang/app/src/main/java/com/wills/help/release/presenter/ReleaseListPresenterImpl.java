package com.wills.help.release.presenter;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.release.model.OrderList;
import com.wills.help.release.model.ReleaseListModel;
import com.wills.help.release.view.ReleaseListView;
import com.wills.help.utils.NetUtils;

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

    @Override
    public void confirm(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog(((Fragment) releaseListView).getActivity());
        releaseListModel.confirm(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Empty empty) {
                        releaseListView.confirm();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }
}
