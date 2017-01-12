package com.wills.help.person.presenter;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.person.model.UserInfoModel;
import com.wills.help.person.view.UserInfoView;

import java.util.Map;

import okhttp3.MultipartBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.person.presenter
 * Created by lizhaoyong
 * 2017/1/12.
 */

public class UserInfoPresenterImpl implements UserInfoPresenter{
    UserInfoModel userInfoModel;
    UserInfoView userInfoView;

    public UserInfoPresenterImpl(UserInfoView userInfoView) {
        this.userInfoView = userInfoView;
        userInfoModel = new UserInfoModel();
    }

    @Override
    public void setUserInfo(Map<String, String> map) {
        userInfoModel.setUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Empty empty) {
                        userInfoView.setUserInfo();
                    }
                });
    }

    @Override
    public void setAvatar(MultipartBody.Part avatar, String userId) {
        userInfoModel.setAvatar(avatar,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Empty empty) {
                        userInfoView.setAvatar();
                    }
                });
    }
}
