package com.wills.help.release.ui;

import android.content.DialogInterface;
import android.content.Intent;
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

import static android.app.Activity.RESULT_OK;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class ReleaseFragment extends BaseFragment implements View.OnClickListener, ReleaseView {

    private TextView tv_release_state, tv_release_from, tv_release_send;
    private EditText et_release_from_address, et_release_money, et_release_send_address,et_release_remark;
    private Button btn_submit;
    private ReleasePresenterImpl releasePresenter;
    private String orderType;
    private String orderTypeName;
    private String srcId = "0";//求助
    private String desId = "0";//送达
    String[] state;
    String[] stateId;
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
        et_release_remark = (EditText) view.findViewById(R.id.et_release_remark);
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
                        orderType = stateId[orderIndex];
                        orderTypeName = state[orderIndex];
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<OrderTypeInfo> orderTypeInfos) {

                    }
                });
    }

    private void clearData(){
        tv_release_state.setText(state[orderIndex]);
        tv_release_from.setText(getString(R.string.select_point_click));
        tv_release_send.setText(getString(R.string.select_point_click));
        orderType = stateId[orderIndex];
        orderTypeName = state[orderIndex];
        srcId = "0";
        desId = "0";
        et_release_from_address.getText().clear();
        et_release_money.getText().clear();
        et_release_send_address.getText().clear();
        et_release_remark.getText().clear();
    }

    private void initListener() {
        tv_release_state.setOnClickListener(this);
        tv_release_from.setOnClickListener(this);
        tv_release_send.setOnClickListener(this);
        et_release_from_address.addTextChangedListener(new EditTextChange());
        et_release_send_address.addTextChangedListener(new EditTextChange());
        tv_release_send.addTextChangedListener(new EditTextChange());
        tv_release_from.addTextChangedListener(new EditTextChange());
        et_release_money.addTextChangedListener(new EditTextChange(1));
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_release_state:
                showAlert(state, tv_release_state, 0);
                break;
            case R.id.tv_release_from:
                IntentUtils.startActivityForResult(getAppCompatActivity(),SelectPointActivity.class,402);
                break;
            case R.id.tv_release_send:
                IntentUtils.startActivityForResult(getAppCompatActivity(),SelectPointActivity.class,403);
                break;
            case R.id.btn_submit:
                releasePresenter.release(getMap(),2);
                break;
        }
    }

    private Map<String, String> getMap() {
        HttpMap map = new HttpMap();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("ordertype", orderType);
        map.put("ordertypename", orderTypeName);
        map.put("srcid", srcId);
        map.put("srcdetail", et_release_from_address.getText().toString());
        map.put("desid", desId);
        map.put("desdetail", et_release_send_address.getText().toString());
        map.put("money", et_release_money.getText().toString());
        map.put("maintype", "0");//默认订单
        map.put("remark", et_release_remark.getText().toString());
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
                        orderTypeName = state[i];
                        break;
//                    case 1:
//                        srcId = addressId[i];
//                        break;
//                    case 2:
//                        desId = addressId[i];
//                        break;
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

    @Override
    public void deleteOrder() {

    }

    public class EditTextChange implements TextWatcher {

        private int type = 0;//输入金额

        public EditTextChange(int type) {
            this.type = type;
        }

        public EditTextChange() {
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            if (type == 1){
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2);
                        et_release_money.setText(s);
                        et_release_money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    et_release_money.setText(s);
                    et_release_money.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et_release_money.setText(s.subSequence(0, 1));
                        et_release_money.setSelection(1);
                        return;
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!StringUtils.isNullOrEmpty(et_release_from_address.getText().toString())
                    && !StringUtils.isNullOrEmpty(et_release_money.getText().toString())
                    && !StringUtils.isNullOrEmpty(et_release_send_address.getText().toString())
                    &&!srcId.equals("0")
                    &&!desId.equals("0")) {
                btn_submit.setEnabled(true);
                btn_submit.setBackgroundResource(R.drawable.btn_selector);
            } else {
                btn_submit.setEnabled(false);
                btn_submit.setBackgroundResource(R.color.button_gray);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 402 && resultCode == RESULT_OK){
            PointInfo info = (PointInfo) data.getSerializableExtra("point");
            srcId = info.getPosid();
            tv_release_from.setText(info.getPosname());
        }else if (requestCode == 403 && resultCode == RESULT_OK){
            PointInfo info = (PointInfo) data.getSerializableExtra("point");
            desId = info.getPosid();
            tv_release_send.setText(info.getPosname());
        }
    }
}
