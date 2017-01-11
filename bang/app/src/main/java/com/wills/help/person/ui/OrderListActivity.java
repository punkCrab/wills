package com.wills.help.person.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.listener.BaseListLoadMoreListener;
import com.wills.help.person.adapter.OrderAdapter;
import com.wills.help.release.model.ReleaseInfo;
import com.wills.help.release.model.ReleaseList;
import com.wills.help.release.presenter.ReleaseListPresenterImpl;
import com.wills.help.release.view.ReleaseListView;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2016/12/5.
 */

public class OrderListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener , BaseListLoadMoreListener.LoadMoreListener , ReleaseListView{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    LinearLayoutManager linearLayoutManager;
    List<ReleaseInfo> orderArrayList = new ArrayList<>();
    private ReleaseListPresenterImpl releaseListPresenter;
    private int page = 1;
    private int action = 0;
    private int type = 0;//0发布1接单
    private int count=0;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.page_list);
        Bundle bundle = getIntent().getExtras();
        setBaseTitle(bundle.getString("title"));
        action = bundle.getInt("action");
        type = bundle.getInt("type");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary, R.color.colorPrimaryLight,R.color.colorAccent);
        initData();
    }

    private void initData() {
        releaseListPresenter = new ReleaseListPresenterImpl(this);
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
            page=1;
        }
        releaseListPresenter.getReleaseList(getMap());
    }

    @Override
    public void loadMore() {
        if (count>orderArrayList.size()){
            releaseListPresenter.getReleaseList(getMap());
        }else {
            orderAdapter.setLoadMore(orderAdapter.EMPTY);
        }
    }

    private Map<String ,String> getMap(){
        Map<String , String> map = new HashMap<>();
        if (type == 0){
            map.put("releaseuserid", App.getApp().getUser().getUserid());
        }else if (type==1){
            map.put("acceptuserid", App.getApp().getUser().getUserid());
        }
        map.put("action", action+"");
        map.put("page", page+"");
        return map;
    }

    @Override
    public void setReleaseList(ReleaseList releaseList) {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        count = releaseList.getCount();
        if (orderArrayList == null ){
            orderArrayList = new ArrayList<>();
        }
        if (releaseList.getData().size()>0){
            orderAdapter.setLoadMore(orderAdapter.SUCCESS);
            orderArrayList.addAll(releaseList.getData());
            orderAdapter.setList(orderArrayList);
            page++;
        }

    }
}
