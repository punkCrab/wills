package com.wills.help.login.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.login.model.User;
import com.wills.help.login.presenter.RegisterPresenterImpl;
import com.wills.help.login.view.RegisterView;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.KeyBoardUtils;
import com.wills.help.utils.NetUtils;
import com.wills.help.utils.ScreenUtils;
import com.wills.help.utils.SharedPreferencesUtils;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.TimeCountUtils;
import com.wills.help.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

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
    private TextView tv_warn;
    private AlertDialog dialog;

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
                    if (KeyboardHeight != 0){
                        linearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        SharedPreferencesUtils.getInstance().put(AppConfig.KEYBOARD,KeyboardHeight);
                    }
                }
            });
        }
        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        et_register_pwd = (EditText) findViewById(R.id.et_register_pwd);
        et_register_pwd_ok = (EditText) findViewById(R.id.et_register_pwd_ok);
        et_register_code = (EditText) findViewById(R.id.et_register_code);
        tv_warn = (TextView) findViewById(R.id.tv_warn);
        et_register_phone.addTextChangedListener(new EditTextChange());
        et_register_pwd.addTextChangedListener(new EditTextChange());
        et_register_pwd_ok.addTextChangedListener(new EditTextChange());
        et_register_code.addTextChangedListener(new EditTextChange());
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
                String phone = et_register_phone.getText().toString();
                if (StringUtils.availablePhone(phone)){
                    TimeCountUtils utils = new TimeCountUtils(context, 60000, 1000, btn_code);
                    utils.start();
                    registerPresenter.getCode(et_register_phone.getText().toString());
                }else {
                    ToastUtils.toast(getString(R.string.phone_error));
                }
                break;
            case R.id.btn_submit:
                dialog = NetUtils.netDialog(context);
                registerPresenter.register(getMap());
                break;
        }
    }

    private Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        map.put("username",et_register_phone.getText().toString());
        map.put("password",et_register_pwd.getText().toString());
        map.put("value",et_register_code.getText().toString());
        return map;
    }

    @Override
    public void setRegister(User user) {
        ToastUtils.toast(getString(R.string.register_success));
        handler.sendEmptyMessageDelayed(1,1500);
    }

    @Override
    public void registerError() {
        if (dialog!=null){
            dialog.dismiss();
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    EMClient.getInstance().login(et_register_phone.getText().toString(), StringUtils.getMD5(et_register_pwd.getText().toString()), new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            if (dialog!=null){
                                dialog.dismiss();
                            }
                            EMClient.getInstance().chatManager().loadAllConversations();
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
                    break;
            }
        }
    };

    @Override
    protected void backClick() {
        setResult(RESULT_CANCELED);
        super.backClick();
    }

    public class EditTextChange implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!StringUtils.isNullOrEmpty(et_register_phone.getText().toString())&&
                    !StringUtils.isNullOrEmpty(et_register_pwd.getText().toString())&&
                    !StringUtils.isNullOrEmpty(et_register_pwd_ok.getText().toString())&&
                    !StringUtils.isNullOrEmpty(et_register_code.getText().toString())&&
                    et_register_pwd.getText().toString().equals(et_register_pwd_ok.getText().toString())){
                tv_warn.setVisibility(View.INVISIBLE);
                btn_submit.setEnabled(true);
                btn_submit.setBackgroundResource(R.drawable.btn_selector);
            }else {
                if (!StringUtils.isNullOrEmpty(et_register_pwd.getText().toString())&&
                        !StringUtils.isNullOrEmpty(et_register_pwd_ok.getText().toString())&&
                        !et_register_pwd.getText().toString().equals(et_register_pwd_ok.getText().toString())){
                    tv_warn.setVisibility(View.VISIBLE);
                }else {
                    tv_warn.setVisibility(View.INVISIBLE);
                }
                btn_submit.setEnabled(false);
                btn_submit.setBackgroundResource(R.color.button_gray);
            }
        }
    }
}
