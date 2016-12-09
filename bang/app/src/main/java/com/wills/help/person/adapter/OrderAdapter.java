package com.wills.help.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.person.model.Order;

import java.util.List;

/**
 * com.wills.help.person.adapter
 * Created by lizhaoyong
 * 2016/12/5.
 */

public class OrderAdapter extends BaseListAdapter<Order>{

    public OrderAdapter(Context context, List<Order> list) {
        super(context, list);
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assist, parent, false);
        return new OrderHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderHolder){
            ((OrderHolder)holder).imageView.setImageResource(R.drawable.example_assist_item);
        }
    }

    public class OrderHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public ImageView imageView;
        public OrderHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
