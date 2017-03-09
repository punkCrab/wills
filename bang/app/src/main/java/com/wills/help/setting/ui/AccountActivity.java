package com.wills.help.setting.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.IntentUtils;

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_change_phone:
                break;
            case R.id.tv_change_pwd:
                break;
            case R.id.rl_pay_pwd:
                IntentUtils.startActivity(context,PayPasswordActivity.class);
                break;
        }
    }
}
