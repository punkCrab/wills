package com.wills.help.listener;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.utils.ToastUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * com.wills.help.listener
 * Created by lizhaoyong
 * 2016/12/27.
 */

public class MessageConnectionListener implements EMConnectionListener{
    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected(final int error) {
        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                if(error == EMError.USER_REMOVED){
                    // 显示帐号已经被移除
                }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                } else {
                    if (NetUtils.hasNetwork(App.getApp())){
                        //连接不到聊天服务器
                    }else{
                        //当前网络不可用，请检查网络设置
                        ToastUtils.toast(App.getApp().getString(R.string.network_unavailable));
                    }
                }
            }
        });
    }
}
