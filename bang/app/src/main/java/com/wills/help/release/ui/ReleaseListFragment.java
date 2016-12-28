package com.wills.help.release.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.listener.BaseListLoadMoreListener;
import com.wills.help.release.adapter.ReleaseListAdapter;
import com.wills.help.release.model.Release;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class ReleaseListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener , BaseListLoadMoreListener.LoadMoreListener{

    private int type = 0;//0进行中1已完成
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ReleaseListAdapter releaseListAdapter;
    ArrayList<Release> list = new ArrayList<>();

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
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getAppCompatActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(getAppCompatActivity(),5));
        releaseListAdapter = new ReleaseListAdapter(getAppCompatActivity(),list,type);
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
        if(list != null) {
            list.clear();
        }
        handler.sendEmptyMessageDelayed(1,2000);
    }

    @Override
    public void loadMore() {
        if (list==null){
            list = new ArrayList<>();
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
                    if (list.size()<3){
                        releaseListAdapter.setLoadMore(releaseListAdapter.SUCCESS);
                        list.addAll(getRelease());
                    }else {
                        releaseListAdapter.setLoadMore(releaseListAdapter.EMPTY);
                    }
                    releaseListAdapter.setList(list);
                    break;
            }
        }
    };

    private ArrayList<Release> getRelease(){
        ArrayList<Release> list = new ArrayList<>();
        for (int i =0 ; i<3 ;i++){
            Release release = new Release();
            release.setName("快递小哥吴彦祖");
            release.setState(i+1);
            list.add(release);
        }
        return list;
    }
}
