package com.wills.help.pay.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.pay.presenter.PayPresenterImpl;
import com.wills.help.pay.view.PayView;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.widget.MyRadioGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * com.wills.help.pay.ui
 * Created by lizhaoyong
 * 2016/12/16.
 */

public class PayActivity extends BaseActivity implements PayView{
    private TextView tv_amount , tv_send , tv_from;
    private MyRadioGroup rg_pay;
    private Button button;
    private PayPresenterImpl payPresenter;
    private String orderId;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_pay);
        setBaseTitle(getString(R.string.pay));
        orderId = getIntent().getExtras().getString("orderId");
        payPresenter = new PayPresenterImpl(this);
        payPresenter.getOrderInfo(getMap());
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_from = (TextView) findViewById(R.id.tv_from);
        rg_pay = (MyRadioGroup) findViewById(R.id.rg_pay);
        button = (Button) findViewById(R.id.btn_submit);
        rg_pay.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_ali){

                }else if (checkedId == R.id.rb_wx){

                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderId);
        return map;
    }

    @Override
    public void setOrderInfo(OrderInfo orderInfo) {
        tv_from.setText(orderInfo.getSrcdetail());
        tv_send.setText(orderInfo.getDesdetail());
        tv_amount.setText(orderInfo.getMoney()+getString(R.string.yuan));
    }
}
