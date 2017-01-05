package com.wills.help.login.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.login.presenter.ResetPresenterImpl;
import com.wills.help.login.view.ResetView;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.TimeCountUtils;
import com.wills.help.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2017/1/5.
 */

public class ResetActivity extends BaseActivity implements View.OnClickListener, ResetView {
    private EditText et_register_phone, et_register_pwd, et_register_pwd_ok, et_register_code;
    private Button btn_code, btn_submit;
    private ResetPresenterImpl resetPresenter;
    private TextView tv_warn;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_register);
        setBaseTitle(getString(R.string.reset_password));
        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        et_register_pwd = (EditText) findViewById(R.id.et_register_pwd);
        et_register_pwd_ok = (EditText) findViewById(R.id.et_register_pwd_ok);
        et_register_code = (EditText) findViewById(R.id.et_register_code);
        tv_warn = (TextView) findViewById(R.id.tv_warn);
        et_register_phone.addTextChangedListener(new EditTextChange());
        et_register_pwd.addTextChangedListener(new EditTextChange());
        et_register_pwd_ok.addTextChangedListener(new EditTextChange());
        et_register_code.addTextChangedListener(new EditTextChange());
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_code.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setText(getString(R.string.reset_password));
        btn_submit.setOnClickListener(this);
        resetPresenter = new ResetPresenterImpl(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                String phone = et_register_phone.getText().toString();
                if (StringUtils.availablePhone(phone)){
                    TimeCountUtils utils = new TimeCountUtils(context, 60000, 1000, btn_code);
                    utils.start();
                    resetPresenter.getCode(et_register_phone.getText().toString());
                }else {
                    ToastUtils.toast(getString(R.string.phone_error));
                }
                break;
            case R.id.btn_submit:
                resetPresenter.reset(getMap());
                break;
        }
    }

    private Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("username", et_register_phone.getText().toString());
        map.put("password", et_register_pwd.getText().toString());
        map.put("value", et_register_code.getText().toString());
        return map;
    }

    @Override
    public void setReset() {
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
            if (!TextUtils.isEmpty(et_register_phone.getText().toString())&&
                    !TextUtils.isEmpty(et_register_pwd.getText().toString())&&
                    !TextUtils.isEmpty(et_register_pwd_ok.getText().toString())&&
                    !TextUtils.isEmpty(et_register_code.getText().toString())&&
                    et_register_pwd.getText().toString().equals(et_register_pwd_ok.getText().toString())){
                tv_warn.setVisibility(View.INVISIBLE);
                btn_submit.setEnabled(true);
                btn_submit.setBackgroundResource(R.drawable.btn_selector);
            }else {
                if (!TextUtils.isEmpty(et_register_pwd.getText().toString())&&
                        !TextUtils.isEmpty(et_register_pwd_ok.getText().toString())&&
                        !et_register_pwd.getText().toString().equals(et_register_pwd_ok.getText().toString())){
                    tv_warn.setVisibility(View.VISIBLE);
                }else {
                    tv_warn.setVisibility(View.INVISIBLE);
                }
                btn_submit.setEnabled(false);
                btn_submit.setBackgroundResource(R.color.button_gray);
            }
        }
    }
}
