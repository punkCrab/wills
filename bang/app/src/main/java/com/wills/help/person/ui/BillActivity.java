package com.wills.help.person.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.net.HttpMap;
import com.wills.help.person.adapter.BillAdapter;
import com.wills.help.person.model.Bill;
import com.wills.help.person.presenter.BillPresenterImpl;
import com.wills.help.person.view.BillView;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class BillActivity extends BaseActivity implements BaseListAdapter.LoadMoreListener , SwipeRefreshLayout.OnRefreshListener,BillView{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    BillAdapter adapter;
    private List<Bill.BillInfo> billInfoList = new ArrayList<>();
    private int page = 1;
    private BillPresenterImpl billPresenter;
    private int count;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.page_list);
        setBaseTitle(getString(R.string.pay_history));
        recyclerView = (RecyclerView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary, R.color.colorPrimaryLight,R.color.colorAccent);
        initData();
    }

    private void initData(){
        billPresenter = new BillPresenterImpl(this);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(context,5));
        adapter = new BillAdapter(context,billInfoList,recyclerView,linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setLoadMoreListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void loadMore() {
        if (count>billInfoList.size()){
            billPresenter.getBill(getMap());
        }else {
            adapter.setLoadMore(adapter.EMPTY);
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (billInfoList !=null){
            billInfoList.clear();
            page =1;
        }
        billPresenter.getBill(getMap());
    }

    private Map<String ,String> getMap(){
        HttpMap map = new HttpMap();
        map.put("userid", App.getApp().getUser().getUserid());
        map.put("page", page+"");
        return map.getMap();
    }

    @Override
    public void setBill(Bill bill) {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        count = bill.getCount();
        if (billInfoList == null ){
            billInfoList = new ArrayList<>();
        }
        if (bill.getData().size()>0){
            adapter.setLoadMore(adapter.SUCCESS);
            billInfoList.addAll(bill.getData());
            adapter.setList(billInfoList);
            page++;
        }else {
            if(billInfoList.size()>0){
                adapter.setLoadMore(adapter.EMPTY);
            }else {
                adapter.setEmpty();
            }
        }
    }
}
