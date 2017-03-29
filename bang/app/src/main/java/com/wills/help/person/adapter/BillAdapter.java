package com.wills.help.person.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.db.bean.OrderTypeInfo;
import com.wills.help.db.manager.OrderTypeInfoHelper;
import com.wills.help.person.model.Bill;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.person.adapter
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class BillAdapter extends BaseListAdapter<Bill.BillInfo>{
    private List<Bill.BillInfo> list;
    private Context context;

    public BillAdapter(Context context, List<Bill.BillInfo> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    public BillAdapter(Context context, List<Bill.BillInfo> list, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        super(context, list, recyclerView, linearLayoutManager);
        this.context = context;
        this.list = list;
    }

    @Override
    protected RecyclerView.ViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillHolder(view);
    }

    @Override
    protected void BindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BillHolder){
            OrderTypeInfoHelper.getInstance().queryById(list.get(position).getOrdertype())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<OrderTypeInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(OrderTypeInfo orderTypeInfo) {
                            if (orderTypeInfo != null){
                                ((BillHolder)holder).tv_state.setText(orderTypeInfo.getOrdertype());
                            }else {
                                ((BillHolder)holder).tv_state.setText(list.get(position).getOrdertype());
                            }
                        }
                    });
            if (Float.parseFloat(list.get(position).getMoney())>0){
                ((BillHolder)holder).tv_amount.setText("+"+list.get(position).getMoney());
            }else {
                ((BillHolder)holder).tv_amount.setText(list.get(position).getMoney());
            }
            ((BillHolder)holder).tv_time.setText(list.get(position).getTime());
        }
    }

    public class BillHolder extends RecyclerView.ViewHolder{
        public TextView tv_state,tv_time,tv_amount;
        public BillHolder(View itemView) {
            super(itemView);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
        }
    }
}
