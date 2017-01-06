package com.wills.help.login.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2016/12/9.
 */

public class RegisterModelImpl {

    private RegisterModel registerModel;

    public RegisterModelImpl() {
        registerModel = HttpManager.getInstance().create(RegisterModel.class);
    }

    public Observable<Empty> getCode(String phone){
        return registerModel.getCode(phone);
    }

    public Observable<User> submitRegister(Map<String,String> map){
        return registerModel.submitRegister(map);
    }
}
