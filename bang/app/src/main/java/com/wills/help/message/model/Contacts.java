package com.wills.help.message.model;

import com.wills.help.db.bean.Contact;

import java.util.List;

/**
 * com.wills.help.message.model
 * Created by lizhaoyong
 * 2017/1/17.
 */

public class Contacts {
    private int state;
    private String info;

    private List<Contact> data;

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

    public List<Contact> getData() {
        return data;
    }

    public void setData(List<Contact> data) {
        this.data = data;
    }
}
