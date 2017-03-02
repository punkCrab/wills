package com.wills.help.release.model;

import java.io.Serializable;

/**
 * com.wills.help.release.model
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class OrderInfo implements Serializable{
    private String orderid;
    private String ordertype;
    private String ordertypename;
    private String maintype;
    private String releaseusername;
    private String releaseavatar;
    private String releasesex;
    private String releasenickname;
    private String releaseschool;
    private String srcid;
    private String srcname;
    private String srcdetail;
    private String desid;
    private String desname;
    private String desdetail;
    private String money;
    private String createtime;
    private String stateid;
    private String state;
    private String acceptusername;
    private String acceptsex;
    private String acceptnickname;
    private String acceptavatar;
    private String remark;

    public String getOrdertypename() {
        return ordertypename;
    }

    public void setOrdertypename(String ordertypename) {
        this.ordertypename = ordertypename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSrcname() {
        return srcname;
    }

    public void setSrcname(String srcname) {
        this.srcname = srcname;
    }

    public String getDesname() {
        return desname;
    }

    public void setDesname(String desname) {
        this.desname = desname;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getMaintype() {
        return maintype;
    }

    public void setMaintype(String maintype) {
        this.maintype = maintype;
    }

    public String getReleaseusername() {
        return releaseusername;
    }

    public void setReleaseusername(String releaseusername) {
        this.releaseusername = releaseusername;
    }

    public String getReleaseavatar() {
        return releaseavatar;
    }

    public void setReleaseavatar(String releaseavatar) {
        this.releaseavatar = releaseavatar;
    }

    public String getReleasesex() {
        return releasesex;
    }

    public void setReleasesex(String releasesex) {
        this.releasesex = releasesex;
    }

    public String getReleasenickname() {
        return releasenickname;
    }

    public void setReleasenickname(String releasenickname) {
        this.releasenickname = releasenickname;
    }

    public String getReleaseschool() {
        return releaseschool;
    }

    public void setReleaseschool(String releaseschool) {
        this.releaseschool = releaseschool;
    }

    public String getSrcid() {
        return srcid;
    }

    public void setSrcid(String srcid) {
        this.srcid = srcid;
    }

    public String getSrcdetail() {
        return srcdetail;
    }

    public void setSrcdetail(String srcdetail) {
        this.srcdetail = srcdetail;
    }

    public String getDesid() {
        return desid;
    }

    public void setDesid(String desid) {
        this.desid = desid;
    }

    public String getDesdetail() {
        return desdetail;
    }

    public void setDesdetail(String desdetail) {
        this.desdetail = desdetail;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAcceptusername() {
        return acceptusername;
    }

    public void setAcceptusername(String acceptusername) {
        this.acceptusername = acceptusername;
    }

    public String getAcceptsex() {
        return acceptsex;
    }

    public void setAcceptsex(String acceptsex) {
        this.acceptsex = acceptsex;
    }

    public String getAcceptnickname() {
        return acceptnickname;
    }

    public void setAcceptnickname(String acceptnickname) {
        this.acceptnickname = acceptnickname;
    }

    public String getAcceptavatar() {
        return acceptavatar;
    }

    public void setAcceptavatar(String acceptavatar) {
        this.acceptavatar = acceptavatar;
    }
}
