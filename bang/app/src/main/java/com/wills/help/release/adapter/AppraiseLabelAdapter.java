package com.wills.help.release.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.release.model.Appraise;

import java.util.List;

/**
 * com.wills.help.release.adapter
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class AppraiseLabelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Appraise.Label> list;
    private LabelItemOnClickListener labelItemOnClickListener;
    public AppraiseLabelAdapter(Context context, List<Appraise.Label> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation_label, parent, false);
        return new EvaluationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Appraise.Label label = list.get(position);
        ((EvaluationHolder)holder).textView.setText(label.getAppraiselabel());
        ((EvaluationHolder)holder).textView.setBackgroundResource(R.drawable.label_false);
        ((EvaluationHolder)holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (label.getSelect() == 0){
                    view.setBackgroundResource(R.drawable.label_true);
                    label.setSelect(1);
                    labelItemOnClickListener.onLabelItemClick(position,true);
                }else {
                    view.setBackgroundResource(R.drawable.label_false);
                    label.setSelect(0);
                    labelItemOnClickListener.onLabelItemClick(position,false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EvaluationHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public EvaluationHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_label);
        }
    }

    public void setLabelItemOnClickListener(LabelItemOnClickListener labelItemOnClickListener) {
        this.labelItemOnClickListener = labelItemOnClickListener;
    }

    public interface LabelItemOnClickListener{
        void onLabelItemClick(int position , boolean isSelect);
    }
}
