package com.wills.help.setting.presenter;

import com.wills.help.net.ApiSubscriber;
import com.wills.help.setting.model.VersionCheck;
import com.wills.help.setting.model.VersionCheckModel;
import com.wills.help.setting.view.VersionCheckView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.setting.presenter
 * Created by lizhaoyong
 * 2017/3/23.
 */

public class VersionCheckPresenterImpl implements VersionCheckPresenter{
    VersionCheckModel versionCheckModel;
    VersionCheckView versionCheckView;

    public VersionCheckPresenterImpl(VersionCheckView versionCheckView) {
        this.versionCheckView = versionCheckView;
        versionCheckModel = new VersionCheckModel();
    }

    @Override
    public void versionCheck() {
        versionCheckModel.versionCheck()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<VersionCheck>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(VersionCheck versionCheck) {
                        versionCheckView.setVersionCheck(versionCheck.getData());
                    }
                });
    }
}
