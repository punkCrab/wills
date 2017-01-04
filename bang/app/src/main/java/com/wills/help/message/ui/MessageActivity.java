package com.wills.help.message.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.message.EaseConstant;
import com.wills.help.utils.AppManager;
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

            @Override
            public void OnListItemLongClicked(EMConversation conversation) {
                showAlert(conversation);
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_content,listFragment).commit();
    }

    private void showAlert(final EMConversation conversation){
        final String[] strings = new String[]{getString(R.string.delete),getString(R.string.check_msg)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        EMClient.getInstance().chatManager().deleteConversation(conversation.getUserName(), false);
                        listFragment.refresh();
                        break;
                    case 1:
                        Bundle bundle = new Bundle();
                        bundle.putString(EaseConstant.EXTRA_USER_ID,conversation.getUserName());
                        IntentUtils.startActivity(context,ChatActivity.class,bundle);
                        break;
                }
            }
        }).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        AppManager.getAppManager().finishActivity(ChatActivity.class);
    }
}
