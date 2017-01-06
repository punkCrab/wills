package com.wills.help.release.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.message.EaseConstant;
import com.wills.help.message.ui.ChatActivity;
import com.wills.help.release.model.Release;
import com.wills.help.release.ui.ReleaseActivity;
import com.wills.help.utils.IntentUtils;

import java.util.List;

/**
 * com.wills.help.release.adapter
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class ReleaseListAdapter extends BaseListAdapter<Release>{

    private int type;
    private Context context;
    private List<Release> list;

    public ReleaseListAdapter(Context context, List<Release> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    public ReleaseListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public ReleaseListAdapter(Context context, List<Release> list,int type) {
        super(context, list);
        this.type = type;
        this.context = context;
        this.list = list;
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_release, parent, false);
        return new ReleaseHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ReleaseHolder){
            if (type == 0){
                changeView(holder,list.get(position).getState());
            }else if(type == 1){
                changeView(holder,4);
            }
            ((ReleaseHolder)holder).iv_release_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EaseConstant.EXTRA_USER_ID,list.get(position).getName());
                    IntentUtils.startActivity(context,ChatActivity.class,bundle);
                }
            });
        }
    }

    private void changeView(RecyclerView.ViewHolder holder , final int state){
        switch (state){
            case 1://发布
                ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).tv_release_change.setVisibility(View.VISIBLE);
                ((ReleaseHolder)holder).tv_release_change.setText(context.getString(R.string.release_state_change));
                ((ReleaseHolder)holder).tv_release_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("type",0);
                        IntentUtils.startActivity(context,ReleaseActivity.class,bundle);
                    }
                });
                break;
            case 2://接单
                ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).layout_progress.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).tv_release_change.setVisibility(View.INVISIBLE);
                break;
            case 3://送达
                ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).layout_progress.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).layout_arrive.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).tv_release_change.setVisibility(View.VISIBLE);
                ((ReleaseHolder)holder).tv_release_change.setText(context.getString(R.string.release_state_ok));
                ((ReleaseHolder)holder).tv_release_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case 4://已完成
                ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).layout_progress.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).layout_arrive.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).layout_complete.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                ((ReleaseHolder)holder).tv_release_change.setText(context.getString(R.string.release_state_evaluation));
                ((ReleaseHolder)holder).tv_release_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
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
