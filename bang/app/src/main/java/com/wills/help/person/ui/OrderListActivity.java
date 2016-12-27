package com.wills.help.person.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.listener.BaseListLoadMoreListener;
import com.wills.help.person.adapter.OrderAdapter;
import com.wills.help.person.model.Order;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2016/12/5.
 */

public class OrderListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener , BaseListLoadMoreListener.LoadMoreListener{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Order> orderArrayList = new ArrayList<>();

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.page_list);
        Bundle bundle = getIntent().getExtras();
        setBaseTitle(bundle.getString("title"));
        recyclerView = (RecyclerView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary, R.color.colorPrimaryLight,R.color.colorAccent);
        initData();
    }

    private void initData() {
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(context,5));
        orderAdapter = new OrderAdapter(context,orderArrayList);
        recyclerView.setAdapter(orderAdapter);
        BaseListLoadMoreListener listLoadMore = new BaseListLoadMoreListener(linearLayoutManager,orderAdapter);
        recyclerView.addOnScrollListener(listLoadMore);
        listLoadMore.setLoadMoreListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if(orderArrayList != null) {
            orderArrayList.clear();
        }
        handler.sendEmptyMessageDelayed(1,2000);
    }

    @Override
    public void loadMore() {
        if (orderArrayList==null){
            orderArrayList = new ArrayList<>();
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
                    if (orderArrayList.size()<3){
                        orderAdapter.setLoadMore(orderAdapter.SUCCESS);
                        orderArrayList.addAll(getAssistList());
                    }
                    orderAdapter.setList(orderArrayList);
                    break;
            }
        }
    };

    private List<Order> getAssistList(){
        List<Order> list = new ArrayList<>();
        for (int i=0;i<3;i++){
            Order order = new Order();
            order.setId(i);
            list.add(order);
        }
        return list;
    }
}
