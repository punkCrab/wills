package com.wills.help.pay.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.net.HttpMap;
import com.wills.help.pay.alipay.PayResult;
import com.wills.help.pay.model.WXPaySign;
import com.wills.help.pay.presenter.PayPresenterImpl;
import com.wills.help.pay.view.PayView;
import com.wills.help.person.model.Wallet;
import com.wills.help.person.presenter.WalletPresenterImpl;
import com.wills.help.person.view.WalletView;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.setting.ui.PayPasswordActivity;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.KeyBoardUtils;
import com.wills.help.utils.NetUtils;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.ToastUtils;
import com.wills.help.widget.MyRadioGroup;
import com.wills.help.widget.PayPwdEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * com.wills.help.pay.ui
 * Created by lizhaoyong
 * 2016/12/16.
 */

public class PayActivity extends BaseActivity implements PayView , WalletView{
    private TextView tv_amount , tv_send , tv_from , tv_balance_amount ,tv_pay_state;
    private MyRadioGroup rg_pay;
    private RelativeLayout rl_balance;
    private RadioButton rb_ali,rb_wx,rb_balance;
    private Button button;
    private PayPresenterImpl payPresenter;
    private String orderId;
    private OrderInfo orderInfo;
    private WalletPresenterImpl walletPresenter;
    private int payType = 0;//1支付宝2微信
    private float amount;//我的钱
    private boolean isClickPay = false;//是否点过支付
    private boolean isPaySuccess =false;//是否支付成功

    private static final int ALI_PAY = 1;
    private static final int PAY_SUCCESS = 2;

    private AlertDialog dialog;

