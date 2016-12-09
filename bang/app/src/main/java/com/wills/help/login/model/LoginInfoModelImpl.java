package com.wills.help.login.model;


import com.wills.help.net.HttpManager;

import rx.Observable;

/**
 * com.lzy.mmd.mvptest.model
 * Created by lizhaoyong
 * 2016/10/20.
 */

public class LoginInfoModelImpl{

//    public Observable<LoginInfo> net_getLoginInfo(final Map<String ,String > map) {
    public Observable<LoginInfo> net_getLoginInfo(final String map) {
//        Observable observable = Observable.create(new Observable.OnSubscribe<LoginInfo>(){
//
//            @Override
//            public void call(final Subscriber<? super LoginInfo> subscriber) {
//                Retrofit retrofit = HttpManager.getInstance();
//                LoginInfoModel iLoginModel = retrofit.create(LoginInfoModel.class);
                ;
//                Call<LoginInfo> call = iLoginModel.net_getLoginInfo(map);
//                call.enqueue(new Callback<LoginInfo>() {
//                    @Override
//                    public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
//                        subscriber.onNext(response.body());
//                        subscriber.onCompleted();
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginInfo> call, Throwable t) {
//                        subscriber.onError(t);
//                    }
//                });
//            }
//        });
        return HttpManager.getInstance().create(LoginInfoModel.class).net_getLoginInfo(map);
    }

}
