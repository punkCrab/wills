package com.wills.help.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.listener.BaseListLoadMoreListener;

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
    public static final int LOAD = 3;//加载中
    public static final int SUCCESS = 4;//加载成功
    public static final int FAIL = 5;//加载失败
    public static final int EMPTY = 6;//全部加载完成
    private static int STATE = 4;//全部加载完成
    private boolean mShowFooter = true;

    private List<T> list;
    private Context context;
    private View view;
    private BaseListLoadMoreListener.LoadMoreListener loadMoreListener;

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
        } else if (viewType == TYPE_FOOTER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
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

    public void setLoadMoreListener(BaseListLoadMoreListener.LoadMoreListener loadMoreListener){
        this.loadMoreListener = loadMoreListener;
    }

    private void changeFooter(){
        if (view!=null){
            if (STATE != SUCCESS){
                if (view.getVisibility() == View.GONE){
                    view.setVisibility(View.VISIBLE);
                }
                if (STATE == LOAD){
                    view.findViewById(R.id.pb_more).setVisibility(View.VISIBLE);
                    ((TextView)view.findViewById(R.id.tv_more)).setText(context.getString(R.string.list_loading));
                }else if (STATE == FAIL){
                    view.findViewById(R.id.pb_more).setVisibility(View.GONE);
                    ((TextView)view.findViewById(R.id.tv_more)).setText(context.getString(R.string.list_fail));
                }else if (STATE == EMPTY){
                    view.findViewById(R.id.pb_more).setVisibility(View.GONE);
                    ((TextView)view.findViewById(R.id.tv_more)).setText(context.getString(R.string.list_empty));
                }
            }else {
                if (view.getVisibility() == View.VISIBLE){
                    view.setVisibility(View.GONE);
                }
            }
        }
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

    public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public FooterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (STATE!=LOAD){
                setLoadMore(LOAD);
                loadMoreListener.loadMore();
            }
        }
    }

    protected abstract RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType);
    protected abstract void BindViewHolder(RecyclerView.ViewHolder holder, int position);
}
