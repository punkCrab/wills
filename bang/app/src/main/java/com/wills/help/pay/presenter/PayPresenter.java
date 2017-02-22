package com.wills.help.pay.presenter;

import java.util.Map;

/**
 * com.wills.help.pay.presenter
 * Created by lizhaoyong
 * 2017/1/11.
 */

public interface PayPresenter {

    void getOrderInfo(Map<String,String> map);

    void paySign(Map<String , String> map);
}
