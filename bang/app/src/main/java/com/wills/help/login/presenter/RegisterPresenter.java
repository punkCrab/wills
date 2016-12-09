package com.wills.help.login.presenter;

import java.util.Map;

/**
 * com.wills.help.login.presenter
 * Created by lizhaoyong
 * 2016/12/9.
 */

public interface RegisterPresenter {

    void getCode(String phone);

    void register(Map<String , String > map);

}
