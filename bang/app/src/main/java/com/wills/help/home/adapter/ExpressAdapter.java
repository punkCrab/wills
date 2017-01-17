package com.wills.help.home.adapter;

import android.app.Activity;
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
    protected void BindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExpressHolder){
            ((ExpressHolder)holder).tv_info.setText(list.get(position).getDeliveryid());
            ((ExpressHolder)holder).btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentUtils.startActivityForResult(context,SendActivity.class,301);
                }
            });
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
