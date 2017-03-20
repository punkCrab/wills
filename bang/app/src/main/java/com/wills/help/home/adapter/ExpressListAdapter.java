package com.wills.help.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.home.model.Express;

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
    protected void BindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExpressListHolder){
            ((ExpressListHolder)holder).tv_type.setText(list.get(position).getDeliverytype());
            ((ExpressListHolder)holder).tv_no.setText(list.get(position).getDeliveryid());
            ((ExpressListHolder)holder).tv_state.setText(list.get(position).getState());
        }
    }

    public class ExpressListHolder extends RecyclerView.ViewHolder{
        public TextView tv_type,tv_no,tv_state;
        public ExpressListHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_no = (TextView) itemView.findViewById(R.id.tv_no);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
        }
    }
}
