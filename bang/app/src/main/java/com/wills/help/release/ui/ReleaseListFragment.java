package com.wills.help.release.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseFragment;
import com.wills.help.listener.BaseListLoadMoreListener;
import com.wills.help.release.adapter.ReleaseListAdapter;
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
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class ReleaseListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener , BaseListLoadMoreListener.LoadMoreListener , ReleaseListView{

    private int type = 0;//0进行中1已完成
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ReleaseListAdapter releaseListAdapter;
    List<ReleaseInfo> releaseInfoList = new ArrayList<>();
    ReleaseListPresenterImpl releaseListPresenter;
    private int page = 1;
    private int count = 0;

    public static ReleaseListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        ReleaseListFragment fragment = new ReleaseListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.page_list,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary, R.color.colorPrimaryLight,R.color.colorAccent);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        releaseListPresenter = new ReleaseListPresenterImpl(this);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getAppCompatActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(getAppCompatActivity(),5));
        releaseListAdapter = new ReleaseListAdapter(getAppCompatActivity(), releaseInfoList,type);
        recyclerView.setAdapter(releaseListAdapter);
        BaseListLoadMoreListener loadMore = new BaseListLoadMoreListener(linearLayoutManager,releaseListAdapter);
        recyclerView.addOnScrollListener(loadMore);
        loadMore.setLoadMoreListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if(releaseInfoList != null) {
            releaseInfoList.clear();
            page = 1;
        }
        releaseListPresenter.getReleaseList(getMap());
    }

    @Override
    public void loadMore() {
        if (count>releaseInfoList.size()){
            releaseListPresenter.getReleaseList(getMap());
        }else {
            releaseListAdapter.setLoadMore(releaseListAdapter.EMPTY);
        }
    }

    private Map<String , String> getMap(){
        Map<String , String> map = new HashMap<>();
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        if (type == 0){
            map.put("action", "-1");
        }else {
            map.put("action", "-2");
        }
        map.put("page", page+"");
        return map;
    }

    @Override
    public void setReleaseList(ReleaseList releaseList) {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        count = releaseList.getCount();
        if (releaseInfoList == null ){
            releaseInfoList = new ArrayList<>();
        }
        if (releaseList.getData().size()>0){
            releaseListAdapter.setLoadMore(releaseListAdapter.SUCCESS);
            releaseInfoList.addAll(releaseList.getData());
            releaseListAdapter.setList(releaseInfoList);
            page++;
        }
    }
}
