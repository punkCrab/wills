package com.wills.help.cache;

import com.wills.help.login.model.User;

import java.util.Observable;

/**
 * com.wills.help.cache
 * Created by lizhaoyong
 * 2016/12/14.
 */

public class UserObserver extends Observable{

    private User.UserInfo userInfo;
    public UserObserver(User.UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void notifyObservers(Object data) {
        setChanged();
        super.notifyObservers(data);
    }
}
