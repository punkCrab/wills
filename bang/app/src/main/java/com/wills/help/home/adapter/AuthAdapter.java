package com.wills.help.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.home.model.Auth;

import java.util.ArrayList;

/**
 * Created by Wills on 2016/12/23.
 */

public class AuthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Auth> list;
    private Context context;
    private AuthItemClickListener authItemClickListener;

    public AuthAdapter(Context context, ArrayList<Auth> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auth, parent, false);
        return new AuthHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((AuthHolder)holder).imageView.setImageResource(list.get(position).getImgId());
        ((AuthHolder)holder).textView.setText(list.get(position).getTitle());
        if (position == 0){
            ((AuthHolder)holder).view.setBackgroundResource(R.drawable.item_selector);
            ((AuthHolder)holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    authItemClickListener.onItemClick(position);
                }
            });
        }else {
            ((AuthHolder)holder).view.setBackgroundResource(R.color.colorDividerLight);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AuthHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public View view;

        public AuthHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.iv_auth);
            textView = (TextView) itemView.findViewById(R.id.tv_auth);

        }
    }

    public void setAuthItemClickListener(AuthItemClickListener itemClickListener) {
        this.authItemClickListener = itemClickListener;
    }

    public interface AuthItemClickListener{
        void onItemClick(int position);
    }
}
