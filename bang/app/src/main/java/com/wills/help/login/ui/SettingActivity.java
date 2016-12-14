package com.wills.help.login.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.SharedPreferencesUtils;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2016/12/14.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_about,rl_exit;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_setting);
        setBaseTitle(getString(R.string.setting));
        rl_about = (RelativeLayout) findViewById(R.id.rl_about);
        rl_exit = (RelativeLayout) findViewById(R.id.rl_exit);
        rl_about.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_about:
                break;
            case R.id.rl_exit:
                SharedPreferencesUtils.getInstance().remove(AppConfig.SP_USER);
                App.getApp().removeUser();
                App.getApp().setIsLogin(false);
                finish();
                break;
        }
    }
}
