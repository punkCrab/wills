package com.wills.help.cache;

import java.util.Observable;

/**
 * com.wills.help.cache
 * Created by lizhaoyong
 * 2016/12/14.
 */

public class UserObserver extends Observable{

    public UserObserver() {
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }

    @Override
    protected void setChanged() {
        super.setChanged();
    }
}
