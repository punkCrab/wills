package com.wills.help.message.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.message.EaseConstant;

/**
 * com.wills.help.message.ui
 * Created by lizhaoyong
 * 2016/12/19.
 */

public class ChatActivity extends BaseActivity{
    private String chatUsername;
    private EaseChatFragment chatFragment;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        chatUsername = bundle.getString(EaseConstant.EXTRA_USER_ID);
        setBaseView(R.layout.ease_activity_message);
        setBaseTitle(chatUsername);
        chatFragment= new EaseChatFragment();
        chatFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_content,chatFragment).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String username = intent.getStringExtra("userId");
        if (chatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public String getChatUsername(){
        return chatUsername;
    }
}
