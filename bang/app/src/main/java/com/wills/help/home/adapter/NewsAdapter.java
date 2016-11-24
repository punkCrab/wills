package com.wills.help.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.home.model.News;

import java.util.List;

/**
 * com.wills.help.home.adapter
 * Created by lizhaoyong
 * 2016/11/15.
 */

public class NewsAdapter extends BaseListAdapter<News>{

    public NewsAdapter(Context context, List<News> list) {
        super(context, list);
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_home, parent, false);
        return new NewsHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsHolder){
            ((NewsHolder)holder).imageView.setImageResource(R.drawable.example_news);
        }
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public NewsHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

}
