package com.wills.help.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wills.help.R;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.base
 * Created by lizhaoyong
 * 2016/11/14.
 */

public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean mShowFooter = true;
    private boolean isLoadMore = false;

    private List<T> list;
    private Context context;

    public BaseListAdapter(Context context) {
        this.context = context;
    }

    public BaseListAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            return CreateViewHolder(parent,viewType);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         BindViewHolder(holder,position);
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public void setFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isLoadMore(){
        return this.isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    @Override
    public int getItemCount() {
        if(list == null||list.size()==0) {
            return 0;
        }
        int begin = mShowFooter?1:0;//因为有角标
        return list.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if ((position + 1 == getItemCount())) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setList(ArrayList<T> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }
    }

    protected abstract RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType);
    protected abstract void BindViewHolder(RecyclerView.ViewHolder holder, int position);
}
