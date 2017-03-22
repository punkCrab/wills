package com.wills.help.release.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.db.bean.OrderTypeInfo;
import com.wills.help.db.bean.PointInfo;
import com.wills.help.db.manager.OrderTypeInfoHelper;
import com.wills.help.net.HttpMap;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.release.model.Release;
import com.wills.help.release.presenter.ReleasePresenterImpl;
import com.wills.help.release.view.ReleaseView;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.ScreenUtils;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.ToastUtils;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Wills on 2016/12/28.
 */

public class ReleaseActivity extends BaseActivity implements View.OnClickListener , ReleaseView{

    private TextView tv_release_state,tv_release_from,tv_release_send;
    private EditText et_release_from_address,et_release_money,et_release_send_address,et_release_remark;
    private Button btn_submit,btn_change;
    private int type = 0;//0修改1为你解邮
    private String orderType;
    private String srcId = "0";//求助
    private String desId = "0";//送达
    String[] state;
    String[] stateId;
    private OrderInfo orderInfo;
    private ReleasePresenterImpl releasePresenter;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.fragment_release);
        setBaseTitle(getString(R.string.release));
        orderInfo = (OrderInfo) getIntent().getExtras().getSerializable("orderInfo");
        initLayout();
        initListener();
        initData();
    }

    private void initLayout(){
        tv_release_state = (TextView) findViewById(R.id.tv_release_state);
        tv_release_from = (TextView) findViewById(R.id.tv_release_from);
        tv_release_send = (TextView) findViewById(R.id.tv_release_send);
        et_release_from_address = (EditText) findViewById(R.id.et_release_from_address);
        et_release_money = (EditText) findViewById(R.id.et_release_money);
        et_release_send_address = (EditText) findViewById(R.id.et_release_send_address);
        et_release_remark = (EditText) findViewById(R.id.et_release_remark);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setText(getString(R.string.release_state_change));
        btn_change = (Button) findViewById(R.id.btn_change);
        btn_change.setVisibility(View.VISIBLE);
    }

    private void initListener(){
        et_release_from_address.addTextChangedListener(new EditTextChange());
        et_release_send_address.addTextChangedListener(new EditTextChange());
        tv_release_send.addTextChangedListener(new EditTextChange());
        tv_release_from.addTextChangedListener(new EditTextChange());
        et_release_money.addTextChangedListener(new EditTextChange());
//        tv_release_state.setOnClickListener(this);
        tv_release_from.setOnClickListener(this);
        tv_release_send.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_change.setOnClickListener(this);
    }


    private void initData(){
        releasePresenter = new ReleasePresenterImpl(this);
        et_release_money.setText(orderInfo.getMoney()+getString(R.string.yuan));
        et_release_money.setEnabled(false);
        OrderTypeInfoHelper.getInstance().queryAll()
                .doOnNext(new Action1<List<OrderTypeInfo>>() {
                    @Override
                    public void call(List<OrderTypeInfo> orderTypeInfos) {
                        if (orderTypeInfos !=null&&orderTypeInfos.size()>0){
                            state = new String[orderTypeInfos.size()];
                            stateId = new String[orderTypeInfos.size()];
                            for (int i = 0; i < orderTypeInfos.size(); i++) {
                                state[i] = orderTypeInfos.get(i).getOrdertype();
                                stateId[i] = orderTypeInfos.get(i).getTypeid();
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<OrderTypeInfo>>() {
                    @Override
                    public void onCompleted() {
                        tv_release_state.setText(orderInfo.getOrdertypename());
                        orderType = orderInfo.getOrderid();
                        //初始化
                        tv_release_from.setText(orderInfo.getSrcname());
                        tv_release_send.setText(orderInfo.getDesname());
                        et_release_from_address.setText(orderInfo.getSrcdetail());
                        et_release_send_address.setText(orderInfo.getDesdetail());
                        et_release_from_address.setSelection(orderInfo.getSrcdetail().length());
                        srcId = orderInfo.getSrcid();
                        desId = orderInfo.getDesid();
                        et_release_remark.setText(orderInfo.getRemark());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<OrderTypeInfo> orderTypeInfos) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.tv_release_state:
//                showAlert(state,tv_release_state,0);
//                break;
            case R.id.tv_release_from:
                IntentUtils.startActivityForResult(ReleaseActivity.this,SelectPointActivity.class,405);
                break;
            case R.id.tv_release_send:
                IntentUtils.startActivityForResult(ReleaseActivity.this,SelectPointActivity.class,406);
                break;
            case R.id.btn_submit:
                releasePresenter.updateOrder(getMap());
                break;
            case R.id.btn_change:
                releasePresenter.cancelOrder(getDelMap());
                break;
        }
    }

    private Map<String, String> getMap() {
        HttpMap map = new HttpMap();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderInfo.getOrderid());
        map.put("ordertype", orderType);
        map.put("srcid", srcId);
        map.put("srcdetail", et_release_from_address.getText().toString());
        map.put("desid", desId);
        map.put("desdetail", et_release_send_address.getText().toString());
        map.put("maintype", "0");//默认订单
        map.put("remark", et_release_remark.getText().toString());
        return map.getMap();
    }

    private Map<String, String> getDelMap() {
        HttpMap map = new HttpMap();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderInfo.getOrderid());
        return map.getMap();
    }

    /**
     * @param strings
     * @param textView
     * @param type     0是类型 1 是求助地址 2是送达地址
     */
    private void showAlert(final String[] strings, final TextView textView, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (type) {
                    case 0:
                        orderType = stateId[i];
                        break;
                }
                textView.setText(strings[i]);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        if (strings.length > 10) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.height = 3 * ScreenUtils.getScreenHeight(context) / 4;
            dialog.getWindow().setAttributes(params);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 405&&resultCode==RESULT_OK){
            PointInfo pointInfo = (PointInfo) data.getSerializableExtra("point");
            srcId = pointInfo.getPosid();
            tv_release_from.setText(pointInfo.getPosname());
        }else if (requestCode == 406&&resultCode==RESULT_OK){
            PointInfo pointInfo = (PointInfo) data.getSerializableExtra("point");
            desId = pointInfo.getPosid();
            tv_release_send.setText(pointInfo.getPosname());
        }
    }

    @Override
    public void setRelease(Release.OrderId order) {

    }

    @Override
    public void updateOrder() {
        ToastUtils.toast(getString(R.string.change_success));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void deleteOrder() {
        ToastUtils.toast(getString(R.string.delete_success));
        setResult(RESULT_OK);
        finish();
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
}
