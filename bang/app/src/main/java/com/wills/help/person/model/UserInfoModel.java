package com.wills.help.person.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * com.wills.help.person.model
 * Created by lizhaoyong
 * 2017/1/12.
 */

public class UserInfoModel {

    public Observable<Empty> setUserInfo(Map<String , String > map){
        return HttpManager.getApiInterface().setUserInfo(map);
    }

    public Observable<Avatar> setAvatar(MultipartBody.Part avatar,String userId){
        return HttpManager.getApiInterface().setAvatar(avatar, RequestBody.create(null,userId));
    }
}
