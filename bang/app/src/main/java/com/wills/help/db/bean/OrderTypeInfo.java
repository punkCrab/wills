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

    @Generated(hash = 604748641)
    public OrderTypeInfo(String typeid, String ordertype) {
        this.typeid = typeid;
        this.ordertype = ordertype;
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
}
