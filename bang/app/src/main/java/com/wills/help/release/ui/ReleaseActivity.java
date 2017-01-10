package com.wills.help.release.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.pay.ui.PayActivity;
import com.wills.help.utils.IntentUtils;

/**
 * Created by Wills on 2016/12/28.
 */

public class ReleaseActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_release_state,tv_release_from,tv_release_send;
    private EditText et_release_from_address,et_release_money,et_release_send_address;
    private Button btn_submit;
    private int timeType = 0;
    String[] state = new String[]{"取快递","买零食"};
    String[] str = new String[]{"申通","圆通","中通","顺风","韵达"};
    String[] address = new String[]{"北京交通大学第七教学楼","北京交通大学第八教学楼","北京交通大学第九教学楼"};
    private int type = 0;//0修改1为你解邮

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.fragment_release);
        setBaseTitle(getString(R.string.release));
        type = getIntent().getExtras().getInt("type",0);
        initLayout();
        initListener();
    }

    private void initLayout(){
        tv_release_state = (TextView) findViewById(R.id.tv_release_state);
        tv_release_from = (TextView) findViewById(R.id.tv_release_from);
        tv_release_send = (TextView) findViewById(R.id.tv_release_send);
        et_release_from_address = (EditText) findViewById(R.id.et_release_from_address);
        et_release_money = (EditText) findViewById(R.id.et_release_money);
        et_release_send_address = (EditText) findViewById(R.id.et_release_send_address);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        if (type == 1){
            et_release_money.setText(getString(R.string.release_free));
            et_release_money.setEnabled(false);
        }
    }

    private void initListener(){
        tv_release_state.setOnClickListener(this);
        tv_release_from.setOnClickListener(this);
        tv_release_send.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_release_state:
                showAlert(state,tv_release_state);
                break;
            case R.id.tv_release_from:
                showAlert(str,tv_release_from);
                break;
            case R.id.tv_release_send:
                showAlert(address,tv_release_send);
                break;
            case R.id.btn_submit:
                IntentUtils.startActivity(context,PayActivity.class);
                break;
        }
    }

    private void showAlert(final String[] strings , final TextView textView){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(strings[i]);
            }
        }).show();
    }
}
