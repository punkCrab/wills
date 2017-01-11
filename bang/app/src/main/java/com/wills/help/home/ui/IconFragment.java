package com.wills.help.home.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.home.adapter.AuthAdapter;
import com.wills.help.home.model.Auth;

import java.util.ArrayList;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2016/11/15.
 */

public class IconFragment extends BaseFragment implements AuthAdapter.AuthItemClickListener{

    private int type= 0; //0求助1帮人
    RecyclerView recyclerView;
    private ArrayList<Auth> auths;
    private AuthAdapter authAdapter;
    private GridLayoutManager gridLayoutManager;
    public static IconFragment newInstance(int type) {
        
        Bundle args = new Bundle();
        args.putInt("type",type);
        IconFragment fragment = new IconFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home_icon,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        auths = new ArrayList<>();
        Auth auth = new Auth();
        auth.setImgId(R.drawable.auth_express);
        auth.setTitle("取快递");
        Auth auth1 = new Auth();
        auth1.setImgId(R.drawable.auth_snacks);
        auth1.setTitle("取零食");
        auths.add(auth);
        auths.add(auth1);
        gridLayoutManager = new GridLayoutManager(getAppCompatActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        authAdapter = new AuthAdapter(getAppCompatActivity(),auths);
        authAdapter.setAuthItemClickListener(this);
        recyclerView.setAdapter(authAdapter);
        if (type ==0){

        }else {

        }
    }

    @Override
    public void onItemClick(int position) {

    }
}
