package com.wills.help.assist.presenter;

import com.wills.help.assist.model.AssistModel;
import com.wills.help.assist.view.AssistView;
import com.wills.help.net.ApiSubscriber;
import com.wills.help.net.Empty;
import com.wills.help.release.model.OrderList;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.assist.presenter
 * Created by lizhaoyong
 * 2017/1/13.
 */

public class AssistPresenterImpl implements AssistPresenter{

    private AssistModel assistModel;
    private AssistView assistView;

    public AssistPresenterImpl(AssistView assistView) {
        this.assistView = assistView;
        assistModel = new AssistModel();
    }

    @Override
    public void accept(Map<String, String> map) {
        assistModel.Accept(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Empty>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Empty empty) {
                        assistView.accept();
                    }
                });
    }

    @Override
    public void getAssistList(Map<String, String> map) {
        assistModel.getAssistList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<OrderList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(OrderList orderList) {
                        assistView.setAssistList(orderList);
                    }
                });
    }
}
