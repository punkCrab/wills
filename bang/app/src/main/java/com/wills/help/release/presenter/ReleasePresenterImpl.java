package com.wills.help.release.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.release.model.Release;
import com.wills.help.release.model.ReleaseModel;
import com.wills.help.release.view.ReleaseView;
import com.wills.help.utils.NetUtils;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.release.presenter
 * Created by lizhaoyong
 * 2017/1/10.
 */

public class ReleasePresenterImpl implements ReleasePresenter {
    ReleaseModel releaseModel;
    ReleaseView releaseView;
    private AlertDialog dialog;

    public ReleasePresenterImpl(ReleaseView releaseView) {
        this.releaseView = releaseView;
        releaseModel = new ReleaseModel();
    }

    @Override
    public void release(Map<String, String> map , int from) {
        if (from == 1){
            dialog = NetUtils.netDialog(((Context) releaseView));
        }else if (from == 2){
            dialog = NetUtils.netDialog(((Fragment) releaseView).getActivity());
        }
        releaseModel.release(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Release>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Release release) {
                        releaseView.setRelease(release.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void updateOrder(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog(((Context) releaseView));
        releaseModel.updateOrder(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Empty empty) {
                        releaseView.updateOrder();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void cancelOrder(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog(((Context) releaseView));
        releaseModel.cancelOrder(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Empty empty) {
                        releaseView.deleteOrder();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }
}
