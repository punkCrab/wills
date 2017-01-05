package com.wills.help.login.presenter;

import com.wills.help.base.App;
import com.wills.help.login.model.ResetModelImpl;
import com.wills.help.login.model.User;
import com.wills.help.login.view.ResetView;
import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.SharedPreferencesUtils;
import com.wills.help.utils.ToastUtils;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.login.presenter
 * Created by lizhaoyong
 * 2017/1/5.
 */

public class ResetPresenterImpl implements ResetPresenter {
    ResetView resetView;
    ResetModelImpl resetModel;

    public ResetPresenterImpl(ResetView resetView) {
        this.resetView = resetView;
        resetModel = new ResetModelImpl();
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
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User login) {
                        //储存到本地
                        SharedPreferencesUtils.getInstance().put(AppConfig.SP_USER,login.getData().getUserid());
                        App.getApp().setUser(login.getData());
                        App.getApp().setIsLogin(true);
                    }
                })
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
