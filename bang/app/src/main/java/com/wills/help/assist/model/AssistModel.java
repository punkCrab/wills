package com.wills.help.assist.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;
import com.wills.help.release.model.OrderList;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.assist.model
 * Created by lizhaoyong
 * 2017/1/13.
 */

public class AssistModel {
    public Observable<Empty> Accept(Map<String , String > map){
        return HttpManager.getApiInterface().accept(map);
    }

    public Observable<OrderList> getAssistList(Map<String , String > map){
        return HttpManager.getApiInterface().getAssistList(map);
    }
}
