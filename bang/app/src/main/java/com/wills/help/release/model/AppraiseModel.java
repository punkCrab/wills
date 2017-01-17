package com.wills.help.release.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.release.model
 * Created by lizhaoyong
 * 2017/1/17.
 */

public class AppraiseModel {

    public Observable<Appraise> getAppraiseLabel(){
        return HttpManager.getApiInterface().getAppraiseLabel();
    }

    public Observable<Empty> appraise(Map<String ,String> map){
        return HttpManager.getApiInterface().Appraise(map);
    }
}
