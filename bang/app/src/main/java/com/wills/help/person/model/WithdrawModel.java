package com.wills.help.person.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.person.model
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class WithdrawModel {

    public Observable<Empty> withdraw(Map<String , String > map){
        return HttpManager.getApiInterface().withdraw(map);
    }
}
