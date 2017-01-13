package com.wills.help.person.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.person.presenter.UserInfoPresenterImpl;
import com.wills.help.person.view.UserInfoView;

import java.util.HashMap;
import java.util.Map;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2017/1/9.
 */

public class ChangeNameActivity extends BaseActivity implements UserInfoView{
    EditText et_name;
    String name;
    private TextView tv_des;
    private UserInfoPresenterImpl userInfoPresenter;
    private int action;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_name);
        setBaseTitle(getString(R.string.nickname_change));
        Bundle b = getIntent().getExtras();
        action = b.getInt("action");
        name = b.getString("name");
        et_name = (EditText) findViewById(R.id.et_name);
        tv_des = (TextView) findViewById(R.id.tv_des);
        if (action == 1){
            setBaseTitle(getString(R.string.nickname_change));
            tv_des.setText(getString(R.string.nickname_change_des));
        }else if (action == 2){
            setBaseTitle(getString(R.string.school_change));
            tv_des.setText(getString(R.string.school_change_des));
        }
        et_name.setText(name);
        et_name.setSelection(name.length());
        userInfoPresenter = new UserInfoPresenterImpl(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base,menu);
        menu.getItem(0).setTitle(getString(R.string.ok));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_right:
                userInfoPresenter.setUserInfo(getMap());
                break;
        }
        return true;
    }

    private Map<String ,String> getMap(){
        Map<String , String> map = new HashMap<>();
        map.put("userid", App.getApp().getUser().getUserid());
        if (action == 1){
            map.put("nickname", et_name.getText().toString());
        }else if (action == 2){
            map.put("school", et_name.getText().toString());
        }
        return map;
    }

    @Override
    public void setUserInfo() {
        if (action == 1){
            App.getApp().getUser().setNickname(et_name.getText().toString());
        }else if (action == 2){
            App.getApp().getUser().setSchool(et_name.getText().toString());
        }
        Intent intent = new Intent();
        intent.putExtra("name",et_name.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void setAvatar() {

    }
}
