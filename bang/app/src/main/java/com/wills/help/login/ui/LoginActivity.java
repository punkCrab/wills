package com.wills.help.login.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.login.model.User;
import com.wills.help.login.presenter.LoginPresenterImpl;
import com.wills.help.login.view.LoginView;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.KeyBoardUtils;
import com.wills.help.utils.ScreenUtils;
import com.wills.help.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * com.wills.help.login.ui
 * Created by lizhaoyong
 * 2016/11/16.
 */

public class LoginActivity extends BaseActivity implements LoginView ,View.OnClickListener{
    ImageView imageView;
    EditText et_username,et_password;
    TextView tv_unlogin,tv_register;
    Button btn_login;
    private LoginPresenterImpl loginInfoPresenter;
    private RelativeLayout relativeLayout;
    private final int REGISTER = 12;//注册

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setNoActionBarView(R.layout.activity_login);
        if (KeyBoardUtils.getKeyBoardHeight() == 0){
            relativeLayout = (RelativeLayout) findViewById(R.id.rl_root);
            relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect r = new Rect();
                    relativeLayout.getWindowVisibleDisplayFrame(r);
                    int screenHeight = ScreenUtils.getScreenHeight(context);
                    int KeyboardHeight = screenHeight-(r.bottom-r.top)-ScreenUtils.getStatusHeight(context);
                    if (KeyboardHeight != 0){
                        relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        SharedPreferencesUtils.getInstance().put(AppConfig.KEYBOARD,KeyboardHeight);
                    }
                }
            });
        }
        imageView = (ImageView) findViewById(R.id.iv_avatar);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_unlogin = (TextView) findViewById(R.id.tv_unlogin);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username.addTextChangedListener(new EditTextChange());
        et_password.addTextChangedListener(new EditTextChange());
        loginInfoPresenter = new LoginPresenterImpl(this);
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_unlogin.setOnClickListener(this);
    }

    @Override
    public void setLogin(User login) {
        setResult(RESULT_OK);
        EMClient.getInstance().login(et_username.getText().toString(), et_password.getText().toString(), new EMCallBack() {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                String user = et_username.getText().toString();
                String pwd = et_password.getText().toString();
                Map<String,String> map = new HashMap<>();
                map.put("username","15311437664");
                map.put("password", "123456");
                loginInfoPresenter.login(map);
                break;
            case R.id.tv_register:
                IntentUtils.startActivityForResult(LoginActivity.this,RegisterActivity.class,REGISTER);
                break;
            case R.id.tv_unlogin:
                IntentUtils.startActivity(context,ResetActivity.class);
                break;
        }
    }

    @Override
    protected void backClick() {
        setResult(RESULT_CANCELED);
        super.backClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER&&resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }

    public class EditTextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!TextUtils.isEmpty(et_username.getText().toString())&&
                    !TextUtils.isEmpty(et_password.getText().toString())){
                btn_login.setEnabled(true);
                btn_login.setBackgroundResource(R.drawable.btn_selector);
            }else {
                btn_login.setEnabled(false);
                btn_login.setBackgroundResource(R.color.button_gray);
            }
        }
    }
}
