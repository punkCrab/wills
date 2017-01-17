package com.wills.help.message.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.db.bean.Contact;
import com.wills.help.db.manager.ContactHelper;
import com.wills.help.message.EaseConstant;

import rx.Subscriber;

/**
 * com.wills.help.message.ui
 * Created by lizhaoyong
 * 2016/12/19.
 */

public class ChatActivity extends BaseActivity{
    private String chatUsername;
    private String nickName;
    private String avatarUrl;
    private EaseChatFragment chatFragment;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        chatUsername = bundle.getString(EaseConstant.EXTRA_USER_ID);
        nickName = bundle.getString(EaseConstant.EXTRA_USER_NAME);
        avatarUrl = bundle.getString(EaseConstant.EXTRA_USER_AVATAR);
        setBaseView(R.layout.ease_activity_message);
        ContactHelper.getInstance().queryByUsername(chatUsername).subscribe(new Subscriber<Contact>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Contact contact) {
                if (contact != null) {
                    setBaseTitle(contact.getNickname());
                } else {
                    if (!TextUtils.isEmpty(nickName)) {
                        setBaseTitle(nickName);
                        Contact mContact = new Contact();
                        contact.setNickname(nickName);
                        contact.setAvatar(avatarUrl);
                        contact.setUsername(chatUsername);
                        ContactHelper.getInstance().insertData(mContact).subscribe();
                    }
                }
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        chatFragment.onActivityResult(requestCode, resultCode, data);
    }
}
