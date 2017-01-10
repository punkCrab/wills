package com.wills.help.home.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseFragment;
import com.wills.help.home.adapter.ExpressAdapter;
import com.wills.help.home.model.Express;
import com.wills.help.home.presenter.ExpressPresenterImpl;
import com.wills.help.home.view.ExpressView;
import com.wills.help.utils.IntentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2016/12/26.
 */

public class ExpressFragment extends BaseFragment implements ExpressView , HomeFragment.ExpressListener{

    private TextView tv_count , tv_more;
    private RecyclerView recyclerView;
    private ExpressAdapter adapter ;
    private LinearLayoutManager linearLayoutManager;
    private List<Express.ExpressInfo> expressList;
    private ExpressPresenterImpl expressPresenter;

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
        ((HomeFragment)getParentFragment()).setExpressListener(this);
        expressPresenter = new ExpressPresenterImpl(this);
        expressPresenter.getExpress(getMap());
        tv_count.setText(String.format(getAppCompatActivity().getString(R.string.home_express_count),String.valueOf(5)));
        linearLayoutManager = new LinearLayoutManager(getAppCompatActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.startActivity(getAppCompatActivity(),ExpressListActivity.class);
            }
        });
    }

    @Override
    public void setExpress(List<Express.ExpressInfo> express) {
        if (expressList == null){
            expressList = new ArrayList<>();
        }
        expressList.addAll(express);
        adapter = new ExpressAdapter(getAppCompatActivity() ,expressList);
        recyclerView.setAdapter(adapter);
    }

    private Map<String ,String> getMap(){
        Map<String,String> map = new HashMap<>();
        map.put("userid", App.getApp().getUser().getUserid());
        return map;
    }

    @Override
    public void expressRefresh() {
        if (expressPresenter == null){
            expressPresenter = new ExpressPresenterImpl(this);
        }
        expressList= new ArrayList<>();
        expressPresenter.getExpress(getMap());
    }
}
