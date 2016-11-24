package com.wills.help.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2016/11/15.
 */

public class IconFragment extends BaseFragment{

    private int type= 0; //0求助1帮人
    ImageView imageView;
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
        imageView = (ImageView) view.findViewById(R.id.iv);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        if (type ==0){
            imageView.setImageResource(R.drawable.example_search);
        }else {
            imageView.setImageResource(R.drawable.example_help);
        }
    }
}
