package com.wills.help.release.model;

import java.util.List;

/**
 * com.wills.help.release.model
 * Created by lizhaoyong
 * 2017/1/10.
 */

public class ReleaseList {
    private int state;
    private String info;
    private int count;
    private List<ReleaseInfo> data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<ReleaseInfo> getData() {
        return data;
    }

    public void setData(List<ReleaseInfo> data) {
        this.data = data;
    }
}
