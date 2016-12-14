package com.wills.help.net;

import com.wills.help.utils.AppConfig;

/**
 * com.wills.help.net
 * Created by lizhaoyong
 * 2016/11/7.
 */

public class HttpResult {
    private int state;
    private String info;

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


    public boolean isCodeInvalid() {
        return state != AppConfig.NET_SUCCESS;
    }
}
