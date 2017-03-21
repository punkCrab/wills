package com.wills.help.assist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.assist.model.OrderNum;
import com.wills.help.utils.ScreenUtils;

import java.util.List;

/**
 * com.wills.help.assist.adapter
 * Created by lizhaoyong
 * 2016/12/16.
 */

public class MapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int[] images;
    private Context context;
    private List<OrderNum.PosNum> list;
    MapItemClickListener itemClickListener;


    public MapAdapter(Context context, int[] images, List<OrderNum.PosNum> list) {
        this.context = context;
        this.images = images;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map, parent, false);
        return new MapHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MapHolder)holder).imageView.setImageResource(images[position]);
        for (OrderNum.PosNum posNum : list) {
            if (posNum.getBlockid().equals(String.valueOf(position + 1))) {
                if (Integer.parseInt(posNum.getCount())>0){
                    ((MapHolder) holder).tv_count.setVisibility(View.VISIBLE);
                    ((MapHolder) holder).tv_count.setText(posNum.getCount());
                }else {
                    ((MapHolder) holder).tv_count.setVisibility(View.GONE);
                }
                break;
            }
        }
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
        public TextView tv_count;
        public View itemView;
        public MapHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.image);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ScreenUtils.getScreenWidth(context)/3, ScreenUtils.getContentHeight(context)/3);
            itemView.setLayoutParams(params);
        }
    }

    public void setItemClickListener(MapItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface MapItemClickListener{
        void onItemClick(int position);
    }
}
