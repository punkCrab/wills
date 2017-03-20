package com.wills.help.person.model;

import java.util.List;

/**
 * com.wills.help.person.model
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class Bill {
    private int state;
    private String info;
    private int count;
    private List<BillInfo> data;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<BillInfo> getData() {
        return data;
    }

    public void setData(List<BillInfo> data) {
        this.data = data;
    }

    public static class BillInfo{
        private String billid;
        private String userid;
        private String username;
        private String money;
        private String time;
        private String orderid ;
        private String ordertype;

        public String getBillid() {
            return billid;
        }

        public void setBillid(String billid) {
            this.billid = billid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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
    }
}
