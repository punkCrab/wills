package com.wills.help.release.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.db.bean.PointInfo;

import java.util.List;

/**
 * com.wills.help.release.adapter
 * Created by lizhaoyong
 * 2017/3/6.
 */

public class PointAdapter extends BaseListAdapter<PointInfo>{
    private Context context;
    private List<PointInfo> list;

    public PointAdapter(Context context, List<PointInfo> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_select_item, parent, false);
        return new PointHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PointHolder){
            ((PointHolder)holder).tv_name.setText(list.get(position).getPosname());
            ((PointHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    baseItemClickListener.onItemClick(position);
                }
            });
        }
    }

    public class PointHolder extends RecyclerView.ViewHolder{

        public TextView tv_name;
        public View itemView;
        public PointHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
