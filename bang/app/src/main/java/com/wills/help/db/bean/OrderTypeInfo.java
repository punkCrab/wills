package com.wills.help.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * com.wills.help.db.bean
 * Created by lizhaoyong
 * 2017/1/6.
 */
@Entity
public class OrderTypeInfo {
    @Id
    private String typeid;
    private String ordertype;
    private int showing;

    @Generated(hash = 637024865)
    public OrderTypeInfo(String typeid, String ordertype, int showing) {
        this.typeid = typeid;
        this.ordertype = ordertype;
        this.showing = showing;
    }

    @Generated(hash = 2134308605)
    public OrderTypeInfo() {
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public int getShowing() {
        return this.showing;
    }

    public void setShowing(int showing) {
        this.showing = showing;
    }
}
