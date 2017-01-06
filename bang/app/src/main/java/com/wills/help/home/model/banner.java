package com.wills.help.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class Banner implements Serializable{

    /**
     * state : 1
     * info : success
     * data : [{"bannerid":"1","bannerurl":"/think/Banner/banner1.png","requesturl":"http://59.110.62.75/think/banner/banner1.png"},{"bannerid":"2","bannerurl":"/think/Banner/banner2.png","requesturl":"http://59.110.62.75/think/banner/banner2.png"},{"bannerid":"3","bannerurl":"/think/Banner/banner3.png","requesturl":"http://59.110.62.75/think/banner/banner3.png"}]
     */

    private int state;
    private String info;
    /**
     * bannerid : 1
     * bannerurl : /think/Banner/banner1.png
     * requesturl : http://59.110.62.75/think/banner/banner1.png
     */

    private List<BannerInfo> data;

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

    public List<BannerInfo> getData() {
        return data;
    }

    public void setData(List<BannerInfo> data) {
        this.data = data;
    }

    public static class BannerInfo {
        private String bannerid;
        private String bannerurl;
        private String requesturl;

        public String getBannerid() {
            return bannerid;
        }

        public void setBannerid(String bannerid) {
            this.bannerid = bannerid;
        }

        public String getBannerurl() {
            return bannerurl;
        }

        public void setBannerurl(String bannerurl) {
            this.bannerurl = bannerurl;
        }

        public String getRequesturl() {
            return requesturl;
        }

        public void setRequesturl(String requesturl) {
            this.requesturl = requesturl;
        }

        @Override
        public String toString() {
            return "BannerInfo{" +
                    "bannerid='" + bannerid + '\'' +
                    ", bannerurl='" + bannerurl + '\'' +
                    ", requesturl='" + requesturl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Banner{" +
                "state=" + state +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
