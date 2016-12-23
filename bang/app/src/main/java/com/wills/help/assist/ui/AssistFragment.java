package com.wills.help.assist.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.wills.help.R;
import com.wills.help.assist.adapter.MapAdapter;
import com.wills.help.base.BaseFragment;
import com.wills.help.message.ui.MessageActivity;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.assist.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class AssistFragment extends BaseFragment implements MapAdapter.MapItemClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MapAdapter mapAdapter;
    private int[] images = {R.drawable.example_assist_01, R.drawable.example_assist_02,R.drawable.example_assist_03,
            R.drawable.example_assist_04, R.drawable.example_assist_05, R.drawable.example_assist_06
            , R.drawable.example_assist_07, R.drawable.example_assist_08, R.drawable.example_assist_09};

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
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getAppCompatActivity().getMenuInflater().inflate(R.menu.menu_base, menu);
        menu.getItem(0).setIcon(R.drawable.msg);
        menu.getItem(0).setTitle(R.string.tab_msg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_right:
                IntentUtils.startActivity(getAppCompatActivity(), MessageActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        gridLayoutManager = new GridLayoutManager(getAppCompatActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        mapAdapter = new MapAdapter(getAppCompatActivity(),images);
        mapAdapter.setItemClickListener(this);
        recyclerView.setAdapter(mapAdapter);
    }

    @Override
    public void onItemClick(int position) {

    }
}
