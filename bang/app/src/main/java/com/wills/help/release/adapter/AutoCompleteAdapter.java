package com.wills.help.release.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.db.bean.PointInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.release.adapter
 * Created by lizhaoyong
 * 2017/3/6.
 */

public class AutoCompleteAdapter extends BaseAdapter implements Filterable{

    private Context context;
    private List<PointInfo> allList;
    private List<PointInfo> newList;
    private final Object mLock = new Object();
    private ListFilter filter;

    public AutoCompleteAdapter(Context context, List<PointInfo> allList) {
        this.context = context;
        this.allList = allList;
    }

    @Override
    public int getCount() {
        return newList.size();
    }

    @Override
    public Object getItem(int i) {
        return newList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_select_item,null);
            holder.textView = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(newList.get(i).getPosname());
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new ListFilter();
        }
        return filter;
    }


    public class ViewHolder{
        TextView textView;
    }

    public class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence ==null || charSequence.length() == 0){
                synchronized (mLock){
                    ArrayList<PointInfo> list = new ArrayList<PointInfo>(allList);
                    results.values = list;
                    results.count = list.size();
                    return results;
                }
            }else {
                String charSequenceString = charSequence.toString().toLowerCase();
                final int count = allList.size();
                final List<PointInfo> newList = new ArrayList<>();
                for (int i = 0;i<count;i++){
                    final String posId = allList.get(i).getPosid();
                    final String blockId = allList.get(i).getBlockid();
                    final String posName = allList.get(i).getPosname();
                    final String nameText = posName.toLowerCase();
                    if (nameText.contains(charSequenceString)){
                        newList.add(allList.get(i));
                    }
                }
                results.values = newList;
                results.count = newList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            newList = (List<PointInfo>) filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();
            }else {
                notifyDataSetInvalidated();
            }
        }
    }
}
