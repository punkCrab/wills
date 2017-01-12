package com.wills.help.person.presenter;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * com.wills.help.person.presenter
 * Created by lizhaoyong
 * 2017/1/12.
 */

public interface UserInfoPresenter {
    void setUserInfo(Map<String,String> map);
    void setAvatar(MultipartBody.Part avatar, String userId);
}
