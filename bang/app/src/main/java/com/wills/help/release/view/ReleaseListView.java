package com.wills.help.release.view;

import com.wills.help.release.model.OrderList;

/**
 * com.wills.help.release.view
 * Created by lizhaoyong
 * 2017/1/10.
 */

public interface ReleaseListView {
    void setReleaseList(OrderList releaseList);
    void confirm();
    void exec();
}