    private String payPassword;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ALI_PAY:
                    PayResult payResult = new PayResult((Map<String,String>)msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus,"9000")){
                        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
                            @Override
                            public void call() {
                                dialog = NetUtils.netDialog(context);
                            }
                        });
                        handler.sendEmptyMessageDelayed(PAY_SUCCESS,1000);
                    }else {
                        Looper.prepare();
                        ToastUtils.toast(getString(R.string.pay_fail));
                        Looper.loop();
                    }
                    break;
                case PAY_SUCCESS:
                    payPresenter.getOrderInfo(getMap());
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
        tv_pay_state = (TextView) findViewById(R.id.tv_pay_state);
        rg_pay = (MyRadioGroup) findViewById(R.id.rg_pay);
        rb_ali = (RadioButton) findViewById(R.id.rb_ali);
        rb_wx = (RadioButton) findViewById(R.id.rb_wx);
        rb_balance = (RadioButton) findViewById(R.id.rb_balance);
        rl_balance = (RelativeLayout) findViewById(R.id.rl_balance);
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
                if (isPaySuccess){
                    setResult(RESULT_OK);
                    finish();
                }else {
                    isClickPay = true;
                    if (payType == 0){
                        if (StringUtils.isNullOrEmpty(App.getApp().getUser().getPaypwd())){
                            showOk();
                        }else {
                            payDialog(context);
                        }
                    }else if (payType == 1){
                        payPresenter.AliPaySign(getPayMap());
                    }else if (payType == 2){
                        payPresenter.WXPaySign(getPayMap());
                    }
                }
            }
        });
    }

    private void showOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.pay_balance_warn))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.setting_set_pay_pwd), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        IntentUtils.startActivity(PayActivity.this, PayPasswordActivity.class);
                    }
                }).show();
    }


    public AlertDialog payDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_pay,null);
        final PayPwdEditText et_password = (PayPwdEditText) view.findViewById(R.id.et_password);
        et_password.initStyle(R.drawable.pay_shape,6,1.0f,R.color.colorPrimaryDark,R.color.colorPrimaryDark,20);
        final AlertDialog dialog = builder.setView(view).create();
        if (!((Activity)context).isFinishing()){
            dialog.show();
        }
        et_password.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                payPassword = str;
                if (App.getApp().getUser().getPaypwd().equals(StringUtils.getMD5(str))){
                    payPresenter.balancePay(getBalanceMap());
                    KeyBoardUtils.closeKeybord(context,et_password.getEditText());
                    dialog.dismiss();
                }else {
                    ToastUtils.toast(getString(R.string.pay_pwd_error));
                }
            }
        });
        et_password.post(new Runnable() {
            @Override
            public void run() {
                et_password.setFocus();
            }
        });
        return dialog;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isClickPay && payType == 2){
            dialog = NetUtils.netDialog(context);
            if (payPresenter == null){
                payPresenter = new PayPresenterImpl(this);
            }
            handler.sendEmptyMessageDelayed(PAY_SUCCESS,1000);
        }
    }

    private Map<String,String> getMap(){
        HttpMap map = new HttpMap();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderId);
        return map.getMap();
    }

    private Map<String,String> getPayMap(){
        HttpMap map = new HttpMap();
        map.put("action",String.valueOf(payType));
        if (payType == 1){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            String time = format.format(date);
            map.put("timestamp", time);
            map.put("biz_content",
                    "{\"timeout_express\":\"30m\"," +
                            "\"subject\":\""+orderInfo.getOrdertypename()+"\"," +
                            "\"product_code\":\"QUICK_MSECURITY_PAY\"," +
                            "\"total_amount\":\""+orderInfo.getMoney()+"\"," +
                            "\"body\":\""+orderInfo.getOrdertypename()+"\"," +
                            "\"out_trade_no\":\"" + orderId +  "\"}");
        }else if (payType == 2) {
            map.put("body", orderInfo.getOrdertypename());
            map.put("out_trade_no", orderId);
            map.put("total_fee", String.valueOf((int)(Double.parseDouble(orderInfo.getMoney())*100)));
        }
        return map.getMap();
    }

    private Map<String,String> getBalanceMap(){
        HttpMap map = new HttpMap();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderId);
        map.put("paypwd", payPassword);
        return map.getMap();
    }

    @Override
    public void setOrderInfo(OrderInfo orderInfo,boolean state) {
        if (state){
            this.orderInfo = orderInfo;
            tv_from.setText(orderInfo.getSrcdetail());
            tv_send.setText(orderInfo.getDesdetail());
            tv_amount.setText(orderInfo.getMoney()+getString(R.string.yuan));
            walletPresenter = new WalletPresenterImpl(this);
            walletPresenter.getMoney(getWalletMap());
            if (isClickPay){
                if (dialog!=null){
                    dialog.dismiss();
                }
                tv_pay_state.setVisibility(View.VISIBLE);
                Drawable drawable = null;
                if (orderInfo.getStateid().equals("1")){
                    isPaySuccess = true;
                    button.setText(getString(R.string.ok));
                    drawable = context.getResources().getDrawable(R.drawable.pay_success);
                    tv_pay_state.setTextColor(R.color.textPrimary);
                    tv_pay_state.setText(getString(R.string.pay_success));
                    rg_pay.setVisibility(View.GONE);
                }else if (orderInfo.getStateid().equals("0")){
                    button.setText(getString(R.string.pay));
                    drawable = context.getResources().getDrawable(R.drawable.pay_fail);
                    tv_pay_state.setTextColor(R.color.red);
                    tv_pay_state.setText(getString(R.string.pay_fail));
                    rg_pay.setVisibility(View.VISIBLE);
                }
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                tv_pay_state.setCompoundDrawables(drawable,null,null,null);
            }else {
                tv_pay_state.setVisibility(View.GONE);
                rg_pay.setVisibility(View.VISIBLE);
            }
        }else {
            if (dialog!=null){
                dialog.dismiss();
            }
        }
    }

    private Map<String ,String> getWalletMap(){
        HttpMap map = new HttpMap();
        map.put("userid", App.getApp().getUser().getUserid());
        return map.getMap();
    }

    @Override
    public void setAliPaySign(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(PayActivity.this);
                Map<String, String> result = payTask.payV2(orderInfo, true);

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
        message.what = PAY_SUCCESS;
        handler.handleMessage(message);
    }

    @Override
    public void setWXPaySign(WXPaySign.WXPayInfo wxPaySign) {
        AppConfig.WX_APP_ID = wxPaySign.getAppid();
        IWXAPI iWXApi = WXAPIFactory.createWXAPI(context,wxPaySign.getAppid());
        PayReq request = new PayReq();
        request.appId = wxPaySign.getAppid();
        request.partnerId = wxPaySign.getPartnerid();
        request.nonceStr = wxPaySign.getNoncestr();
        request.sign = wxPaySign.getSign();
        request.packageValue = wxPaySign.getPackageValue();
        request.timeStamp = wxPaySign.getTimestamp();
        request.prepayId = wxPaySign.getPrepayid();
        iWXApi.sendReq(request);
    }



    @Override
    public void setMoney(Wallet.Money money) {
        amount = Float.parseFloat(money.getMoney());
        if (money.getMoney().equals("0")){
            rl_balance.setVisibility(View.GONE);
            rb_ali.setChecked(true);
            payType = 1;
        }else {
            tv_balance_amount.setText(money.getMoney()+getString(R.string.yuan));
            if (amount<Float.parseFloat(orderInfo.getMoney())){
                rb_balance.setEnabled(false);
                rb_balance.setVisibility(View.GONE);
                rb_ali.setChecked(true);
                payType = 1;
            }else {
                rl_balance.setVisibility(View.VISIBLE);
                rb_balance.setChecked(true);
                payType = 0;
            }
        }
    }

}
