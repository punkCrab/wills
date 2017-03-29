package com.wills.help.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.listener.BaseListLoadMoreListener;

import java.util.List;

/**
 * com.wills.help.base
 * Created by lizhaoyong
 * 2016/11/14.
 */

public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_EMPTY = 2;
    public static final int LOAD = 3;//加载中
    public static final int SUCCESS = 4;//加载成功
    public static final int FAIL = 5;//加载失败
    public static final int EMPTY = 6;//全部加载完成
    private static int STATE = 4;//全部加载完成
    private boolean mShowFooter = true;

    private List<T> list;
    private Context context;
    private View footerView ,emptyView;
    private LoadMoreListener listener;
    protected BaseItemClickListener baseItemClickListener;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    public BaseListAdapter(Context context) {
        this.context = context;
    }

    public BaseListAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    public BaseListAdapter(Context context, List<T> list, RecyclerView recyclerView,LinearLayoutManager linearLayoutManager) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            return CreateViewHolder(parent,viewType);
        } else if (viewType == TYPE_FOOTER){
            footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, null);
            footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(footerView);
        }else if (viewType == TYPE_EMPTY){
            emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_list_empty, null);
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            return new EmptyViewHolder(emptyView);
        }
        return null;
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

    public int getState(){
        return STATE;
    }

    public void setLoadMore(int state) {
        this.STATE = state;
        changeFooter();
    }

    private void changeFooter(){
        if (emptyView!=null){
            emptyView.setVisibility(View.GONE);
        }
        if (footerView!=null){
            if (STATE != SUCCESS){
                if (footerView.getVisibility() == View.GONE){
                    footerView.setVisibility(View.VISIBLE);
                }
                if (STATE == LOAD){
                    footerView.findViewById(R.id.pb_more).setVisibility(View.VISIBLE);
                    ((TextView)footerView.findViewById(R.id.tv_more)).setText(context.getString(R.string.list_loading));
                }else if (STATE == FAIL){
                    footerView.findViewById(R.id.pb_more).setVisibility(View.GONE);
                    ((TextView)footerView.findViewById(R.id.tv_more)).setText(context.getString(R.string.list_fail));
                }else if (STATE == EMPTY){
                    footerView.findViewById(R.id.pb_more).setVisibility(View.GONE);
                    ((TextView)footerView.findViewById(R.id.tv_more)).setText(context.getString(R.string.list_empty));
                }
            }else {
                if (footerView.getVisibility() == View.VISIBLE){
                    footerView.setVisibility(View.GONE);
                }
            }
        }
    }

    public void setEmpty(){
        if (emptyView!=null){
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter?1:0;//因为有角标
        if(list == null||list.size()==0) {
            return begin;
        }
        return list.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if ((position + 1 == getItemCount())) {
            if (list.size()>0){
                return TYPE_FOOTER;
            }else {
                return TYPE_EMPTY;
            }
        } else {
            return TYPE_ITEM;
        }
    }

    public void setList(List<T> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public FooterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (STATE!=LOAD){
                setLoadMore(LOAD);
                listener.loadMore();
            }
        }
    }
    public class EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View view) {
            super(view);
        }
    }
    protected abstract RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType);
    protected abstract void BindViewHolder(RecyclerView.ViewHolder holder, int position);

    public void setBaseItemClickListener(BaseItemClickListener baseItemClickListener) {
        this.baseItemClickListener = baseItemClickListener;
    }

    public interface BaseItemClickListener{
        void onItemClick(int position);
    }

    public interface LoadMoreListener{
        void loadMore();
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener){
        this.listener = loadMoreListener;
        BaseListLoadMoreListener listLoadMore = new BaseListLoadMoreListener(linearLayoutManager, this,loadMoreListener);
        recyclerView.addOnScrollListener(listLoadMore);
    }
}
