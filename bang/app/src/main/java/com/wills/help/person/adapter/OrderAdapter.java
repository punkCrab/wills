package com.wills.help.person.adapter;

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
import com.wills.help.release.model.OrderInfo;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.IntentUtils;

import java.util.List;

/**
 * com.wills.help.person.adapter
 * Created by lizhaoyong
 * 2016/12/5.
 */

public class OrderAdapter extends BaseListAdapter<OrderInfo>{
    private Context context;
    private List<OrderInfo> list;
    private int type,mainType;
    public OrderAdapter(Context context, List<OrderInfo> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    public OrderAdapter(Context context, List<OrderInfo> list,int mainType,int type) {
        super(context, list);
        this.context = context;
        this.list = list;
        this.mainType = mainType;
        this.type = type;
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assist, parent, false);
        return new OrderHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderHolder){
            final OrderInfo orderInfo = list.get(position);
            GlideUtils.getInstance().displayCircleImage(context ,orderInfo.getReleaseavatar() ,((OrderHolder)holder).imageView);
            ((OrderHolder)holder).tv_name.setText(orderInfo.getReleasenickname());
            if (orderInfo.getOrdertype().equals("1")){
                ((OrderHolder)holder).tv_assist_state.setText(context.getString(R.string.help_express));
            }else if (orderInfo.getOrdertype().equals("2")){
                ((OrderHolder)holder).tv_assist_state.setText(context.getString(R.string.help_buy));
            }
            if (!orderInfo.getStateid().equals("0")&&!orderInfo.getStateid().equals("1")){
                ((OrderHolder)holder).iv_assist_msg.setVisibility(View.VISIBLE);
            }else {
                ((OrderHolder)holder).iv_assist_msg.setVisibility(View.INVISIBLE);
            }
            ((OrderHolder)holder).tv_assist_location.setText(orderInfo.getSrcdetail());
            ((OrderHolder)holder).tv_assist_address.setText(orderInfo.getDesdetail());
            ((OrderHolder)holder).tv_assist_time.setText(orderInfo.getCreatetime());
            ((OrderHolder)holder).tv_assist_money.setText(orderInfo.getMoney()+context.getString(R.string.yuan));
            ((OrderHolder)holder).iv_assist_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    if (mainType == 0){
                        bundle.putString(EaseConstant.EXTRA_USER_ID,orderInfo.getAcceptusername());
                    }else if (mainType == 1){
                        bundle.putString(EaseConstant.EXTRA_USER_ID,orderInfo.getReleaseusername());
                    }
                    IntentUtils.startActivity(context,ChatActivity.class,bundle);
                }
            });
            if (orderInfo.getStateid().equals("3")){
                ((OrderHolder)holder).tv_assist_progress.setVisibility(View.VISIBLE);
                ((OrderHolder)holder).tv_assist_progress.setText(context.getString(R.string.release_state_evaluation));
            }else {
                ((OrderHolder)holder).tv_assist_progress.setVisibility(View.GONE);
            }
        }
    }

    public class OrderHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public ImageView imageView ,iv_assist_msg;
        public TextView tv_assist_state,tv_assist_location,tv_assist_address,tv_assist_time,tv_assist_money,tv_assist_progress,
                tv_name,tv_school;
        public OrderHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            iv_assist_msg = (ImageView) itemView.findViewById(R.id.iv_assist_msg);
            tv_assist_state = (TextView) itemView.findViewById(R.id.tv_assist_state);
            tv_assist_address = (TextView) itemView.findViewById(R.id.tv_assist_address);
            tv_assist_location = (TextView) itemView.findViewById(R.id.tv_assist_location);
            tv_assist_time = (TextView) itemView.findViewById(R.id.tv_assist_time);
            tv_assist_money = (TextView) itemView.findViewById(R.id.tv_assist_money);
            tv_assist_progress = (TextView) itemView.findViewById(R.id.tv_assist_progress);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_school = (TextView) itemView.findViewById(R.id.tv_school);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
