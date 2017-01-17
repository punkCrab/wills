package com.wills.help.home.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.db.bean.PointInfo;
import com.wills.help.db.manager.PointInfoHelper;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.release.model.Release;
import com.wills.help.release.presenter.ReleasePresenterImpl;
import com.wills.help.release.view.ReleaseView;
import com.wills.help.utils.ScreenUtils;
import com.wills.help.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2017/1/4.
 */

public class SendActivity extends BaseActivity implements View.OnClickListener ,ReleaseView{
    private TextView tv_choose_address;
    private EditText et_address;
    private Button btn_submit;
    String[] address;
    String[] addressId;
    private String desId;
    private ReleasePresenterImpl releasePresenter;
    private OrderInfo orderInfo;
    private boolean isUpdate = false;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_send);
        setBaseTitle(getString(R.string.home_express));
        tv_choose_address = (TextView) findViewById(R.id.tv_choose_address);
        et_address = (EditText) findViewById(R.id.et_address);
        et_address.addTextChangedListener(new EditTextChange());
        btn_submit = (Button) findViewById(R.id.btn_submit);
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
        }
        releasePresenter = new ReleasePresenterImpl(this);
        PointInfoHelper.getInstance().queryAll()
                .doOnNext(new Action1<List<PointInfo>>() {
                    @Override
                    public void call(List<PointInfo> pointInfos) {
                        address = new String[pointInfos.size()];
                        addressId = new String[pointInfos.size()];
                        for (int i=0;i<pointInfos.size();i++){
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
                        if (orderInfo!=null){
                            tv_choose_address.setText(orderInfo.getDesname());
                            desId = orderInfo.getDesid();
                        }else {
                            tv_choose_address.setText(address[0]);
                            desId = addressId[0];
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PointInfo> pointInfos) {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_address:
                showAlert(address,tv_choose_address);
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

    private Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
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
        return map;
    }

    private void showAlert(final String[] strings , final TextView textView){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                desId = addressId[i];
                textView.setText(strings[i]);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        if (strings.length>10){
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.height = 3* ScreenUtils.getScreenHeight(context)/4;
            dialog.getWindow().setAttributes(params);
        }
    }

    @Override
    public void setRelease(Release.OrderId order) {
        ToastUtils.toast("免费派送成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void updateOrder() {
        ToastUtils.toast("修改成功");
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
            if (!TextUtils.isEmpty(et_address.getText().toString())) {
                btn_submit.setEnabled(true);
                btn_submit.setBackgroundResource(R.drawable.btn_selector);
            } else {
                btn_submit.setEnabled(false);
                btn_submit.setBackgroundResource(R.color.button_gray);
            }
        }
    }
}
