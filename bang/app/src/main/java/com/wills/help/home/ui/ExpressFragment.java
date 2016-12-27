package com.wills.help.home.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.home.adapter.ExpressAdapter;
import com.wills.help.home.model.Express;
import com.wills.help.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2016/12/26.
 */

public class ExpressFragment extends BaseFragment{

    private TextView tv_count , tv_more;
    private RecyclerView recyclerView;
    private ExpressAdapter adapter ;
    private LinearLayoutManager linearLayoutManager;
    private List<Express> expressList;

    public static ExpressFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ExpressFragment fragment = new ExpressFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home_express,null);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_more = (TextView) view.findViewById(R.id.tv_more);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_count.setText(String.format(getAppCompatActivity().getString(R.string.home_express_count),String.valueOf(5)));
        linearLayoutManager = new LinearLayoutManager(getAppCompatActivity());
        adapter = new ExpressAdapter(getAppCompatActivity() ,getExpress());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.startActivity(getAppCompatActivity(),ExpressListActivity.class);
            }
        });
    }

    public List<Express> getExpress(){
        Express express1 = new Express();
        express1.setExpressName("申通快递");
        express1.setExpressNo("1523214526");
        Express express2 = new Express();
        express2.setExpressName("中通快递");
        express2.setExpressNo("8523214522");
        Express express3 = new Express();
        express3.setExpressName("圆通快递");
        express3.setExpressNo("2523214533");
        List<Express> list = new ArrayList<>();
        list.add(express1);
        list.add(express2);
        list.add(express3);
        return list;
    }
}
