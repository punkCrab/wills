package com.wills.help.pay.model;

/**
 * com.wills.help.pay.model
 * Created by lizhaoyong
 * 2017/2/28.
 */

public class WXPaySign {
    private int state;
    private String info;
    private WXPayInfo data;

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

    public WXPayInfo getData() {
        return data;
    }

    public void setData(WXPayInfo data) {
        this.data = data;
    }

    public static class WXPayInfo {
        private String appid;
        private String partnerid;
        private String noncestr;
        private String packageValue;
        private String timestamp;
        private String sign;
        private String prepayid;

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }
    }
}
