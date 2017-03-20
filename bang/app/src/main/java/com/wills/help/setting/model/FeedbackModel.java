package com.wills.help.setting.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.setting.model
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class FeedbackModel {

    public Observable<Empty> feedback(Map<String , String > map){
        return HttpManager.getApiInterface().feedback(map);
    }
}
