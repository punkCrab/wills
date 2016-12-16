package com.wills.help.assist.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wills.help.R;
import com.wills.help.assist.adapter.AssistAdapter;
import com.wills.help.assist.model.Assist;
import com.wills.help.base.BaseActivity;
import com.wills.help.listener.BaseListLoadMoreListener;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.assist.ui
 * Created by lizhaoyong
 * 2016/11/16.
 */

public class AssistListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener , BaseListLoadMoreListener.LoadMoreListener{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    AssistAdapter assistAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Assist> assistList = new ArrayList<>();

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.page_list);
        setBaseTitle(getString(R.string.tab_assist));
        recyclerView = (RecyclerView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary, R.color.colorPrimaryLight,R.color.colorAccent);
        initData();
    }

    private void initData(){
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(context,5));
        assistAdapter = new AssistAdapter(context,assistList);
        recyclerView.setAdapter(assistAdapter);
        BaseListLoadMoreListener listLoadMore = new BaseListLoadMoreListener(linearLayoutManager,assistAdapter);
        recyclerView.addOnScrollListener(listLoadMore);
        listLoadMore.setLoadMoreListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if(assistList != null) {
            assistList.clear();
        }
        handler.sendEmptyMessageDelayed(1,2000);
    }

    @Override
    public void loadMore() {
        if (assistList==null){
            assistList = new ArrayList<>();
        }
        handler.sendEmptyMessageDelayed(1,2000);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    assistAdapter.setLoadMore(assistAdapter.SUCCESS);
                    assistList.addAll(getAssistList());
                    assistAdapter.setList(assistList);
                    break;
            }
        }
    };

    private List<Assist> getAssistList(){
        List<Assist> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            Assist assist = new Assist();
            assist.setId(i);
            list.add(assist);
        }
        return list;
    }
}
