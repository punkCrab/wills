package com.wills.help.home.model;

import java.io.Serializable;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2016/12/26.
 */

public class Express implements Serializable{
    private String expressNo;
    private String expressName;

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }
}
