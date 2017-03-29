package com.wills.help.release.adapter;

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
import android.widget.LinearLayout;
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
 * com.wills.help.release.adapter
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class ReleaseListAdapter extends BaseListAdapter<OrderInfo>{

    private int type;
    private Context context;
    private List<OrderInfo> list;

    public ReleaseListAdapter(Context context, List<OrderInfo> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    public ReleaseListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public ReleaseListAdapter(Context context, List<OrderInfo> list, int type) {
        super(context, list);
        this.type = type;
        this.context = context;
        this.list = list;
    }

    public ReleaseListAdapter(Context context, List<OrderInfo> list, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager, int type) {
        super(context, list, recyclerView, linearLayoutManager);
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_release, parent, false);
        return new ReleaseHolder(view);
    }

    @Override
    protected void BindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ReleaseHolder){
            final OrderInfo releaseInfo = list.get(position);
            if (type == 0){
                changeView(holder,Integer.parseInt(releaseInfo.getStateid()),releaseInfo);
            }else if(type == 1){
                changeView(holder,Integer.parseInt(releaseInfo.getStateid()),releaseInfo);
            }
            ((ReleaseHolder)holder).tv_release_state.setText(context.getString(R.string.help)+releaseInfo.getOrdertypename());
            if (releaseInfo.getMaintype().equals("0")){
                ((ReleaseHolder)holder).iv_home_express.setVisibility(View.GONE);
                ((ReleaseHolder)holder).tv_release_money.setVisibility(View.VISIBLE);
            }else if (releaseInfo.getMaintype().equals("1")){
                ((ReleaseHolder)holder).iv_home_express.setVisibility(View.VISIBLE);
                ((ReleaseHolder)holder).tv_release_money.setVisibility(View.GONE);
            }
            Drawable drawable =null;
            if (releaseInfo.getReleasesex().equals("1")){
                drawable = context.getResources().getDrawable(R.drawable.sex_girl);
            }else {
                drawable = context.getResources().getDrawable(R.drawable.sex_boy);
            }
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            ((ReleaseHolder)holder).tv_name.setCompoundDrawables(drawable,null,null,null);
            ((ReleaseHolder)holder).tv_release_location.setText(releaseInfo.getSrcname());
            ((ReleaseHolder)holder).tv_release_address.setText(releaseInfo.getDesname());
            ((ReleaseHolder)holder).tv_release_money.setText(releaseInfo.getMoney()+context.getString(R.string.yuan));
            ((ReleaseHolder)holder).tv_release_time.setText(releaseInfo.getCreatetime()+"发布");
            ((ReleaseHolder)holder).iv_release_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EaseConstant.EXTRA_USER_ID,releaseInfo.getAcceptusername());
                    bundle.putString(EaseConstant.EXTRA_USER_AVATAR,releaseInfo.getAcceptavatar());
                    bundle.putString(EaseConstant.EXTRA_USER_NAME,releaseInfo.getAcceptnickname());
                    IntentUtils.startActivity(context,ChatActivity.class,bundle);
                }
            });
            ((ReleaseHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    baseItemClickListener.onItemClick(position);
                }
            });
        }
    }

    private void changeView(RecyclerView.ViewHolder holder , final int state , final OrderInfo releaseInfo){
        switch (state){
            case 0:
                GlideUtils.getInstance().displayCircleImage(context,releaseInfo.getReleaseavatar(),((ReleaseHolder)holder).imageView);
                ((ReleaseHolder)holder).tv_name.setText(releaseInfo.getReleasenickname());
                ((ReleaseHolder)holder).tv_release_change.setText(context.getString(R.string.pay));
                ((ReleaseHolder)holder).tv_release_change.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((ReleaseHolder)holder).tv_release_change.setBackgroundResource(R.drawable.release_shape);
                ((ReleaseHolder)holder).iv_release_msg.setVisibility(View.GONE);
                if (type == 0){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.VISIBLE);
                }else if (type == 1){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.GONE);
                }
                ((ReleaseHolder)holder).tv_release_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId",releaseInfo.getOrderid());
                        IntentUtils.startActivityForResult((Activity)context,PayActivity.class,bundle,401);
                    }
                });
                break;
            case 1://发布
                GlideUtils.getInstance().displayCircleImage(context,releaseInfo.getReleaseavatar(),((ReleaseHolder)holder).imageView);
                ((ReleaseHolder)holder).tv_name.setText(releaseInfo.getReleasenickname());
                if (type == 0){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.VISIBLE);
                    ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_progress.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_false);
                    ((ReleaseHolder)holder).layout_arrive.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_false);
                    ((ReleaseHolder)holder).layout_complete.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_false);
                }else if (type == 1){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.GONE);
                }
                ((ReleaseHolder)holder).iv_release_msg.setVisibility(View.GONE);
                ((ReleaseHolder)holder).tv_release_change.setText(context.getString(R.string.release_state_change));
                ((ReleaseHolder)holder).tv_release_change.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((ReleaseHolder)holder).tv_release_change.setBackgroundResource(R.drawable.release_shape);
                ((ReleaseHolder)holder).tv_release_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderInfo",releaseInfo);
                        if (releaseInfo.getMaintype().equals("0")){
                            IntentUtils.startActivityForResult((Activity)context,ReleaseActivity.class,bundle,401);
                        }else {
                            IntentUtils.startActivityForResult((Activity)context,SendActivity.class,bundle,401);
                        }
                    }
                });
                break;
            case 2://接单
                GlideUtils.getInstance().displayCircleImage(context,releaseInfo.getAcceptavatar(),((ReleaseHolder)holder).imageView);
                ((ReleaseHolder)holder).tv_name.setText(releaseInfo.getReleasenickname());
                if (type == 0){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.VISIBLE);
                    ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_progress.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_arrive.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_false);
                    ((ReleaseHolder)holder).layout_complete.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_false);
                }else if (type == 1){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.GONE);
                }
                ((ReleaseHolder)holder).iv_release_msg.setVisibility(View.VISIBLE);
                ((ReleaseHolder)holder).tv_release_change.setText(context.getString(R.string.release_state_ok));
                ((ReleaseHolder)holder).tv_release_change.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((ReleaseHolder)holder).tv_release_change.setBackgroundResource(R.drawable.release_shape);
                ((ReleaseHolder)holder).tv_release_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (buttonClickListener!=null){
                            buttonClickListener.buttonClick(2,releaseInfo);
                        }
                    }
                });
                break;
            case 3://进行中
                GlideUtils.getInstance().displayCircleImage(context,releaseInfo.getAcceptavatar(),((ReleaseHolder)holder).imageView);
                ((ReleaseHolder)holder).tv_name.setText(releaseInfo.getAcceptnickname());
                if (type == 0){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.VISIBLE);
                    ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_progress.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_arrive.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_complete.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_false);
                }else if (type == 1){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.GONE);
                }
                ((ReleaseHolder)holder).iv_release_msg.setVisibility(View.VISIBLE);
                ((ReleaseHolder)holder).tv_release_change.setText(context.getString(R.string.release_state_ok));
                ((ReleaseHolder)holder).tv_release_change.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((ReleaseHolder)holder).tv_release_change.setBackgroundResource(R.drawable.release_shape);
                ((ReleaseHolder)holder).tv_release_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (buttonClickListener!=null){
                            buttonClickListener.buttonClick(2,releaseInfo);
                        }
                    }
                });
                break;
            case 4://送达
                GlideUtils.getInstance().displayCircleImage(context,releaseInfo.getAcceptavatar(),((ReleaseHolder)holder).imageView);
                ((ReleaseHolder)holder).tv_name.setText(releaseInfo.getAcceptnickname());
                if (type == 0){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.VISIBLE);
                    ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_progress.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_arrive.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_complete.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                }else if (type == 1){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.GONE);
                }
                ((ReleaseHolder)holder).iv_release_msg.setVisibility(View.VISIBLE);
                ((ReleaseHolder)holder).tv_release_change.setText(context.getString(R.string.release_state_evaluation));
                ((ReleaseHolder)holder).tv_release_change.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((ReleaseHolder)holder).tv_release_change.setBackgroundResource(R.drawable.release_shape);
                ((ReleaseHolder)holder).tv_release_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderId",releaseInfo.getOrderid());
                        IntentUtils.startActivity(context,AppraiseActivity.class,bundle);
                    }
                });
                break;
            case 5://已完成
                GlideUtils.getInstance().displayCircleImage(context,releaseInfo.getAcceptavatar(),((ReleaseHolder)holder).imageView);
                ((ReleaseHolder)holder).tv_name.setText(releaseInfo.getAcceptnickname());
                if (type == 0){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.VISIBLE);
                    ((ReleaseHolder)holder).layout_release.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_progress.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_arrive.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                    ((ReleaseHolder)holder).layout_complete.findViewById(R.id.iv_state).setBackgroundResource(R.drawable.release_state_true);
                }else if (type == 1){
                    ((ReleaseHolder)holder).ll_state.setVisibility(View.GONE);
                }
                ((ReleaseHolder)holder).iv_release_msg.setVisibility(View.VISIBLE);
                ((ReleaseHolder)holder).tv_release_change.setText(context.getString(R.string.release_state_evaluated));
                ((ReleaseHolder)holder).tv_release_change.setTextColor(context.getResources().getColor(R.color.textSecondary));
                ((ReleaseHolder)holder).tv_release_change.setBackgroundDrawable(null);
                ((ReleaseHolder)holder).tv_release_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
        }
    }

    public class ReleaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View itemView;
        public ImageView imageView , iv_release_msg ,iv_home_express;
        public TextView tv_name,tv_count,tv_release_state,tv_release_location,tv_release_address,tv_release_time,
                tv_release_money,tv_release_change;
        public View layout_release,layout_progress,layout_arrive,layout_complete;
        public LinearLayout ll_state;
        public ReleaseHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            iv_home_express = (ImageView) itemView.findViewById(R.id.iv_home_express);
            iv_home_express.getBackground().setAlpha(50);
            iv_release_msg = (ImageView) itemView.findViewById(R.id.iv_release_msg);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_release_state = (TextView) itemView.findViewById(R.id.tv_release_state);
            tv_release_location = (TextView) itemView.findViewById(R.id.tv_release_location);
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
            ll_state = (LinearLayout) itemView.findViewById(R.id.ll_state);
            ((TextView)layout_release.findViewById(R.id.tv_state)).setText(context.getString(R.string.release_state_release));
            ((TextView)layout_progress.findViewById(R.id.tv_state)).setText(context.getString(R.string.release_state_progress));
            ((TextView)layout_arrive.findViewById(R.id.tv_state)).setText(context.getString(R.string.release_state_arrive));
            ((TextView)layout_complete.findViewById(R.id.tv_state)).setText(context.getString(R.string.release_state_complete));
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
        void buttonClick(int state , OrderInfo releaseInfo);
    }
}
