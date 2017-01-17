package com.wills.help.message.model;

import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.message.model
 * Created by lizhaoyong
 * 2017/1/17.
 */

public class ContactsModel {
    public Observable<Contacts> getContacts(Map<String , String > map){
        return HttpManager.getApiInterface().getContacts(map);
    }
}
