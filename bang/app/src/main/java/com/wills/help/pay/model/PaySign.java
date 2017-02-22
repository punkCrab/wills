package com.wills.help.pay.model;

/**
 * com.wills.help.pay.model
 * Created by lizhaoyong
 * 2017/2/22.
 */

public class PaySign {

    /**
     * state : 1
     * info : 成功
     * data : app_id=3214567&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%222017022252509852%22%7D%22&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F59.110.62.75%2Fthink%2Fnotify&sign=kKAMvamxBAbW8ullL8tvhNlos5GXC7%2FEDofwmBUH6nYLEHUIDU5X%2B%2F4jWnhywP1Hoe1321d1BlUGafqLJpNTshmfAJCKapr1exrcSMi9yfsvkoyEJMHMVP52LGqTBsy0sc585MWGhOp8leiNUKKC9qnVzi%2Fds9%2F3SiBXSS4uB77zJQrVBqqV1QFAoZ11xlySFBEL7rO0fMCBM4lhPlAJOjp7vvRggmQ1F2Hlw1mNhz0HqWVgyr%2FQsPRQh%2F6goIVd4wG2jA8JQp7dc46uRnDjEYeQqG%2BaiJTPQ690yC9byNrLJzsWijh%2FblrHwNKlw2TaNaxi29ckE3%2Fj72aHzVWxcw%3D%3D&sign_type=RSA2&timestamp=1487730978075&version=1.0
     */

    private int state;
    private String info;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
