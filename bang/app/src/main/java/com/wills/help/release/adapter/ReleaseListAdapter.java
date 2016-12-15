package com.wills.help.release.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Context context;

    public ReleaseListAdapter(Context context, List<Release> list) {
        super(context, list);
        this.context = context;
    }

    public ReleaseListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public ReleaseListAdapter(Context context, ArrayList<Release> list,int type) {
        super(context, list);
        this.type = type;
        this.context = context;
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
                ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
            }else if(type == 1){
                ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).layout_progress.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).layout_arrive.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).layout_complete.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
            }
        }
    }

    public class ReleaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView , iv_release_msg ;
        public TextView tv_name,tv_count,tv_release_state,tv_release_address,tv_release_time,
                tv_release_money,tv_release_change;
        public View layout_release,layout_progress,layout_arrive,layout_complete;
        public ReleaseHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            iv_release_msg = (ImageView) itemView.findViewById(R.id.iv_release_msg);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_release_state = (TextView) itemView.findViewById(R.id.tv_release_state);
            tv_release_address = (TextView) itemView.findViewById(R.id.tv_release_address);
            tv_release_time = (TextView) itemView.findViewById(R.id.tv_release_time);
            tv_release_money = (TextView) itemView.findViewById(R.id.tv_release_money);
            tv_release_change = (TextView) itemView.findViewById(R.id.tv_release_change);
            itemView.setOnClickListener(this);
            iv_release_msg.setOnClickListener(this);
            tv_release_change.setOnClickListener(this);
            layout_release = itemView.findViewById(R.id.layout_release);
            layout_progress = itemView.findViewById(R.id.layout_progress);
            layout_arrive = itemView.findViewById(R.id.layout_arrive);
            layout_complete = itemView.findViewById(R.id.layout_complete);
            ((TextView)layout_release.findViewById(R.id.tv_state)).setText(context.getString(R.string.release_state_release));
            ((TextView)layout_progress.findViewById(R.id.tv_state)).setText(context.getString(R.string.release_state_progress));
            ((TextView)layout_arrive.findViewById(R.id.tv_state)).setText(context.getString(R.string.release_state_arrive));
            ((TextView)layout_complete.findViewById(R.id.tv_state)).setText(context.getString(R.string.release_state_complete));
        }

        @Override
        public void onClick(View view) {

        }
    }
}
