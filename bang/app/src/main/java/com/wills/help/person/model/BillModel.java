package com.wills.help.person.model;

import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.person.model
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class BillModel {
    public Observable<Bill> getBill(Map<String , String > map){
        return HttpManager.getApiInterface().getBill(map);
    }
}
