package com.wills.help.assist.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.assist.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class AssistFragment extends BaseFragment {

    private Toolbar toolbar;
    ImageView iv;

    public static AssistFragment newInstance() {

        Bundle args = new Bundle();

        AssistFragment fragment = new AssistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_assist, null);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tab_assist));
        getAppCompatActivity().setSupportActionBar(toolbar);
        iv = (ImageView) view.findViewById(R.id.iv);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.startActivity(getAppCompatActivity(),AssistListActivity.class);
            }
        });
    }
}
