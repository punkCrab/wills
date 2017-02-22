package com.wills.help.pay.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.pay.alipay.PayResult;
import com.wills.help.pay.presenter.PayPresenterImpl;
import com.wills.help.pay.view.PayView;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.widget.MyRadioGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
    private OrderInfo orderInfo;

    private static final int ALI_PAY = 1;
    private static final int WX_PAY = 2;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ALI_PAY:
                    PayResult payResult = new PayResult((Map<String,String>)msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    break;
                case WX_PAY:
                    break;
            }
        }
    };

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
                payPresenter.paySign(getPayMap());
//                setResult(RESULT_OK);
//                finish();
            }
        });
    }

    private Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderId);
        return map;
    }

    private Map<String,String> getPayMap(){
        Map<String,String> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String time = format.format(date);
        map.put("timestamp", time);
        map.put("biz_content",
                "{\"timeout_express\":\"30m\"," +
                "\"subject\":\""+orderInfo.getOrdertype()+"\"," +
                "\"product_code\":\"QUICK_MSECURITY_PAY\"," +
                "\"total_amount\":\""+orderInfo.getMoney()+"\"," +
                "\"body\":\""+orderInfo.getOrdertype()+"\"," +
                "\"out_trade_no\":\"" + orderId +  "\"}\"");
        return map;
    }

    @Override
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        tv_from.setText(orderInfo.getSrcdetail());
        tv_send.setText(orderInfo.getDesdetail());
        tv_amount.setText(orderInfo.getMoney()+getString(R.string.yuan));
    }

    @Override
    public void setPaySign(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(PayActivity.this);
                Map<String, String> result = payTask.payV2(orderInfo,true);

                Message message = new Message();
                message.what = ALI_PAY;
                message.obj = result;
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
