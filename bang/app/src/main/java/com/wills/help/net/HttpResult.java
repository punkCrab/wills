package com.wills.help.net;

import com.wills.help.utils.AppConfig;

/**
 * com.wills.help.net
 * Created by lizhaoyong
 * 2016/11/7.
 */

public class HttpResult<T> {
    private int state;
    private String info;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isCodeInvalid() {
        return state != AppConfig.NET_SUCCESS;
    }
}
