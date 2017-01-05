package com.wills.help.login.presenter;

import java.util.Map;

/**
 * com.wills.help.login.presenter
 * Created by lizhaoyong
 * 2017/1/5.
 */

public interface ResetPresenter {

    void getCode(String phone);

    void reset(Map<String , String > map);

}
