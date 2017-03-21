package com.wills.help.setting.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2017/3/21.
 */

public class ChangePasswordModel {

    public Observable<Empty> changePassword(Map<String , String > map){
        return HttpManager.getApiInterface().changePassword(map);
    }
}
