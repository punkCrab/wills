package com.wills.help.login.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2016/12/14.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_about,rl_exit,rl_msg;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_setting);
        setBaseTitle(getString(R.string.setting));
        rl_about = (RelativeLayout) findViewById(R.id.rl_about);
        rl_exit = (RelativeLayout) findViewById(R.id.rl_exit);
        rl_msg = (RelativeLayout) findViewById(R.id.rl_msg);
        rl_about.setOnClickListener(this);
        rl_msg.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_about:
                break;
            case R.id.rl_msg:
                IntentUtils.startActivity(context,MessageSettingActivity.class);
                break;
            case R.id.rl_exit:
                App.getApp().exitApp();
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        finish();
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                break;
        }
    }
}
