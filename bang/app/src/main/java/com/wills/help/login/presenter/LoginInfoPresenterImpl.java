package com.wills.help.login.presenter;


import com.wills.help.login.model.LoginInfo;
import com.wills.help.login.model.LoginInfoModelImpl;
import com.wills.help.login.view.LoginInfoView;
import com.wills.help.net.ApiSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.lzy.mmd.mvptest.presenter
 * Created by lizhaoyong
 * 2016/10/20.
 */

public class LoginInfoPresenterImpl implements LoginInfoPresenter{
    private LoginInfoModelImpl loginInfoModel;
    private LoginInfoView loginInfoView;

    public LoginInfoPresenterImpl(LoginInfoView loginInfoView) {
        this.loginInfoView = loginInfoView;
        loginInfoModel = new LoginInfoModelImpl();
    }

    @Override
    public void getLoginInfo() {
        Map<String,String> map = new HashMap<>();
        map.put("page","2");
//        loginInfoModel.net_getLoginInfo(map)
        loginInfoModel.net_getLoginInfo("guolei1130")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<LoginInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        if (loginInfo!=null){
                            loginInfoView.setLoginInfo(loginInfo);
                        }
                    }

                });
    }
}
