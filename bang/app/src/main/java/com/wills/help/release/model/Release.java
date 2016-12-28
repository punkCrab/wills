package com.wills.help.release.model;

/**
 * com.wills.help.release.model
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class Release {
    private String name;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
