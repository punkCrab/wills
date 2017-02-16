package com.wills.help.listener;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.login.ui.LoginActivity;
import com.wills.help.utils.AppManager;
import com.wills.help.utils.IntentUtils;
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
                    App.getApp().exitApp();
                    AlertDialog.Builder builder = new AlertDialog.Builder(AppManager.getAppManager().currentActivity())
                            .setMessage(AppManager.getAppManager().currentActivity().getString(R.string.msg_break))
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AppManager.getAppManager().MsgBreak();
                                    IntentUtils.startActivityForResult(AppManager.getAppManager().currentActivity(), LoginActivity.class, 11);
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
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
