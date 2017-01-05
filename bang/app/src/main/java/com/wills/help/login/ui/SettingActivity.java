package com.wills.help.login.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.CacheCleanUtils;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2016/12/14.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_about,rl_exit,rl_msg,rl_cache;
    private TextView tv_cache;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_setting);
        setBaseTitle(getString(R.string.setting));
        rl_about = (RelativeLayout) findViewById(R.id.rl_about);
        rl_exit = (RelativeLayout) findViewById(R.id.rl_exit);
        rl_msg = (RelativeLayout) findViewById(R.id.rl_msg);
        rl_cache = (RelativeLayout) findViewById(R.id.rl_cache);
        tv_cache = (TextView) findViewById(R.id.tv_cache);
        rl_about.setOnClickListener(this);
        rl_msg.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
        rl_cache.setOnClickListener(this);
        try {
            tv_cache.setText(CacheCleanUtils.getTotalCacheSize(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_about:
                break;
            case R.id.rl_msg:
                IntentUtils.startActivity(context,MessageSettingActivity.class);
                break;
            case R.id.rl_cache:
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage(getString(R.string.setting_cache_clear))
                        .setCancelable(true)
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CacheCleanUtils.clearAllCache(context);
                                tv_cache.setText("0.0KB");
                            }
                        });
                builder.show();
                break;
            case R.id.rl_exit:
                AlertDialog.Builder exit = new AlertDialog.Builder(context)
                        .setMessage(getString(R.string.setting_exit_content))
                        .setCancelable(true)
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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
                            }
                        });
                exit.show();
                break;
        }
    }



}
