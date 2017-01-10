package com.wills.help.release.model;

/**
 * com.wills.help.release.model
 * Created by lizhaoyong
 * 2017/1/10.
 */

public class Release {

    /**
     * state : 1
     * info :
     * data : {"orderid":"2017011051535299"}
     */

    private int state;
    private String info;
    /**
     * orderid : 2017011051535299
     */

    private OrderId data;

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

    public OrderId getData() {
        return data;
    }

    public void setData(OrderId data) {
        this.data = data;
    }

    public static class OrderId {
        private String orderid;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }
}
