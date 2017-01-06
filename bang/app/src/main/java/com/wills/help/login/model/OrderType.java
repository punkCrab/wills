package com.wills.help.login.model;

import com.wills.help.db.bean.OrderTypeInfo;

import java.io.Serializable;
import java.util.List;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class OrderType implements Serializable{

    /**
     * state : 1
     * info : success
     * data : [{"typeid":"1","ordertype":""},{"typeid":"2","ordertype":""}]
     */

    private int state;
    private String info;
    /**
     * typeid : 1
     * ordertype :
     */

    private List<OrderTypeInfo> data;

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

    public List<OrderTypeInfo> getData() {
        return data;
    }

    public void setData(List<OrderTypeInfo> data) {
        this.data = data;
    }

}
