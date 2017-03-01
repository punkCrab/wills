package com.wills.help.pay.view;

import com.wills.help.pay.model.WXPaySign;
import com.wills.help.release.model.OrderInfo;

/**
 * com.wills.help.pay.view
 * Created by lizhaoyong
 * 2017/1/11.
 */

public interface PayView {
    void setOrderInfo(OrderInfo orderInfo);
    void setAliPaySign(String orderInfo);
    void setBalancePay();
    void setWXPaySign(WXPaySign.WXPayInfo wxPaySign);
}
