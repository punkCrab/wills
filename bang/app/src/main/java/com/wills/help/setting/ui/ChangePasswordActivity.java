package com.wills.help.setting.ui;

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
import com.wills.help.net.HttpMap;
import com.wills.help.setting.presenter.ChangePasswordPresenterImpl;
import com.wills.help.setting.view.ChangePasswordView;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.ToastUtils;

import java.util.Map;

/**
 * com.wills.help.setting.ui
 * Created by lizhaoyong
 * 2017/3/21.
 */

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordView{

    EditText et_pwd_original,et_pwd_new,et_pwd_new_ok;
    TextView tv_warn;
    Button btn_submit;
    ChangePasswordPresenterImpl changePasswordPresenter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_changepwd);
        setBaseTitle(getString(R.string.setting_account_pwd));
        et_pwd_original = (EditText) findViewById(R.id.et_pwd_original);
        et_pwd_new = (EditText) findViewById(R.id.et_pwd_new);
        et_pwd_new_ok = (EditText) findViewById(R.id.et_pwd_new_ok);
        et_pwd_original.addTextChangedListener(new EditTextChange());
        et_pwd_new.addTextChangedListener(new EditTextChange());
        et_pwd_new_ok.addTextChangedListener(new EditTextChange());
        tv_warn = (TextView) findViewById(R.id.tv_warn);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changePasswordPresenter == null){
                    changePasswordPresenter = new ChangePasswordPresenterImpl(ChangePasswordActivity.this);
                }
                changePasswordPresenter.changePassword(getMap());
            }
        });
    }


    private Map<String,String> getMap(){
        HttpMap map = new HttpMap();
        map.put("userid", App.getApp().getUser().getUserid());
        map.put("password",et_pwd_original.getText().toString());
        map.put("newpassword",et_pwd_new.getText().toString());
        return map.getMap();
    }

    @Override
    public void setChangePassword() {
        ToastUtils.toast(getString(R.string.change_success));
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
            if (!StringUtils.isNullOrEmpty(et_pwd_original.getText().toString())&&
                    !StringUtils.isNullOrEmpty(et_pwd_new.getText().toString())&&
                    !StringUtils.isNullOrEmpty(et_pwd_new_ok.getText().toString())&&
                    et_pwd_new.getText().toString().equals(et_pwd_new_ok.getText().toString())){
                tv_warn.setVisibility(View.INVISIBLE);
                btn_submit.setEnabled(true);
                btn_submit.setBackgroundResource(R.drawable.btn_selector);
            }else {
                if (!StringUtils.isNullOrEmpty(et_pwd_new.getText().toString())&&
                        !StringUtils.isNullOrEmpty(et_pwd_new_ok.getText().toString())&&
                        !et_pwd_new.getText().toString().equals(et_pwd_new_ok.getText().toString())){
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
