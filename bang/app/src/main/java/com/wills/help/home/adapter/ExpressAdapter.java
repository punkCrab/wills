package com.wills.help.home.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.home.model.Express;
import com.wills.help.home.ui.SendActivity;
import com.wills.help.utils.IntentUtils;

import java.util.List;

/**
 * com.wills.help.home.adapter
 * Created by lizhaoyong
 * 2016/12/26.
 */

public class ExpressAdapter extends BaseListAdapter<Express.ExpressInfo>{
    private Activity context;
    private List<Express.ExpressInfo> list;

    public ExpressAdapter(Activity context, List<Express.ExpressInfo> list) {
        super(context, list);
        this.context = context;
        this.list = list;
        setFooter(false);
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_express, parent, false);
        return new ExpressHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ExpressHolder){
            ((ExpressHolder)holder).tv_info.setText(list.get(position).getDeliverytype()+"\t"+list.get(position).getDeliveryid());
            if (list.get(position).getStateid().equals("0")){
                ((ExpressHolder)holder).btn_send.setTextColor(context.getResources().getColor(R.color.white));
                ((ExpressHolder)holder).btn_send.setBackgroundResource(R.drawable.btn_selector);
                ((ExpressHolder)holder).btn_send.setText(context.getString(R.string.home_express_send));
                ((ExpressHolder)holder).btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("deliveryid",list.get(position).getDeliveryid());
                        IntentUtils.startActivityForResult(context,SendActivity.class,bundle,301);
                    }
                });
            }else {
                ((ExpressHolder)holder).btn_send.setTextColor(context.getResources().getColor(R.color.textSecondary));
                ((ExpressHolder)holder).btn_send.setBackgroundDrawable(null);
                ((ExpressHolder)holder).btn_send.setText(list.get(position).getState());
            }
        }
    }

    public class ExpressHolder extends RecyclerView.ViewHolder{
        public TextView tv_info;
        public Button btn_send;

        public ExpressHolder(View itemView) {
            super(itemView);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            btn_send  = (Button) itemView.findViewById(R.id.btn_send);
        }
    }
}
