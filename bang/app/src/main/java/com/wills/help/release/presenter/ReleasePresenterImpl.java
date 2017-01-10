package com.wills.help.release.presenter;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.release.model.Release;
import com.wills.help.release.model.ReleaseModel;
import com.wills.help.release.view.ReleaseView;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.release.presenter
 * Created by lizhaoyong
 * 2017/1/10.
 */

public class ReleasePresenterImpl implements ReleasePresenter{
    ReleaseModel releaseModel;
    ReleaseView releaseView;

    public ReleasePresenterImpl(ReleaseView releaseView) {
        this.releaseView = releaseView;
        releaseModel = new ReleaseModel();
    }

    @Override
    public void release(Map<String, String> map) {
        releaseModel.release(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Release>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Release release) {
                        releaseView.setRelease(release.getData());
                    }
                });
    }
}
