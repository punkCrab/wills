package com.wills.help.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * com.wills.help.base
 * Created by lizhaoyong
 * 2016/11/7.
 */

public abstract class BaseFragment extends Fragment{
    public View view;
    public AppCompatActivity activity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity= (AppCompatActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = initView(inflater);
        return view;
    }

    public View getRootView(){
        return view;
    }

    public abstract View initView(LayoutInflater inflater);

    public abstract void initData(Bundle savedInstanceState);

    public AppCompatActivity getAppCompatActivity(){
        return activity;
    }
}
