package com.wills.help.home.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.home.adapter.ExpressListAdapter;
import com.wills.help.home.model.Express;
import com.wills.help.home.presenter.ExpressPresenterImpl;
import com.wills.help.home.view.ExpressView;
import com.wills.help.net.HttpMap;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2016/12/26.
 */

public class ExpressListActivity extends BaseActivity implements ExpressView,SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ExpressListAdapter adapter;
    List<Express.ExpressInfo> expressInfoList = new ArrayList<>();
    ExpressPresenterImpl expressPresenter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.page_list);
        setBaseTitle(getString(R.string.express_my));
        recyclerView = (RecyclerView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary, R.color.colorPrimaryLight,R.color.colorAccent);
        initData();
    }

    private void initData(){
        expressPresenter = new ExpressPresenterImpl(this);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(context,5));
        adapter = new ExpressListAdapter(context,expressInfoList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void setExpress(Express express) {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        if (expressInfoList == null){
            expressInfoList = new ArrayList<>();
        }
        if (express.getCount()>0){
            adapter.setLoadMore(adapter.SUCCESS);
            expressInfoList.addAll(express.getData());
            adapter.setList(expressInfoList);
        }else {
            adapter.setEmpty();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        expressPresenter.getExpress(getMap());
    }

    private Map<String ,String> getMap(){
        HttpMap map = new HttpMap();
        map.put("userid", App.getApp().getUser().getUserid());
        return map.getMap();
    }
}
