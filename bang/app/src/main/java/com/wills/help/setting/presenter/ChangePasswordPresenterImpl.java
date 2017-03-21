package com.wills.help.setting.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.wills.help.setting.model.ChangePasswordModel;
import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.setting.view.ChangePasswordView;
import com.wills.help.utils.NetUtils;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.login.presenter
 * Created by lizhaoyong
 * 2017/3/21.
 */

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter{

    ChangePasswordView changePasswordView;
    ChangePasswordModel changePasswordModel;

    public ChangePasswordPresenterImpl(ChangePasswordView changePasswordView) {
        this.changePasswordView = changePasswordView;
        changePasswordModel = new ChangePasswordModel();
    }

    @Override
    public void changePassword(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog((Context) changePasswordView);
        changePasswordModel.changePassword(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Empty empty) {
                        changePasswordView.setChangePassword();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }
}
