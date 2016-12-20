package com.wills.help.login.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.login.presenter.RegisterPresenterImpl;
import com.wills.help.login.view.RegisterView;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.KeyBoardUtils;
import com.wills.help.utils.ScreenUtils;
import com.wills.help.utils.SharedPreferencesUtils;
import com.wills.help.utils.TimeCountUtils;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2016/12/8.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener , RegisterView{
    private EditText et_register_phone,et_register_pwd,et_register_pwd_ok,et_register_code;
    private Button btn_code,btn_submit;
    private RegisterPresenterImpl registerPresenter;
    private LinearLayout linearLayout;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_register);
        setBaseTitle(getString(R.string.register));
        if (KeyBoardUtils.getKeyBoardHeight() == 0){
            linearLayout = (LinearLayout) findViewById(R.id.ll_root);
            linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect r = new Rect();
                    linearLayout.getWindowVisibleDisplayFrame(r);
                    int screenHeight = ScreenUtils.getScreenHeight(context);
                    int KeyboardHeight = screenHeight-(r.bottom-r.top)-ScreenUtils.getStatusHeight(context);
                    SharedPreferencesUtils.getInstance().put(AppConfig.KEYBOARD,KeyboardHeight);
                }
            });
        }
        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        et_register_pwd = (EditText) findViewById(R.id.et_register_pwd);
        et_register_pwd_ok = (EditText) findViewById(R.id.et_register_pwd_ok);
        et_register_code = (EditText) findViewById(R.id.et_register_code);
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_code.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        registerPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_code:
                TimeCountUtils utils = new TimeCountUtils(context , 60000 , 1000 ,  btn_code);
                utils.start();
                registerPresenter.getCode("15311437664");
                break;
            case R.id.btn_submit:
                break;
        }
    }

    @Override
    public void setRegister(boolean register) {

    }
}
