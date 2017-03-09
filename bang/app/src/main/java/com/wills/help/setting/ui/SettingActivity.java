package com.wills.help.setting.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
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
import com.wills.help.utils.ToastUtils;
import com.wills.help.widget.PayPwdEditText;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2016/12/14.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_cache;
    private TextView tv_cache,tv_about,tv_exit ,tv_msg,tv_account;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_setting);
        setBaseTitle(getString(R.string.setting));
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_account = (TextView) findViewById(R.id.tv_account);
        rl_cache = (RelativeLayout) findViewById(R.id.rl_cache);
        tv_cache = (TextView) findViewById(R.id.tv_cache);
        tv_about.setOnClickListener(this);
        tv_msg.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        rl_cache.setOnClickListener(this);
        tv_account.setOnClickListener(this);
        try {
            tv_cache.setText(CacheCleanUtils.getTotalCacheSize(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_about:
                payDialog(context);
                break;
            case R.id.tv_msg:
                IntentUtils.startActivity(context,MessageSettingActivity.class);
                break;
            case R.id.tv_account:
                IntentUtils.startActivity(context,AccountActivity.class);
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
            case R.id.tv_exit:
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
                                        setResult(RESULT_OK);
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


    public AlertDialog payDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_pay,null);
        final PayPwdEditText et_password = (PayPwdEditText) view.findViewById(R.id.et_password);
        et_password.initStyle(R.drawable.pay_shape,6,1.0f,R.color.colorPrimaryDark,R.color.colorPrimaryDark,20);
        final AlertDialog dialog = builder.setView(view).create();
        if (!((Activity)context).isFinishing()){
            dialog.show();
        }
        et_password.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                ToastUtils.toast(str);
                dialog.dismiss();
            }
        });
        et_password.post(new Runnable() {
            @Override
            public void run() {
                et_password.setFocus();
            }
        });
        return dialog;
    }
}
