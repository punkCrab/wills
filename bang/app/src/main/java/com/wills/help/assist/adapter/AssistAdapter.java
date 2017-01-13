package com.wills.help.assist.adapter;

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
import com.wills.help.person.adapter.OrderAdapter;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.IntentUtils;

import java.util.List;

/**
 * com.wills.help.assist.adapter
 * Created by lizhaoyong
 * 2016/11/16.
 */

public class AssistAdapter extends BaseListAdapter<OrderInfo>{
    private Context context;
    private List<OrderInfo> list;
    public AssistAdapter(Context context, List<OrderInfo> list) {
        super(context, list);
        this.context=context;
        this.list=list;
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assist, parent, false);
        return new AssistHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AssistHolder){
            final OrderInfo orderInfo = list.get(position);
            GlideUtils.getInstance().displayCircleImage(context,orderInfo.getReleaseavatar(),((AssistHolder)holder).imageView);
            if (orderInfo.getOrdertype().equals("1")){
                ((AssistHolder)holder).tv_assist_state.setText(context.getString(R.string.help_express));
            }else if (orderInfo.getOrdertype().equals("2")){
                ((AssistHolder)holder).tv_assist_state.setText(context.getString(R.string.help_buy));
            }
            ((AssistHolder)holder).tv_name.setText(orderInfo.getReleaseusername());
            ((AssistHolder)holder).tv_assist_time.setText(orderInfo.getCreatetime());
            ((AssistHolder)holder).tv_assist_location.setText(orderInfo.getSrcdetail());
            ((AssistHolder)holder).tv_assist_address.setText(orderInfo.getDesdetail());
            ((AssistHolder)holder).tv_assist_money.setText(orderInfo.getMoney());
            ((AssistHolder)holder).tv_assist_progress.setVisibility(View.INVISIBLE);
            ((OrderAdapter.OrderHolder)holder).iv_assist_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EaseConstant.EXTRA_USER_ID,orderInfo.getReleaseusername());
                    IntentUtils.startActivity(context,ChatActivity.class,bundle);
                }
            });
            ((AssistHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    baseItemClickListener.onItemClick(position);
                }
            });
        }
    }

    public class AssistHolder extends RecyclerView.ViewHolder{
        public View itemView;
        public ImageView imageView;
        public TextView tv_name,tv_school,tv_assist_state,tv_assist_address,tv_assist_time,
                tv_assist_money,tv_assist_location,tv_assist_progress;
        public AssistHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_school = (TextView) itemView.findViewById(R.id.tv_school);
            tv_assist_state = (TextView) itemView.findViewById(R.id.tv_assist_state);
            tv_assist_address = (TextView) itemView.findViewById(R.id.tv_assist_address);
            tv_assist_time = (TextView) itemView.findViewById(R.id.tv_assist_time);
            tv_assist_money = (TextView) itemView.findViewById(R.id.tv_assist_money);
            tv_assist_location = (TextView) itemView.findViewById(R.id.tv_assist_location);
            tv_assist_progress = (TextView) itemView.findViewById(R.id.tv_assist_progress);
        }
    }
}
