package com.wills.help.login.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2017/1/5.
 */

public class ResetModel {

    public Observable<Empty> getCode(String phone){
        return HttpManager.getApiInterface().getResetCode(phone);
    }

    public Observable<Empty> reset(Map<String,String> map){
        return HttpManager.getApiInterface().reset(map);
    }
}
