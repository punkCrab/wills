package com.wills.help.person.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.person.model.IdCheckModel;
import com.wills.help.person.view.IdCheckView;
import com.wills.help.utils.NetUtils;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.person.presenter
 * Created by lizhaoyong
 * 2017/3/3.
 */

public class IdCheckPresenterImpl implements IdCheckPresenter{

    private IdCheckModel idCheckModel;
    private IdCheckView idCheckView;

    public IdCheckPresenterImpl(IdCheckView idCheckView) {
        this.idCheckView = idCheckView;
        idCheckModel = new IdCheckModel();
    }

    @Override
    public void idCheck(Map<String, String> map) {
        final AlertDialog dialog = NetUtils.netDialog((Context) idCheckView);
        idCheckModel.idCheck(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Empty empty) {
                        idCheckView.setIdCheck();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }
                });
    }
}
