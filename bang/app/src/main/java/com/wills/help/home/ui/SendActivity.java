package com.wills.help.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.db.bean.PointInfo;
import com.wills.help.net.HttpMap;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.release.model.Release;
import com.wills.help.release.presenter.ReleasePresenterImpl;
import com.wills.help.release.ui.SelectPointActivity;
import com.wills.help.release.view.ReleaseView;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.ToastUtils;

import java.util.Map;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2017/1/4.
 */

public class SendActivity extends BaseActivity implements View.OnClickListener ,ReleaseView{
    private TextView tv_choose_address;
    private EditText et_address,et_release_remark;
    private Button btn_submit;
    private String desId = "0";
    private ReleasePresenterImpl releasePresenter;
    private OrderInfo orderInfo;
    private boolean isUpdate = false;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_send);
        setBaseTitle(getString(R.string.home_express));
        tv_choose_address = (TextView) findViewById(R.id.tv_choose_address);
        et_address = (EditText) findViewById(R.id.et_address);
        tv_choose_address.addTextChangedListener(new EditTextChange());
        et_address.addTextChangedListener(new EditTextChange());
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_release_remark = (EditText)findViewById(R.id.et_release_remark);
        tv_choose_address.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        initData();
    }

    private void initData(){
        if (getIntent().getExtras()!=null){
            isUpdate = true;
            orderInfo = (OrderInfo) getIntent().getExtras().getSerializable("orderInfo");
            et_address.setText(orderInfo.getDesdetail());
            et_address.setSelection(orderInfo.getDesdetail().length());
            tv_choose_address.setText(orderInfo.getDesname());
            desId = orderInfo.getDesid();
        }else {
            desId = "0";
        }
        releasePresenter = new ReleasePresenterImpl(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_address:
                IntentUtils.startActivityForResult(SendActivity.this,SelectPointActivity.class,410);
                break;
            case R.id.btn_submit:
                if (isUpdate){
                    releasePresenter.updateOrder(getMap());
                }else {
                    releasePresenter.release(getMap());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 410 && resultCode == RESULT_OK){
            PointInfo info = (PointInfo) data.getSerializableExtra("point");
            desId = info.getPosid();
            tv_choose_address.setText(info.getPosname());
        }
    }

    private Map<String, String> getMap() {
        HttpMap map = new HttpMap();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        if (isUpdate){
            map.put("orderid", orderInfo.getOrderid());
        }
        map.put("ordertype", "1");
        map.put("srcid", "64");
        map.put("srcdetail", getString(R.string.send_site));
        map.put("desid", desId);
        map.put("desdetail", et_address.getText().toString());
        map.put("money", "0");
        map.put("maintype", "1");
        map.put("remark", et_release_remark.getText().toString());
        return map.getMap();
    }

    @Override
    public void setRelease(Release.OrderId order) {
        ToastUtils.toast(getString(R.string.release_free));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void updateOrder() {
        ToastUtils.toast(getString(R.string.change_success));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void deleteOrder() {

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
            if (!StringUtils.isNullOrEmpty(et_address.getText().toString())
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
