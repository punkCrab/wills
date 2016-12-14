package com.wills.help.login.presenter;


import com.wills.help.base.App;
import com.wills.help.login.model.LoginModelImpl;
import com.wills.help.login.model.User;
import com.wills.help.login.view.LoginView;
import com.wills.help.net.ApiSubscriber;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.SharedPreferencesUtils;
import com.wills.help.utils.ToastUtils;

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
    private LoginModelImpl loginInfoModel;
    private LoginView loginInfoView;

    public LoginPresenterImpl(LoginView loginInfoView) {
        this.loginInfoView = loginInfoView;
        loginInfoModel = new LoginModelImpl();
    }

    @Override
    public void login(Map<String, String> map) {
        loginInfoModel.login(map)
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User login) {
                        //储存到本地
                        SharedPreferencesUtils.getInstance().put(AppConfig.SP_USER,login.getData().getUserid());
                        App.getApp().setUser(login.getData());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(User login) {
                        ToastUtils.toast("成功");
                    }
                });
    }
}
