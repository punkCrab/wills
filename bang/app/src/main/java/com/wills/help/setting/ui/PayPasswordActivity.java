package com.wills.help.setting.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.db.manager.UserInfoHelper;
import com.wills.help.net.HttpMap;
import com.wills.help.person.model.Avatar;
import com.wills.help.person.presenter.UserInfoPresenterImpl;
import com.wills.help.person.view.UserInfoView;
import com.wills.help.utils.StringUtils;
import com.wills.help.widget.PayPwdEditText;

import java.util.Map;

/**
 * com.wills.help.setting.ui
 * Created by lizhaoyong
 * 2017/3/9.
 */

public class PayPasswordActivity extends BaseActivity implements UserInfoView{
    PayPwdEditText et_password;
    Button btn_submit;
    String pwd,pwd1,pwd2;
    private TextView tv_content,tv_warn;
    private UserInfoPresenterImpl userInfoPresenter;
    private boolean isVerify;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_pay_password);
        setBaseTitle(getString(R.string.setting_pay_pwd));
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_warn = (TextView) findViewById(R.id.tv_warn);
        if (!StringUtils.isNullOrEmpty(App.getApp().getUser().getPaypwd())){
            tv_content.setText(getString(R.string.pay_pwd_original_input));
            pwd = App.getApp().getUser().getPaypwd();
            isVerify = true;
        }
        et_password = (PayPwdEditText) findViewById(R.id.et_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_password.initStyle(R.drawable.pay_shape,6,1.0f,R.color.colorPrimaryDark,R.color.colorPrimaryDark,20);
        et_password.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                if(!isVerify){
                    if (pwd1 == null){
                        pwd1 = str;
                        et_password.clearText();
                        btn_submit.setVisibility(View.VISIBLE);
                        btn_submit.setEnabled(false);
                        btn_submit.setBackgroundResource(R.color.button_gray);
                        tv_content.setText(getString(R.string.pay_pwd_input_again));
                    }else {
                        pwd2 = str;
                        if (pwd1.equals(pwd2)){
                            btn_submit.setEnabled(true);
                            btn_submit.setBackgroundResource(R.drawable.btn_selector);
                            tv_warn.setVisibility(View.INVISIBLE);
                        }else {
                            tv_warn.setVisibility(View.VISIBLE);
                            tv_warn.setText(R.string.register_pwd_warn);
                        }
                    }
                }else {
                    if (pwd != null){
                        if (pwd.equals(StringUtils.getMD5(str))){
                            tv_warn.setVisibility(View.INVISIBLE);
                            et_password.clearText();
                            tv_content.setText(getString(R.string.pay_pwd_input));
                            isVerify = false;
                        }else {
                            tv_warn.setVisibility(View.VISIBLE);
                            tv_warn.setText(getString(R.string.password_error));
                        }
                    }
                }
            }
        });
        et_password.post(new Runnable() {
            @Override
            public void run() {
                et_password.setFocus();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userInfoPresenter == null){
                    userInfoPresenter = new UserInfoPresenterImpl(PayPasswordActivity.this);
                }
                userInfoPresenter.setUserInfo(getMap());
            }
        });
    }

    private Map<String ,String> getMap(){
        HttpMap map = new HttpMap();
        map.put("userid", App.getApp().getUser().getUserid());
        map.put("paypwd", pwd2);
        return map.getMap();
    }

    @Override
    public void setUserInfo() {
        App.getApp().getUser().setPaypwd(StringUtils.getMD5(pwd2));
        UserInfoHelper.getInstance().updateData(App.getApp().getUser()).subscribe();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setAvatar(Avatar.AvatarUrl url) {

    }
}
