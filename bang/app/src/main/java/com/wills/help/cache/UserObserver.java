package com.wills.help.cache;

import com.wills.help.base.App;

import java.util.Observable;

/**
 * com.wills.help.cache
 * Created by lizhaoyong
 * 2016/12/14.
 */

public class UserObserver extends Observable{

    private App app;
    public UserObserver(App app) {
        this.app = app;
    }

    @Override
    public void notifyObservers(Object data) {
        setChanged();
        super.notifyObservers(data);
    }
}
