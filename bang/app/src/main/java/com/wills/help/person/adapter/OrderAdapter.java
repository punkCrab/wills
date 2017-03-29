package com.wills.help.person.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.home.ui.SendActivity;
import com.wills.help.message.EaseConstant;
import com.wills.help.message.ui.ChatActivity;
import com.wills.help.pay.ui.PayActivity;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.release.ui.AppraiseActivity;
import com.wills.help.release.ui.ReleaseActivity;
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

    public OrderAdapter(Context context, List<OrderInfo> list, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager, int mainType, int type) {
        super(context, list, recyclerView, linearLayoutManager);
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
    protected void BindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OrderHolder){
            final OrderInfo orderInfo = list.get(position);
            ((OrderHolder)holder).tv_assist_state.setText(context.getString(R.string.help)+orderInfo.getOrdertypename());
            if (orderInfo.getMaintype().equals("0")){
                ((OrderHolder)holder).iv_home_express.setVisibility(View.GONE);
            }else if (orderInfo.getMaintype().equals("1")){
                ((OrderHolder)holder).iv_home_express.setVisibility(View.VISIBLE);
            }
            Drawable drawable =null;
            if (orderInfo.getReleasesex().equals("1")){
                drawable = context.getResources().getDrawable(R.drawable.sex_girl);
            }else {
                drawable = context.getResources().getDrawable(R.drawable.sex_boy);
            }
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            ((OrderHolder)holder).tv_name.setCompoundDrawables(drawable,null,null,null);
            ((OrderHolder)holder).tv_assist_location.setText(orderInfo.getSrcname()+orderInfo.getSrcdetail());
            ((OrderHolder)holder).tv_assist_address.setText(orderInfo.getDesname()+orderInfo.getDesdetail());
            ((OrderHolder)holder).tv_assist_time.setText(orderInfo.getCreatetime());
            ((OrderHolder)holder).tv_assist_money.setText(orderInfo.getMoney()+context.getString(R.string.yuan));
            if (mainType == 0){
                if (orderInfo.getStateid().equals("0")||orderInfo.getStateid().equals("1")){
                    GlideUtils.getInstance().displayCircleImage(context ,orderInfo.getReleaseavatar() ,((OrderHolder)holder).imageView);
                    ((OrderHolder)holder).tv_name.setText(orderInfo.getReleasenickname());
                    ((OrderHolder)holder).iv_assist_msg.setVisibility(View.INVISIBLE);
                }else {
                    GlideUtils.getInstance().displayCircleImage(context ,orderInfo.getAcceptavatar() ,((OrderHolder)holder).imageView);
                    ((OrderHolder)holder).tv_name.setText(orderInfo.getAcceptnickname());
                    ((OrderHolder)holder).iv_assist_msg.setVisibility(View.VISIBLE);
                    ((OrderHolder)holder).iv_assist_msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString(EaseConstant.EXTRA_USER_ID,orderInfo.getAcceptusername());
                            bundle.putString(EaseConstant.EXTRA_USER_AVATAR,orderInfo.getAcceptavatar());
                            bundle.putString(EaseConstant.EXTRA_USER_NAME,orderInfo.getAcceptnickname());
                            IntentUtils.startActivity(context,ChatActivity.class,bundle);
                        }
                    });
                }
                if (orderInfo.getStateid().equals("4")){
                    ((OrderHolder)holder).tv_assist_progress.setText(context.getString(R.string.release_state_evaluation));
                    ((OrderHolder)holder).tv_assist_progress.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((OrderHolder)holder).tv_assist_progress.setBackgroundResource(R.drawable.release_shape);
                    ((OrderHolder)holder).tv_assist_progress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("orderId",orderInfo.getOrderid());
                            IntentUtils.startActivity(context,AppraiseActivity.class,bundle);
                        }
                    });
                }else if (orderInfo.getStateid().equals("2")||orderInfo.getStateid().equals("3")){
                    ((OrderHolder)holder).tv_assist_progress.setText(context.getString(R.string.release_state_ok));
                    ((OrderHolder)holder).tv_assist_progress.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((OrderHolder)holder).tv_assist_progress.setBackgroundResource(R.drawable.release_shape);
                    ((OrderHolder)holder).tv_assist_progress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (buttonClickListener!=null){
                                buttonClickListener.buttonClick(2,orderInfo);
                            }
                        }
                    });
                }else if (orderInfo.getStateid().equals("0")){
                    ((OrderHolder)holder).tv_assist_progress.setText(context.getString(R.string.pay));
                    ((OrderHolder)holder).tv_assist_progress.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((OrderHolder)holder).tv_assist_progress.setBackgroundResource(R.drawable.release_shape);
                    ((OrderHolder)holder).tv_assist_progress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("orderId",orderInfo.getOrderid());
                            IntentUtils.startActivityForResult((Activity)context,PayActivity.class,bundle,401);
                        }
                    });
                }else if (orderInfo.getStateid().equals("1")) {
                    ((OrderHolder)holder).tv_assist_progress.setText(context.getString(R.string.release_state_change));
                    ((OrderHolder)holder).tv_assist_progress.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((OrderHolder)holder).tv_assist_progress.setBackgroundResource(R.drawable.release_shape);
                    ((OrderHolder)holder).tv_assist_progress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("orderInfo",orderInfo);
                            if (orderInfo.getMaintype().equals("0")){
                                IntentUtils.startActivityForResult((Activity)context,ReleaseActivity.class,bundle,401);
                            }else {
                                IntentUtils.startActivityForResult((Activity)context,SendActivity.class,bundle,401);
                            }
                        }
                    });
                }else {
                    ((OrderHolder)holder).tv_assist_progress.setText(context.getString(R.string.release_state_evaluated));
                    ((OrderHolder)holder).tv_assist_progress.setTextColor(context.getResources().getColor(R.color.textSecondary));
                    ((OrderHolder)holder).tv_assist_progress.setBackgroundDrawable(null);
                    ((OrderHolder)holder).tv_assist_progress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            }else if (mainType == 1){
                GlideUtils.getInstance().displayCircleImage(context ,orderInfo.getReleaseavatar() ,((OrderHolder)holder).imageView);
                ((OrderHolder)holder).tv_name.setText(orderInfo.getReleasenickname());
                ((OrderHolder)holder).iv_assist_msg.setVisibility(View.VISIBLE);
                ((OrderHolder)holder).tv_assist_progress.setVisibility(View.GONE);
                ((OrderHolder)holder).iv_assist_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString(EaseConstant.EXTRA_USER_ID,orderInfo.getReleaseusername());
                        bundle.putString(EaseConstant.EXTRA_USER_AVATAR,orderInfo.getReleaseavatar());
                        bundle.putString(EaseConstant.EXTRA_USER_NAME,orderInfo.getReleasenickname());
                        IntentUtils.startActivity(context,ChatActivity.class,bundle);
                    }
                });
                if (orderInfo.getStateid().equals("2")){
                    ((OrderHolder)holder).tv_assist_progress.setVisibility(View.VISIBLE);
                    ((OrderHolder)holder).tv_assist_progress.setText(context.getString(R.string.release_state_arrive));
                    ((OrderHolder)holder).tv_assist_progress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (buttonClickListener!=null){
                                buttonClickListener.buttonClick(1,orderInfo);
                            }
                        }
                    });
                }else {
                    ((OrderHolder)holder).tv_assist_progress.setVisibility(View.GONE);
                }
            }
            ((OrderHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    baseItemClickListener.onItemClick(position);
                }
            });
        }
    }

    public class OrderHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public View itemView;
        public ImageView imageView ,iv_assist_msg ,iv_home_express;
        public TextView tv_assist_state,tv_assist_location,tv_assist_address,tv_assist_time,tv_assist_money,tv_assist_progress,
                tv_name,tv_school;
        public OrderHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            iv_home_express = (ImageView) itemView.findViewById(R.id.iv_home_express);
            iv_home_express.getBackground().setAlpha(50);
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

    private ButtonClickListener buttonClickListener;

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    public interface ButtonClickListener{
        //1:进行中2：确认收货
        void buttonClick(int state ,OrderInfo releaseInfo);
    }
}
