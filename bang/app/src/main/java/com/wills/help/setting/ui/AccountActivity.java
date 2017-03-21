package com.wills.help.setting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.StringUtils;

/**
 * com.wills.help.setting.ui
 * Created by lizhaoyong
 * 2017/1/20.
 */

public class AccountActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_change_phone,tv_change_pwd,tv_pay_state;
    private RelativeLayout rl_pay_pwd;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_account);
        setBaseTitle(getString(R.string.setting_account));
        tv_change_phone = (TextView) findViewById(R.id.tv_change_phone);
        tv_change_pwd = (TextView) findViewById(R.id.tv_change_pwd);
        tv_pay_state = (TextView) findViewById(R.id.tv_pay_state);
        rl_pay_pwd = (RelativeLayout) findViewById(R.id.rl_pay_pwd);
        tv_change_phone.setOnClickListener(this);
        tv_change_pwd.setOnClickListener(this);
        rl_pay_pwd.setOnClickListener(this);
        if(!StringUtils.isNullOrEmpty(App.getApp().getUser().getPaypwd())){
            tv_pay_state.setText(getString(R.string.setting_change_pay_pwd));
        }else {
            tv_pay_state.setText(getString(R.string.setting_set_pay_pwd));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_change_phone:
                break;
            case R.id.tv_change_pwd:
                IntentUtils.startActivity(context,ChangePasswordActivity.class);
                break;
            case R.id.rl_pay_pwd:
                IntentUtils.startActivityForResult(AccountActivity.this,PayPasswordActivity.class,502);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 502&&resultCode==RESULT_OK){
            tv_pay_state.setText(getString(R.string.setting_change_pay_pwd));
        }
    }
}
