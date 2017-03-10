package com.wills.help.person.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.wills.help.utils.ToastUtils;

import java.util.Map;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2017/3/10.
 */

public class ApproveActivity extends BaseActivity implements UserInfoView{
    private EditText et_name,et_zfb;
    private TextView tv_warn;
    private Button btn_submit;
    private UserInfoPresenterImpl userInfoPresenter;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_approve);
        setBaseTitle(getString(R.string.approve));
        et_name = (EditText) findViewById(R.id.et_name);
        et_zfb = (EditText) findViewById(R.id.et_zfb);
        tv_warn = (TextView) findViewById(R.id.tv_warn);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_name.addTextChangedListener(new EditTextChange());
        et_zfb.addTextChangedListener(new EditTextChange());
        if (!StringUtils.isNullOrEmpty(App.getApp().getUser().getAliaccount())){
            et_name.setText(App.getApp().getUser().getRealname());
            et_zfb.setText(App.getApp().getUser().getAliaccount());
            et_name.setEnabled(false);
            et_zfb.setEnabled(false);
            tv_warn.setVisibility(View.GONE);
            btn_submit.setVisibility(View.GONE);
        }
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userInfoPresenter == null){
                    userInfoPresenter = new UserInfoPresenterImpl(ApproveActivity.this);
                }
                userInfoPresenter.setUserInfo(getMap());
            }
        });
    }


    @Override
    public void setUserInfo() {
        App.getApp().getUser().setRealname(et_name.getText().toString());
        App.getApp().getUser().setAliaccount(et_zfb.getText().toString());
        UserInfoHelper.getInstance().updateData(App.getApp().getUser()).subscribe();
        ToastUtils.toast(getString(R.string.approved));
        finish();
    }
    private Map<String ,String> getMap(){
        HttpMap map = new HttpMap();
        map.put("userid", App.getApp().getUser().getUserid());
        map.put("realname", et_name.getText().toString());
        map.put("aliaccount", et_zfb.getText().toString());
        return map.getMap();
    }
    @Override
    public void setAvatar(Avatar.AvatarUrl url) {

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
            if (!StringUtils.isNullOrEmpty(et_name.getText().toString())&&
                    !StringUtils.isNullOrEmpty(et_zfb.getText().toString())){
                btn_submit.setEnabled(true);
                btn_submit.setBackgroundResource(R.drawable.btn_selector);
            }else {
                btn_submit.setEnabled(false);
                btn_submit.setBackgroundResource(R.color.button_gray);
            }
        }
    }
}
