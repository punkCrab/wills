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
        private String username;
        private String stateid;
        private String state;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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
    }
}
