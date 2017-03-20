package com.wills.help.setting.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.setting.model.FeedbackModel;
import com.wills.help.setting.view.FeedbackView;
import com.wills.help.utils.NetUtils;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.setting.presenter
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class FeedbackPresenterImpl implements FeedbackPresenter{

    FeedbackModel feedbackModel;
    FeedbackView feedbackView;

    public FeedbackPresenterImpl(FeedbackView feedbackView) {
        this.feedbackView = feedbackView;
        feedbackModel = new FeedbackModel();
    }

    @Override
    public void feedback(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog((Context) feedbackView);
        feedbackModel.feedback(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Empty empty) {
                        feedbackView.setFeedback();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }
}
