package com.wills.help.message.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.message.EaseConstant;
import com.wills.help.message.adapter.SystemAdapter;
import com.wills.help.message.utils.EaseCommonUtils;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.message.ui
 * Created by lizhaoyong
 * 2017/4/5.
 */

public class SystemActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private List<EMMessage> messageList;
    EMConversation emConversation;
    private int pageSize = 10;
    SystemAdapter adapter;
    private boolean loadCompleted = false;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.page_list);
        setBaseTitle(AppConfig.ADMIN_NAME);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary, R.color.colorPrimaryLight,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageList = new ArrayList<>();
        emConversation = EMClient.getInstance().chatManager().getConversation(AppConfig.ADMIN, EaseCommonUtils.getConversationType(EaseConstant.CHATTYPE_SINGLE), true);
        emConversation.markAllMessagesAsRead();
        messageList = emConversation.getAllMessages();
        int msgCount = messageList != null ? messageList.size() : 0;
        if (msgCount < emConversation.getAllMsgCount() && msgCount < pageSize) {
            String msgId = null;
            if (messageList != null && messageList.size() > 0) {
                msgId = messageList.get(0).getMsgId();
            }
            messageList.addAll(0,emConversation.loadMoreMsgFromDB(msgId, pageSize - msgCount));
        }
        adapter = new SystemAdapter(context,messageList);
        recyclerView.setAdapter(adapter);
        moveToPosition(linearLayoutManager,recyclerView,messageList.size()-1);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<EMMessage> list;
                if (!loadCompleted){
                    if (linearLayoutManager.findFirstVisibleItemPosition() == 0){
                        list = emConversation.loadMoreMsgFromDB(messageList.get(0).getMsgId(), pageSize);
                        if (list!=null&&list.size()>0){
                            if (list.size()<pageSize){
                                loadCompleted = true;
                            }
                            messageList.addAll(0,list);
                            adapter.setList(messageList);
                            if (messageList.size()>pageSize){
                                moveToPosition(linearLayoutManager,recyclerView,pageSize);
                            }else {
                                moveToPosition(linearLayoutManager,recyclerView,messageList.size()-1);
                            }
                        }else {
                            ToastUtils.toast(getString(R.string.no_more_messages));
                        }
                    }
                }else {
                    ToastUtils.toast(getString(R.string.no_more_messages));
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        },500);
    }


    public void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {


        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }

}
