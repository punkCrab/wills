package com.wills.help.release.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class ReleaseFragment extends BaseFragment{

    public static ReleaseFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ReleaseFragment fragment = new ReleaseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_release,null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
