package com.wills.help.person.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.person.model
 * Created by lizhaoyong
 * 2017/3/3.
 */

public class IdCheckModel {

    public Observable<Empty> idCheck(Map<String , String > map){
        return HttpManager.getApiInterface().idCheck(map);
    }

}
