package com.wills.help.release.model;

/**
 * com.wills.help.release.model
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class OrderInfo {

    /**
     * state : 1
     * info : 成功
     * data : {"orderid":"2017011054101100","ordertype":"1","releaseuserid":"{83970672-286A-24BE-CD46-E87618775B35}","releasename":"15311437664","releasesex":"0","releaseschool":"","srcid":"0","srcdetail":"篮球场","desid":"0","desdetail":"篮球场","money":"3","createtime":"2017-01-10 12:01:26","stateid":"0","state":"订单提交，请付款","acceptuserid":"","acceptusername":"","acceptsex":"0"}
     */

    private int state;
    private String info;
    /**
     * orderid : 2017011054101100
     * ordertype : 1
     * releaseuserid : {83970672-286A-24BE-CD46-E87618775B35}
     * releasename : 15311437664
     * releasesex : 0
     * releaseschool :
     * srcid : 0
     * srcdetail : 篮球场
     * desid : 0
     * desdetail : 篮球场
     * money : 3
     * createtime : 2017-01-10 12:01:26
     * stateid : 0
     * state : 订单提交，请付款
     * acceptuserid :
     * acceptusername :
     * acceptsex : 0
     */

    private OrderDetail data;

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

    public OrderDetail getData() {
        return data;
    }

    public void setData(OrderDetail data) {
        this.data = data;
    }

    public static class OrderDetail {
        private String orderid;
        private String ordertype;
        private String releaseuserid;
        private String releasename;
        private String releasesex;
        private String releaseschool;
        private String srcid;
        private String srcdetail;
        private String desid;
        private String desdetail;
        private String money;
        private String createtime;
        private String stateid;
        private String state;
        private String acceptuserid;
        private String acceptusername;
        private String acceptsex;

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

        public String getReleaseuserid() {
            return releaseuserid;
        }

        public void setReleaseuserid(String releaseuserid) {
            this.releaseuserid = releaseuserid;
        }

        public String getReleasename() {
            return releasename;
        }

        public void setReleasename(String releasename) {
            this.releasename = releasename;
        }

        public String getReleasesex() {
            return releasesex;
        }

        public void setReleasesex(String releasesex) {
            this.releasesex = releasesex;
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

        public String getAcceptuserid() {
            return acceptuserid;
        }

        public void setAcceptuserid(String acceptuserid) {
            this.acceptuserid = acceptuserid;
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
    }
}
