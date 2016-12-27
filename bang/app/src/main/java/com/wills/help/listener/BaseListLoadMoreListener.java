package com.wills.help.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wills.help.base.BaseListAdapter;

/**
 * com.wills.help.base
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class BaseListLoadMoreListener extends RecyclerView.OnScrollListener {

    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    private BaseListAdapter adapter;
    private LoadMoreListener loadMoreListener;
    private boolean isScrolled = false;
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && lastVisibleItem + 1 == adapter.getItemCount()
                && adapter.isShowFooter()
                && adapter.getState() != adapter.LOAD
                && isScrolled) {
            adapter.setLoadMore(adapter.LOAD);
            loadMoreListener.loadMore();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (dy>0)
            isScrolled = true;
        else
            isScrolled = false;
    }

    public BaseListLoadMoreListener() {
        super();
    }

    public BaseListLoadMoreListener(LinearLayoutManager linearLayoutManager , BaseListAdapter adapter) {
        this.linearLayoutManager = linearLayoutManager;
        this.adapter = adapter;
    }

    public interface LoadMoreListener{
        void loadMore();
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener){
        this.loadMoreListener = loadMoreListener;
        adapter.setLoadMoreListener(loadMoreListener);
    }
}
