package com.wills.help.person.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
    String nickname;
    private UserInfoPresenterImpl userInfoPresenter;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_name);
        setBaseTitle(getString(R.string.nickname_change));
        nickname = getIntent().getExtras().getString("nickname");
        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setText(nickname);
        et_name.setSelection(nickname.length());
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
        map.put("nickname", et_name.getText().toString());
        return map;
    }

    @Override
    public void setUserInfo() {
        App.getApp().getUser().setNickname(et_name.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("nickname",et_name.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void setAvatar() {

    }
}
