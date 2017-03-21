package com.wills.help.person.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.net.HttpMap;
import com.wills.help.person.presenter.WithdrawPresenterImpl;
import com.wills.help.person.view.WithdrawView;
import com.wills.help.utils.KeyBoardUtils;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.ToastUtils;
import com.wills.help.widget.PayPwdEditText;

import java.util.Map;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class WithdrawActivity extends BaseActivity implements WithdrawView{
    private TextView tv_zfb,tv_amount;
    private EditText et_amount;
    private Button btn_submit;
    private WithdrawPresenterImpl withdrawPresenter;
    private float amount;
    private String payPassword;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_withdraw);
        setBaseTitle(getString(R.string.money_withdraw));
        amount = getIntent().getExtras().getFloat("amount");
        tv_zfb = (TextView) findViewById(R.id.tv_zfb);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        et_amount = (EditText) findViewById(R.id.et_amount);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tv_zfb.setText(StringUtils.getZFB(App.getApp().getUser().getAliaccount()));
        tv_amount.setText(String.format(getString(R.string.money_withdraw_warn),String.valueOf(amount)));
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Float.parseFloat(et_amount.getText().toString())>amount){
                    ToastUtils.toast(getString(R.string.money_withdraw_error));
                }else {
                    payDialog(context);
                }
            }
        });
    }

    private Map<String ,String> getMap(){
        HttpMap map = new HttpMap();
        map.put("userid", App.getApp().getUser().getUserid());
        map.put("paypwd", payPassword);
        map.put("money", et_amount.getText().toString());
        return map.getMap();
    }

    @Override
    public void setWithdraw() {
        ToastUtils.toast(getString(R.string.money_withdraw_success));
        setResult(RESULT_OK);
        finish();
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
                    if (withdrawPresenter == null){
                        withdrawPresenter = new WithdrawPresenterImpl(WithdrawActivity.this);
                    }
                    withdrawPresenter.withdraw(getMap());
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
}
