package com.wills.help.login.presenter;


import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.wills.help.base.App;
import com.wills.help.login.model.LoginModel;
import com.wills.help.login.model.User;
import com.wills.help.login.view.LoginView;
import com.wills.help.net.ApiSubscriber;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.NetUtils;
import com.wills.help.utils.SharedPreferencesUtils;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * com.lzy.mmd.mvptest.presenter
 * Created by lizhaoyong
 * 2016/10/20.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginModel loginInfoModel;
    private LoginView loginInfoView;

    public LoginPresenterImpl(LoginView loginInfoView) {
        this.loginInfoView = loginInfoView;
        loginInfoModel = new LoginModel();
    }

    @Override
    public void login(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog((Context) loginInfoView);
        loginInfoModel.login(map)
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
                .subscribe(new ApiSubscriber<User>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(User login) {
                        loginInfoView.setLogin(login);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }
}
