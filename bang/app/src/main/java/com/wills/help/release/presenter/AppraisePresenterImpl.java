package com.wills.help.release.presenter;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.release.model.Appraise;
import com.wills.help.release.model.AppraiseModel;
import com.wills.help.release.view.AppraiseView;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.release.presenter
 * Created by lizhaoyong
 * 2017/1/17.
 */

public class AppraisePresenterImpl implements AppraisePresenter{

    AppraiseModel appraiseModel;
    AppraiseView appraiseView;

    public AppraisePresenterImpl(AppraiseView appraiseView) {
        this.appraiseView = appraiseView;
        appraiseModel = new AppraiseModel();
    }

    @Override
    public void getAppraiseLabel() {
        appraiseModel.getAppraiseLabel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Appraise>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Appraise appraise) {
                        appraiseView.setAppraiseLabel(appraise.getData());
                    }
                });
    }

    @Override
    public void appraise(Map<String, String> map) {
        appraiseModel.appraise(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Empty empty) {
                        appraiseView.appraise();
                    }
                });
    }
}
