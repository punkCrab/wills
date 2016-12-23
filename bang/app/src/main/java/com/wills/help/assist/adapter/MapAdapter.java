package com.wills.help.assist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wills.help.R;
import com.wills.help.utils.ScreenUtils;

/**
 * com.wills.help.assist.adapter
 * Created by lizhaoyong
 * 2016/12/16.
 */

public class MapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int[] images;
    private Context context;
    MapItemClickListener itemClickListener;


    public MapAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map, parent, false);
        return new MapHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MapHolder)holder).imageView.setImageResource(images[position]);
        ((MapHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MapHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public View itemView;
        public MapHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.image);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ScreenUtils.getScreenWidth(context)/3, ScreenUtils.getContentHeight()/3);
            imageView.setLayoutParams(params);
        }
    }

    public void setItemClickListener(MapItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface MapItemClickListener{
        void onItemClick(int position);
    }
}
