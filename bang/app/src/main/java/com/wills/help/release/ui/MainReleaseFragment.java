package com.wills.help.release.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.message.ui.MessageActivity;
import com.wills.help.release.adapter.PagerAdapter;
import com.wills.help.utils.IntentUtils;

import static android.app.Activity.RESULT_OK;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class MainReleaseFragment extends BaseFragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private int tabIndex;
    private int stateId;
    private ReleaseListFragment progressFragment,completeFragment;
    private ReleaseFragment releaseFragment;

    public static MainReleaseFragment newInstance(int tabIndex, int stateId) {

        Bundle args = new Bundle();
        args.putInt("tabIndex", tabIndex);
        args.putInt("stateId", stateId);
        MainReleaseFragment fragment = new MainReleaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_mainrelease, null);
        tabIndex = getArguments().getInt("tabIndex");
        stateId = getArguments().getInt("stateId");
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
//        toolbar.setTitle(getString(R.string.tab_release));
        toolbar.setLogo(R.drawable.title);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager.setOffscreenPageLimit(2);
        setViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.release)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.release_progress)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.release_complete)));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(tabIndex).select();
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

    private void setViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        progressFragment = ReleaseListFragment.newInstance(0);
        completeFragment = ReleaseListFragment.newInstance(1);
        releaseFragment = ReleaseFragment.newInstance(stateId);
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(releaseFragment, getString(R.string.release));
        adapter.addFragment(progressFragment, getString(R.string.release_progress));
        adapter.addFragment(completeFragment, getString(R.string.release_complete));
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            changeMsgIcon();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        changeMsgIcon();
    }


    private void changeMsgIcon(){
        int count = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        if (count > 0) {
            if (!toolbar.getMenu().getItem(0).getIcon().equals(getResources().getDrawable(R.drawable.msg_new))){
                toolbar.getMenu().getItem(0).setIcon(R.drawable.msg_new);
                toolbar.invalidate();
            }
        } else {
            if (!toolbar.getMenu().getItem(0).getIcon().equals(getResources().getDrawable(R.drawable.msg))){
                toolbar.getMenu().getItem(0).setIcon(R.drawable.msg);
                toolbar.invalidate();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 401 && resultCode == RESULT_OK){
            if(tabLayout == null){
                tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
            }
            tabLayout.getTabAt(1).select();
            if (progressFragment!=null){
                progressFragment.onRefresh();
            }
        }else if ((requestCode == 402||requestCode == 403) && resultCode == RESULT_OK){
            if (releaseFragment!=null){
                releaseFragment.onActivityResult(requestCode,resultCode,data);
            }
        }
    }
}
