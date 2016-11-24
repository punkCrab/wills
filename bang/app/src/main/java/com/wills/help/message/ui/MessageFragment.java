package com.wills.help.message.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;

/**
 * com.wills.help.message.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class MessageFragment extends BaseFragment{


    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_msg, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
