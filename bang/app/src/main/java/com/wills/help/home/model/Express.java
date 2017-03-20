package com.wills.help.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2016/12/26.
 */

public class Express implements Serializable{

    /**
     * state : 1
     * info : success
     * data : [{"id":"1","deliveryid":"123456789987246","username":"15652956043","stateid":"1","state":""}]
     */

    private int state;
    private String info;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * id : 1
     * deliveryid : 123456789987246
     * username : 15652956043
     * stateid : 1
     * state :
     */

    private List<ExpressInfo> data;

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

    public List<ExpressInfo> getData() {
        return data;
    }

    public void setData(List<ExpressInfo> data) {
        this.data = data;
    }

    public static class ExpressInfo {
        private String id;
        private String deliveryid;
        private String deliverytype;
        private String phone_num;
        private String stateid;
        private String state;
        private String orderid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDeliveryid() {
            return deliveryid;
        }

        public void setDeliveryid(String deliveryid) {
            this.deliveryid = deliveryid;
        }

        public String getDeliverytype() {
            return deliverytype;
        }

        public void setDeliverytype(String deliverytype) {
            this.deliverytype = deliverytype;
        }

        public String getPhone_num() {
            return phone_num;
        }

        public void setPhone_num(String phone_num) {
            this.phone_num = phone_num;
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

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }
}
