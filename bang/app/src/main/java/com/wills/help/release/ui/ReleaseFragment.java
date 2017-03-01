package com.wills.help.release.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseFragment;
import com.wills.help.db.bean.OrderTypeInfo;
import com.wills.help.db.bean.PointInfo;
import com.wills.help.db.manager.OrderTypeInfoHelper;
import com.wills.help.db.manager.PointInfoHelper;
import com.wills.help.net.HttpMap;
import com.wills.help.pay.ui.PayActivity;
import com.wills.help.release.model.Release;
import com.wills.help.release.presenter.ReleasePresenterImpl;
import com.wills.help.release.view.ReleaseView;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.ScreenUtils;
import com.wills.help.utils.StringUtils;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class ReleaseFragment extends BaseFragment implements View.OnClickListener, ReleaseView {

    private TextView tv_release_state, tv_release_from, tv_release_send;
    private EditText et_release_from_address, et_release_money, et_release_send_address;
    private Button btn_submit;
    private ReleasePresenterImpl releasePresenter;
    private String orderType;
    private String srcId;//求助
    private String desId;//送达
    String[] state;
    String[] stateId;
    String[] address;
    String[] addressId;
    private int orderIndex;

    public static ReleaseFragment newInstance(int stateId) {

        Bundle args = new Bundle();
        args.putInt("stateId",stateId);
        ReleaseFragment fragment = new ReleaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_release, null);
        tv_release_state = (TextView) view.findViewById(R.id.tv_release_state);
        tv_release_from = (TextView) view.findViewById(R.id.tv_release_from);
        tv_release_send = (TextView) view.findViewById(R.id.tv_release_send);
        et_release_from_address = (EditText) view.findViewById(R.id.et_release_from_address);
        et_release_money = (EditText) view.findViewById(R.id.et_release_money);
        et_release_send_address = (EditText) view.findViewById(R.id.et_release_send_address);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        orderIndex = getArguments().getInt("stateId",0);
        releasePresenter = new ReleasePresenterImpl(this);
        initListener();
        OrderTypeInfoHelper.getInstance().queryAll()
                .doOnNext(new Action1<List<OrderTypeInfo>>() {
                    @Override
                    public void call(List<OrderTypeInfo> orderTypeInfos) {
                        state = new String[orderTypeInfos.size()];
                        stateId = new String[orderTypeInfos.size()];
                        for (int i = 0; i < orderTypeInfos.size(); i++) {
                            state[i] = orderTypeInfos.get(i).getOrdertype();
                            stateId[i] = orderTypeInfos.get(i).getTypeid();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<OrderTypeInfo>>() {
                    @Override
                    public void onCompleted() {
                        tv_release_state.setText(state[orderIndex]);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<OrderTypeInfo> orderTypeInfos) {

                    }
                });
        PointInfoHelper.getInstance().queryAll()
                .doOnNext(new Action1<List<PointInfo>>() {
                    @Override
                    public void call(List<PointInfo> pointInfos) {
                        address = new String[pointInfos.size()];
                        addressId = new String[pointInfos.size()];
                        for (int i = 0; i < pointInfos.size(); i++) {
                            address[i] = pointInfos.get(i).getPosname();
                            addressId[i] = pointInfos.get(i).getPosid();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PointInfo>>() {
                    @Override
                    public void onCompleted() {
                        //初始化
                        tv_release_from.setText(address[0]);
                        tv_release_send.setText(address[0]);
                        orderType = stateId[orderIndex];
                        srcId = addressId[0];
                        desId = addressId[0];
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PointInfo> pointInfos) {

                    }
                });
    }

    private void clearData(){
        tv_release_state.setText(state[orderIndex]);
        tv_release_from.setText(address[0]);
        tv_release_send.setText(address[0]);
        orderType = stateId[orderIndex];
        srcId = addressId[0];
        desId = addressId[0];
        et_release_from_address.getText().clear();
        et_release_money.getText().clear();
        et_release_send_address.getText().clear();
    }

    private void initListener() {
        tv_release_state.setOnClickListener(this);
        tv_release_from.setOnClickListener(this);
        tv_release_send.setOnClickListener(this);
        et_release_from_address.addTextChangedListener(new EditTextChange());
        et_release_send_address.addTextChangedListener(new EditTextChange());
        et_release_money.addTextChangedListener(new EditTextChange());
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_release_state:
                showAlert(state, tv_release_state, 0);
                break;
            case R.id.tv_release_from:
                showAlert(address, tv_release_from, 1);
                break;
            case R.id.tv_release_send:
                showAlert(address, tv_release_send, 2);
                break;
            case R.id.btn_submit:
                releasePresenter.release(getMap());
                break;
        }
    }

    private Map<String, String> getMap() {
        HttpMap map = new HttpMap();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("ordertype", orderType);
        map.put("srcid", srcId);
        map.put("srcdetail", et_release_from_address.getText().toString());
        map.put("desid", desId);
        map.put("desdetail", et_release_send_address.getText().toString());
        map.put("money", et_release_money.getText().toString());
        map.put("maintype", "0");//默认订单
        return map.getMap();
    }

    /**
     * @param strings
     * @param textView
     * @param type     0是类型 1 是求助地址 2是送达地址
     */
    private void showAlert(final String[] strings, final TextView textView, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getAppCompatActivity());
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (type) {
                    case 0:
                        orderType = stateId[i];
                        break;
                    case 1:
                        srcId = addressId[i];
                        break;
                    case 2:
                        desId = addressId[i];
                        break;
                }
                textView.setText(strings[i]);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        if (strings.length > 10) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.height = 3 * ScreenUtils.getScreenHeight(getAppCompatActivity()) / 4;
            dialog.getWindow().setAttributes(params);
        }
    }

    @Override
    public void setRelease(Release.OrderId order) {
        clearData();
        Bundle bundle = new Bundle();
        bundle.putString("orderId",order.getOrderid());
        IntentUtils.startActivityForResult(getAppCompatActivity(), PayActivity.class,bundle,401);
    }

    @Override
    public void updateOrder() {

    }

    public class EditTextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!StringUtils.isNullOrEmpty(et_release_from_address.getText().toString())
                    && !StringUtils.isNullOrEmpty(et_release_money.getText().toString())
                    && !StringUtils.isNullOrEmpty(et_release_send_address.getText().toString())) {
                btn_submit.setEnabled(true);
                btn_submit.setBackgroundResource(R.drawable.btn_selector);
            } else {
                btn_submit.setEnabled(false);
                btn_submit.setBackgroundResource(R.color.button_gray);
            }
        }
    }
}
