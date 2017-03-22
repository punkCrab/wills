package com.wills.help.home.adapter;

import android.app.Activity;
import android.content.Context;
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
 * 2017/3/20.
 */

public class ExpressListAdapter extends BaseListAdapter<Express.ExpressInfo>{

    private Context context;
    private List<Express.ExpressInfo> list;

    public ExpressListAdapter(Context context, List<Express.ExpressInfo> list) {
        super(context, list);
        this.context = context;
        this.list = list;
//        setFooter(false);
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_express, parent, false);
        return new ExpressListHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ExpressListHolder){
            ((ExpressListHolder)holder).tv_type.setText(list.get(position).getDeliverytype());
            ((ExpressListHolder)holder).tv_no.setText(list.get(position).getDeliveryid());
            if (list.get(position).getStateid().equals("0")){
                ((ExpressListHolder)holder).btn_state.setTextColor(context.getResources().getColor(R.color.white));
                ((ExpressListHolder)holder).btn_state.setBackgroundResource(R.drawable.btn_selector);
                ((ExpressListHolder)holder).btn_state.setText(context.getString(R.string.home_express_send));
                ((ExpressListHolder)holder).btn_state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("deliveryid",list.get(position).getDeliveryid());
                        IntentUtils.startActivityForResult((Activity) context,SendActivity.class,bundle,301);
                    }
                });
            }else {
                ((ExpressListHolder)holder).btn_state.setTextColor(context.getResources().getColor(R.color.orange));
                ((ExpressListHolder)holder).btn_state.setText(list.get(position).getState());
                ((ExpressListHolder)holder).btn_state.setBackgroundDrawable(null);
            }
        }
    }

    public class ExpressListHolder extends RecyclerView.ViewHolder{
        public TextView tv_type,tv_no;
        public Button btn_state;
        public ExpressListHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_no = (TextView) itemView.findViewById(R.id.tv_no);
            btn_state = (Button) itemView.findViewById(R.id.btn_state);
        }
    }
}
