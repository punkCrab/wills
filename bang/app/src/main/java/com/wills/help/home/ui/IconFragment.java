package com.wills.help.home.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.base.MainActivity;
import com.wills.help.home.adapter.AuthAdapter;
import com.wills.help.home.model.Auth;

import java.util.ArrayList;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2016/11/15.
 */

public class IconFragment extends BaseFragment implements AuthAdapter.AuthItemClickListener {

    private int type = 0; //0求助1帮人
    RecyclerView recyclerView;
    private ArrayList<Auth> auths;
    private AuthAdapter authAdapter;
    private GridLayoutManager gridLayoutManager;

    public static IconFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        IconFragment fragment = new IconFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home_icon, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        auths = new ArrayList<>();
        if (type == 0) {
            Auth auth = new Auth();
            auth.setImgId(R.drawable.auth_express);
            auth.setTitle("取快递");
            Auth auth1 = new Auth();
            auth1.setImgId(R.drawable.auth_snacks);
            auth1.setTitle("取零食");
            Auth auth2 = new Auth();
            auth2.setImgId(R.drawable.auth_queue);
            auth2.setTitle("找排队");
            Auth auth3 = new Auth();
            auth3.setImgId(R.drawable.auth_seat);
            auth3.setTitle("占座位");
            Auth auth4 = new Auth();
            auth4.setImgId(R.drawable.auth_help_coach);
            auth4.setTitle("找辅导");
            Auth auth5 = new Auth();
            auth5.setImgId(R.drawable.auth_help_teacher);
            auth5.setTitle("找老师");
            Auth auth6 = new Auth();
            auth6.setImgId(R.drawable.auth_public);
            auth6.setTitle("公益之窗");
            Auth auth7 = new Auth();
            auth7.setImgId(R.drawable.auth_other);
            auth7.setTitle("其他");
            auths.add(auth);
            auths.add(auth1);
            auths.add(auth2);
            auths.add(auth3);
            auths.add(auth4);
            auths.add(auth5);
            auths.add(auth6);
            auths.add(auth7);
        } else {
            Auth auth = new Auth();
            auth.setImgId(R.drawable.auth_express);
            auth.setTitle("取快递");
            Auth auth1 = new Auth();
            auth1.setImgId(R.drawable.auth_snacks);
            auth1.setTitle("取零食");
            Auth auth2 = new Auth();
            auth2.setImgId(R.drawable.auth_queue);
            auth2.setTitle("帮排队");
            Auth auth3 = new Auth();
            auth3.setImgId(R.drawable.auth_seat);
            auth3.setTitle("占座位");
            Auth auth4 = new Auth();
            auth4.setImgId(R.drawable.auth_help_coach);
            auth4.setTitle("帮辅导");
            Auth auth5 = new Auth();
            auth5.setImgId(R.drawable.auth_help_teacher);
            auth5.setTitle("帮老师");
            Auth auth6 = new Auth();
            auth6.setImgId(R.drawable.auth_public);
            auth6.setTitle("公益之窗");
            Auth auth7 = new Auth();
            auth7.setImgId(R.drawable.auth_other);
            auth7.setTitle("其他");
            auths.add(auth);
            auths.add(auth1);
            auths.add(auth2);
            auths.add(auth3);
            auths.add(auth4);
            auths.add(auth5);
            auths.add(auth6);
            auths.add(auth7);
        }
        gridLayoutManager = new GridLayoutManager(getAppCompatActivity(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        authAdapter = new AuthAdapter(getAppCompatActivity(), auths);
        authAdapter.setAuthItemClickListener(this);
        recyclerView.setAdapter(authAdapter);
    }

    @Override
    public void onItemClick(int position) {
        if (type == 0) {
            ((MainActivity) getAppCompatActivity()).jumpReleaseFragment(1, position);
        } else if (type == 1) {
            ((MainActivity) getAppCompatActivity()).getBottomNavigationBar().selectTab(2);
        }
    }
}
