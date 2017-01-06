package com.wills.help.login.model;

import com.wills.help.db.bean.PointInfo;

import java.io.Serializable;
import java.util.List;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class Point implements Serializable{

    /**
     * state : 1
     * info : success
     * data : [{"posid":"1","blockid ":"","posname":""}]
     */

    private int state;
    private String info;
    /**
     * posid : 1
     * blockid  :
     * posname :
     */

    private List<PointInfo> data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<PointInfo> getData() {
        return data;
    }

    public void setData(List<PointInfo> data) {
        this.data = data;
    }
}
