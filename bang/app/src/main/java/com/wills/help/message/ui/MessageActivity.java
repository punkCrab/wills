package com.wills.help.message.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hyphenate.chat.EMConversation;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.message.EaseConstant;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.message.ui
 * Created by lizhaoyong
 * 2016/12/19.
 */

public class MessageActivity extends BaseActivity{

    private EaseConversationListFragment listFragment;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.ease_activity_message);
        setBaseTitle(getString(R.string.tab_msg));
        listFragment = new EaseConversationListFragment();
        listFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Bundle bundle = new Bundle();
                bundle.putString(EaseConstant.EXTRA_USER_ID,conversation.getUserName());
                IntentUtils.startActivity(context,ChatActivity.class,bundle);
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_content,listFragment).commit();
    }
}
