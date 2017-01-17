package com.wills.help.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.base.WebViewActivity;
import com.wills.help.home.model.News;
import com.wills.help.utils.IntentUtils;

import java.util.List;

/**
 * com.wills.help.home.adapter
 * Created by lizhaoyong
 * 2016/11/15.
 */

public class NewsAdapter extends BaseListAdapter<News.NewsInfo>{
    private Context context;
    private List<News.NewsInfo> list;
    public NewsAdapter(Context context, List<News.NewsInfo> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_home, parent, false);
        return new NewsHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsHolder){
            if (!list.get(position).getState().equals("0")){
                ((NewsHolder)holder).tv_title.setText(list.get(position).getTitle());
                ((NewsHolder)holder).tv_content.setText(list.get(position).getContent());
                ((NewsHolder)holder).view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url",list.get(position).getNewsurl());
                        IntentUtils.startActivity(context, WebViewActivity.class,bundle);
                    }
                });
            }
        }
    }

    public class NewsHolder extends RecyclerView.ViewHolder{
        public TextView tv_title,tv_content;
        public View view;
        public NewsHolder(View itemView) {
            super(itemView);
            this.view  = itemView;
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

}
