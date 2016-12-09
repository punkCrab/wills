package com.wills.help.login.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.TimeCountUtils;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2016/12/8.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_register_phone,et_register_pwd,et_register_pwd_ok,et_register_code;
    private Button btn_code,btn_submit;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_register);
        setBaseTitle(getString(R.string.register));
        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        et_register_pwd = (EditText) findViewById(R.id.et_register_pwd);
        et_register_pwd_ok = (EditText) findViewById(R.id.et_register_pwd_ok);
        et_register_code = (EditText) findViewById(R.id.et_register_code);
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_code.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_code:
                TimeCountUtils utils = new TimeCountUtils(context , 600000,1000,btn_code);
                utils.start();
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
