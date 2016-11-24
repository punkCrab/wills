package com.wills.help.assist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wills.help.R;
import com.wills.help.assist.model.Assist;
import com.wills.help.base.BaseListAdapter;

import java.util.List;

/**
 * com.wills.help.assist.adapter
 * Created by lizhaoyong
 * 2016/11/16.
 */

public class AssistAdapter extends BaseListAdapter<Assist>{

    public AssistAdapter(Context context, List<Assist> list) {
        super(context, list);
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assist, parent, false);
        return new AssistHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AssistHolder){
            ((AssistHolder)holder).imageView.setImageResource(R.drawable.example_assist_item);
        }
    }

    public class AssistHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public ImageView imageView;
        public AssistHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
