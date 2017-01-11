package com.wills.help.release.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.release.model.Evaluation;

import java.util.List;

/**
 * com.wills.help.release.adapter
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class EvaluationLabelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Evaluation> list;

    public EvaluationLabelAdapter(Context context, List<Evaluation> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation_label, parent, false);
        return new EvaluationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Evaluation evaluation = list.get(position);
        ((EvaluationHolder)holder).textView.setText(evaluation.getName());
        ((EvaluationHolder)holder).textView.setBackgroundResource(R.drawable.label_false);
        ((EvaluationHolder)holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundResource(R.drawable.label_true);
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
}
