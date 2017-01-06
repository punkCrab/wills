package com.wills.help.login.model;

import com.wills.help.net.Empty;
import com.wills.help.net.HttpManager;

import java.util.Map;

import rx.Observable;

/**
 * com.wills.help.login.model
 * Created by lizhaoyong
 * 2017/1/5.
 */

public class ResetModelImpl {
    private ResetModel resetModel;

    public ResetModelImpl() {
        resetModel = HttpManager.getInstance().create(ResetModel.class);
    }

    public Observable<Empty> getCode(String phone){
        return resetModel.getCode(phone);
    }

    public Observable<Empty> reset(Map<String,String> map){
        return resetModel.reset(map);
    }
}
