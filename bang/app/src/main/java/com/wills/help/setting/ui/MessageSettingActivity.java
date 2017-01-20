package com.wills.help.setting.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.SharedPreferencesUtils;
import com.wills.help.widget.SwitchView;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2017/1/4.
 */

public class MessageSettingActivity extends BaseActivity {

    LinearLayout ll_setting;
    SwitchView sv_notice,sv_voice,sv_shake;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_msg_setting);
        setBaseTitle(getString(R.string.setting_msg));
        ll_setting = (LinearLayout) findViewById(R.id.ll_setting);
        sv_notice = (SwitchView) findViewById(R.id.sv_notice);
        sv_voice = (SwitchView) findViewById(R.id.sv_voice);
        sv_shake = (SwitchView) findViewById(R.id.sv_shake);
        sv_notice.setState((boolean) SharedPreferencesUtils.getInstance().get(AppConfig.IM_NOTICE,true));
        sv_voice.setState((boolean) SharedPreferencesUtils.getInstance().get(AppConfig.IM_VOICE,true));
        sv_shake.setState((boolean) SharedPreferencesUtils.getInstance().get(AppConfig.IM_SHAKE,true));
        sv_notice.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                changeSetting(AppConfig.IM_NOTICE,true);
                ll_setting.setVisibility(View.VISIBLE);
            }

            @Override
            public void toggleToOff() {
                changeSetting(AppConfig.IM_NOTICE,false);
                ll_setting.setVisibility(View.GONE);
            }
        });
        sv_voice.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                changeSetting(AppConfig.IM_VOICE,true);
            }

            @Override
            public void toggleToOff() {
                changeSetting(AppConfig.IM_VOICE,false);
            }
        });
        sv_shake.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                changeSetting(AppConfig.IM_SHAKE,true);
            }

            @Override
            public void toggleToOff() {
                changeSetting(AppConfig.IM_SHAKE,false);
            }
        });
    }

    private void changeSetting(final String tag , final boolean open){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferencesUtils.getInstance().put(tag,open);
            }
        });
        thread.start();
    }
}
