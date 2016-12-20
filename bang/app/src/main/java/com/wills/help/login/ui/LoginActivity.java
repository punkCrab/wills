package com.wills.help.login.ui;

import android.graphics.Rect;
import android.os.Bundle;
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
import com.wills.help.utils.ToastUtils;

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
                    SharedPreferencesUtils.getInstance().put(AppConfig.KEYBOARD,KeyboardHeight);
                    ToastUtils.toast("键盘高度"+KeyboardHeight);
                }
            });
        }
        imageView = (ImageView) findViewById(R.id.iv_avatar);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_unlogin = (TextView) findViewById(R.id.tv_unlogin);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        loginInfoPresenter = new LoginPresenterImpl(this);
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_unlogin.setOnClickListener(this);
        et_username.setText("15652956043");
        et_password.setText("qqq");
    }

    @Override
    public void setLogin(User login) {
        setResult(RESULT_OK);
        EMClient.getInstance().login("leewills", "123456", new EMCallBack() {
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
                map.put("username",user);
                map.put("password", pwd);
                loginInfoPresenter.login(map);
                break;
            case R.id.tv_register:
                IntentUtils.startActivity(context,RegisterActivity.class);
                break;
            case R.id.tv_unlogin:
                break;
        }
    }

    @Override
    protected void backClick() {
        setResult(RESULT_CANCELED);
        super.backClick();
    }
}
