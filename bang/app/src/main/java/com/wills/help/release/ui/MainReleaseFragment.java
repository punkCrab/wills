package com.wills.help.release.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.message.ui.MessageActivity;
import com.wills.help.release.adapter.PagerAdapter;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class MainReleaseFragment extends BaseFragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    public static MainReleaseFragment newInstance() {
        
        Bundle args = new Bundle();
        MainReleaseFragment fragment = new MainReleaseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_mainrelease,null);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle(getString(R.string.tab_release));
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager.setOffscreenPageLimit(2);
        setViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.release)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.release_progress)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.release_complete)));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbar.inflateMenu(R.menu.menu_base);
        toolbar.getMenu().getItem(0).setIcon(R.drawable.msg);
        toolbar.getMenu().getItem(0).setTitle(R.string.tab_msg);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_right:
                        IntentUtils.startActivity(getAppCompatActivity(), MessageActivity.class);
                        break;
                }
                return true;
            }
        });
    }

    private void setViewPager(ViewPager mViewPager){
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(ReleaseFragment.newInstance(),getString(R.string.release));
        adapter.addFragment(ReleaseListFragment.newInstance(0),getString(R.string.release_progress));
        adapter.addFragment(ReleaseListFragment.newInstance(1),getString(R.string.release_complete));
        mViewPager.setAdapter(adapter);
    }

}
