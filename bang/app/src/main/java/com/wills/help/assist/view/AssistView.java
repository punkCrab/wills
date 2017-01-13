package com.wills.help.assist.view;

import com.wills.help.release.model.OrderList;

/**
 * com.wills.help.assist.view
 * Created by lizhaoyong
 * 2017/1/13.
 */

public interface AssistView {
    void accept();
    void setAssistList(OrderList orderList);
}
