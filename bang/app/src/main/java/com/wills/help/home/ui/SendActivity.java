package com.wills.help.home.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2017/1/4.
 */

public class SendActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_choose_address;
    private EditText et_address;
    private Button btn_submit;
    String[] address = new String[]{"北京交通大学第七教学楼","北京交通大学第八教学楼","北京交通大学第九教学楼"};

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_send);
        setBaseTitle(getString(R.string.home_express));
        tv_choose_address = (TextView) findViewById(R.id.tv_choose_address);
        et_address = (EditText) findViewById(R.id.et_address);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tv_choose_address.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_address:
                showAlert(address,tv_choose_address);
                break;
            case R.id.btn_submit:
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
