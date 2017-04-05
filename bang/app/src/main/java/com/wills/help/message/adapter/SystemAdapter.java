package com.wills.help.message.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.DateUtils;
import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * com.wills.help.message.adapter
 * Created by lizhaoyong
 * 2017/4/5.
 */

public class SystemAdapter extends BaseListAdapter<EMMessage>{

    private Context context;
    private List<EMMessage> list;


    public SystemAdapter(Context context, List<EMMessage> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_system, parent, false);
        return new SystemHolder(view);
    }

    @Override
    protected void BindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SystemHolder){
            if (position == 0) {
                ((SystemHolder)holder).timestamp.setText(DateUtils.getTimestampString(new Date(list.get(position).getMsgTime())));
                ((SystemHolder)holder).timestamp.setVisibility(View.VISIBLE);
            } else {
                // show time stamp if interval with last message is > 30 seconds
                EMMessage prevMessage = list.get(position-1);
                if (prevMessage != null && DateUtils.isCloseEnough(list.get(position).getMsgTime(), prevMessage.getMsgTime())) {
                    ((SystemHolder)holder).timestamp.setVisibility(View.GONE);
                } else {
                    ((SystemHolder)holder).timestamp.setText(DateUtils.getTimestampString(new Date(list.get(position).getMsgTime())));
                    ((SystemHolder)holder).timestamp.setVisibility(View.VISIBLE);
                }
            }
            ((SystemHolder)holder).tv_time.setText(DateUtils.getTimestampString(new Date(list.get(position).getMsgTime())));
            ((SystemHolder)holder).tv_content.setText(((EMTextMessageBody)list.get(position).getBody()).getMessage());
            try {
                if (!StringUtils.isNullOrEmpty(list.get(position).getStringAttribute("notifytype"))
                &&list.get(position).getStringAttribute("notifytype").equals("1")){//发单
                    ((SystemHolder)holder).tv_title.setText(context.getString(R.string.notify_release));
                }else if (!StringUtils.isNullOrEmpty(list.get(position).getStringAttribute("notifytype"))
                &&list.get(position).getStringAttribute("notifytype").equals("2")){//接单人
                    ((SystemHolder)holder).tv_title.setText(context.getString(R.string.notify_accept));
                }
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    public class SystemHolder extends RecyclerView.ViewHolder{
        public CardView cv;
        public TextView timestamp,tv_title,tv_time,tv_content;

        public SystemHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
