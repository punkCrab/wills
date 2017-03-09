package com.wills.help.assist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.db.bean.PointInfo;

import java.util.List;

/**
 * com.wills.help.assist.adapter
 * Created by lizhaoyong
 * 2017/3/9.
 */

public class PointListAdapter extends BaseAdapter {
    private Context context;

    private ViewHolder viewHolder;

    private List<PointInfo> list;

    public PointListAdapter(Context context, List<PointInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_select_item, null);
        }
        viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_name);
        viewHolder.textView.setText(list.get(position).getPosname());
        return convertView;
    }

    private class ViewHolder {
        private TextView textView;
    }
}
