package com.wills.help.login.model;


import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.lzy.mmd.mvptest.model
 * Created by lizhaoyong
 * 2016/10/20.
 */

public class LoginModelImpl {


    public Observable<User> login(Map<String,String> map) {
        return HttpManager.getInstance().create(LoginModel.class).login(map);
    }

}
