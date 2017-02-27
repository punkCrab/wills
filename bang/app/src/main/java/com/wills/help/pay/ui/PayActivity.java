package com.wills.help.pay.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.pay.alipay.PayResult;
import com.wills.help.pay.presenter.PayPresenterImpl;
import com.wills.help.pay.view.PayView;
import com.wills.help.person.model.Wallet;
import com.wills.help.person.presenter.WalletPresenterImpl;
import com.wills.help.person.view.WalletView;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.utils.ToastUtils;
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

public class PayActivity extends BaseActivity implements PayView,WalletView{
    private TextView tv_amount , tv_send , tv_from , tv_balance_amount;
    private MyRadioGroup rg_pay;
    private RadioButton rb_ali,rb_wx,rb_balance;
    private Button button;
    private PayPresenterImpl payPresenter;
    private String orderId;
    private OrderInfo orderInfo;
    private WalletPresenterImpl walletPresenter;
    private int payType = 0;//1支付宝2微信

    private static final int BALANCE_PAY = 0;
    private static final int ALI_PAY = 1;
    private static final int WX_PAY = 2;
    private static final int PAY_SUCCESS = 3;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BALANCE_PAY:
                    handler.sendEmptyMessageDelayed(PAY_SUCCESS,1000);
                    break;
                case ALI_PAY:
                    PayResult payResult = new PayResult((Map<String,String>)msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus,"9000")){
                        handler.sendEmptyMessageDelayed(PAY_SUCCESS,1000);
                    }else {
                        Looper.prepare();
                        ToastUtils.toast("支付失败");
                        Looper.loop();
                    }
                    break;
                case WX_PAY:

                    break;
                case PAY_SUCCESS:
                    ToastUtils.toast("支付成功");
                    setResult(RESULT_OK);
                    finish();
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
        tv_balance_amount = (TextView) findViewById(R.id.tv_balance_amount);
        rg_pay = (MyRadioGroup) findViewById(R.id.rg_pay);
        rb_ali = (RadioButton) findViewById(R.id.rb_ali);
        rb_wx = (RadioButton) findViewById(R.id.rb_wx);
        rb_balance = (RadioButton) findViewById(R.id.rb_balance);
        button = (Button) findViewById(R.id.btn_submit);
        rg_pay.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_ali){
                    payType = 1;
                }else if (checkedId == R.id.rb_wx){
                    payType = 2;
                }else if (checkedId == R.id.rb_balance){
                    payType = 0;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payType == 0){
                    payPresenter.balancePay(getBalanceMap());
                }else {
                    payPresenter.paySign(getPayMap());
                }
            }
        });
    }

    private Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderId);
        map.put("action",String.valueOf(payType));
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
                "\"out_trade_no\":\"" + orderId +  "\"}");
        return map;
    }

    private Map<String,String> getBalanceMap(){
        Map<String,String> map = new HashMap<>();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderId);
        return map;
    }

    @Override
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        tv_from.setText(orderInfo.getSrcdetail());
        tv_send.setText(orderInfo.getDesdetail());
        tv_amount.setText(orderInfo.getMoney()+getString(R.string.yuan));
        walletPresenter = new WalletPresenterImpl(this);
        walletPresenter.getMoney(getWalletMap());
    }

    private Map<String ,String> getWalletMap(){
        Map<String , String> map = new HashMap<>();
        map.put("userid", App.getApp().getUser().getUserid());
        return map;
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
                handler.handleMessage(message);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void setBalancePay() {
        Message message = new Message();
        message.what = BALANCE_PAY;
        handler.handleMessage(message);
    }

    @Override
    public void setMoney(Wallet.Money money) {
        if (money.getMoney().equals("0")){
            rg_pay.findViewById(R.id.rb_balance).setVisibility(View.GONE);
            rb_ali.setChecked(true);
            payType = 1;
        }else {
            rg_pay.findViewById(R.id.rb_balance).setVisibility(View.VISIBLE);
            tv_balance_amount.setText(money.getMoney()+getString(R.string.yuan));
            rb_balance.setChecked(true);
            payType = 0;
        }
    }
}
