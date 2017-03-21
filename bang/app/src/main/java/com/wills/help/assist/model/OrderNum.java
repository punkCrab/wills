package com.wills.help.assist.model;

import java.util.List;

/**
 * com.wills.help.assist.model
 * Created by lizhaoyong
 * 2017/3/21.
 */

public class OrderNum {
    private int state;
    private String info;
    private List<PosNum> data;

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

    public List<PosNum> getData() {
        return data;
    }

    public void setData(List<PosNum> data) {
        this.data = data;
    }

    public static class PosNum{
        private String blockid;
        private String count;

        public String getBlockid() {
            return blockid;
        }

        public void setBlockid(String blockid) {
            this.blockid = blockid;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
