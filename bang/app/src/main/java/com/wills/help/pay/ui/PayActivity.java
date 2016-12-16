package com.wills.help.pay.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.widget.MyRadioGroup;

/**
 * com.wills.help.pay.ui
 * Created by lizhaoyong
 * 2016/12/16.
 */

public class PayActivity extends BaseActivity{
    private TextView tv_amount;
    private MyRadioGroup rg_pay;
    private Button button;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_pay);
        setBaseTitle(getString(R.string.pay));
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        rg_pay = (MyRadioGroup) findViewById(R.id.rg_pay);
        button = (Button) findViewById(R.id.btn_submit);
        rg_pay.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_ali){

                }else if (checkedId == R.id.rb_wx){

                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
