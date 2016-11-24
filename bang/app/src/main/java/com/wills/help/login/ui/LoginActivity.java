package com.wills.help.login.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.base.MainActivity;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2016/11/16.
 */

public class LoginActivity extends BaseActivity{
    ImageView imageView;
    EditText et_username,et_password;
    TextView tv_unlogin,tv_register;
    Button btn_login;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setNoActionBarView(R.layout.activity_login);
        imageView = (ImageView) findViewById(R.id.iv_avatar);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_unlogin = (TextView) findViewById(R.id.tv_unlogin);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        initEvents();
    }

    private void initEvents() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.startFinishActivity(context, MainActivity.class);
            }
        });
    }
}
