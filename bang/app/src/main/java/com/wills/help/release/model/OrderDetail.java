package com.wills.help.release.model;

/**
 * com.wills.help.release.model
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class OrderDetail {

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

    private OrderInfo data;

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

    public OrderInfo getData() {
        return data;
    }

    public void setData(OrderInfo data) {
        this.data = data;
    }

}
