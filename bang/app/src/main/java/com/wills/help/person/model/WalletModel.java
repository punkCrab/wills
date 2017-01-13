package com.wills.help.person.model;

import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.person.model
 * Created by lizhaoyong
 * 2017/1/13.
 */

public class WalletModel {
    public Observable<Wallet> getMoney(Map<String , String > map){
        return HttpManager.getApiInterface().getMoney(map);
    }
}
