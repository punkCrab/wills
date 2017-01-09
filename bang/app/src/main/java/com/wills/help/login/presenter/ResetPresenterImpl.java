package com.wills.help.login.presenter;

import com.wills.help.login.model.ResetModel;
import com.wills.help.login.view.ResetView;
import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.utils.ToastUtils;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.login.presenter
 * Created by lizhaoyong
 * 2017/1/5.
 */

public class ResetPresenterImpl implements ResetPresenter {
    ResetView resetView;
    ResetModel resetModel;

    public ResetPresenterImpl(ResetView resetView) {
        this.resetView = resetView;
        resetModel = new ResetModel();
    }

    @Override
    public void getCode(String phone) {
        resetModel.getCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onNext(Empty empty) {
                        ToastUtils.toast("成功");
                    }
                });
    }

    @Override
    public void reset(Map<String, String> map) {
        resetModel.reset(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Empty empty) {
                        resetView.setReset();
                    }
                });

    }
}
