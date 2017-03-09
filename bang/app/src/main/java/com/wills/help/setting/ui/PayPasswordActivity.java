package com.wills.help.setting.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.widget.PayPwdEditText;

/**
 * com.wills.help.setting.ui
 * Created by lizhaoyong
 * 2017/3/9.
 */

public class PayPasswordActivity extends BaseActivity{
    PayPwdEditText et_password;
    Button btn_submit;
    String pwd1,pwd2;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_pay_password);
        setBaseTitle(getString(R.string.setting_pay_pwd));
        et_password = (PayPwdEditText) findViewById(R.id.et_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_password.initStyle(R.drawable.pay_shape,6,1.0f,R.color.colorPrimaryDark,R.color.colorPrimaryDark,20);
        et_password.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                if (pwd1 == null){
                    pwd1 = str;
                    et_password.clearText();
                    btn_submit.setVisibility(View.VISIBLE);
                    btn_submit.setEnabled(false);
                    btn_submit.setBackgroundResource(R.color.button_gray);
                }else {
                    pwd2 = str;
                    btn_submit.setEnabled(true);
                    btn_submit.setBackgroundResource(R.drawable.btn_selector);
                }
            }
        });
        et_password.post(new Runnable() {
            @Override
            public void run() {
                et_password.setFocus();
            }
        });
    }
}
