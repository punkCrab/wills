package com.wills.help.release.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.release.model.Release;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.release.adapter
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class ReleaseListAdapter extends BaseListAdapter<Release>{

    private int type;

    public ReleaseListAdapter(Context context, List<Release> list) {
        super(context, list);
    }

    public ReleaseListAdapter(Context context) {
        super(context);
    }

    public ReleaseListAdapter(Context context, ArrayList<Release> list,int type) {
        super(context, list);
        this.type = type;
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_release, parent, false);
        return new ReleaseHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReleaseHolder){
            if (type == 0){
                ((ReleaseHolder)holder).imageView.setImageResource(R.drawable.example_release_process);
            }else if(type == 1){
                ((ReleaseHolder)holder).imageView.setImageResource(R.drawable.example_release_complete);
            }
        }
    }

    public class ReleaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public ReleaseHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
