package com.wills.help.login.presenter;

import com.wills.help.login.model.RegisterModelImpl;
import com.wills.help.login.view.RegisterView;
import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.utils.ToastUtils;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.login.presenter
 * Created by lizhaoyong
 * 2016/12/9.
 */

public class RegisterPresenterImpl implements RegisterPresenter{
    private RegisterView registerView;
    private RegisterModelImpl registerModel;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        registerModel = new RegisterModelImpl();
    }

    @Override
    public void getCode(String phone) {
        registerModel.getCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {
                        ToastUtils.toast("完成");
                    }

                    @Override
                    public void onNext(Empty empty) {
                        ToastUtils.toast("成功");
                    }
                });
    }

    @Override
    public void register(Map<String, String> map) {
        registerModel.submitRegister(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Empty o) {

                    }
                });
    }
}
