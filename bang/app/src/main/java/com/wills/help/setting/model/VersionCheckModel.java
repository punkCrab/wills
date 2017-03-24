package com.wills.help.setting.model;

import com.wills.help.net.HttpManager;

import rx.Observable;

/**
 * com.wills.help.setting.model
 * Created by lizhaoyong
 * 2017/3/23.
 */

public class VersionCheckModel {

    public Observable<VersionCheck> versionCheck(){
        return HttpManager.getApiInterface().versionCheck();
    }
}
