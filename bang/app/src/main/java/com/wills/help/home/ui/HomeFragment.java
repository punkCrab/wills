package com.wills.help.home.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.home.adapter.NewsAdapter;
import com.wills.help.home.model.News;
import com.wills.help.listener.AppBarStateChangeListener;
import com.wills.help.release.adapter.PagerAdapter;
import com.wills.help.widget.banner.AutoScrollPoster;
import com.wills.help.widget.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class HomeFragment extends BaseFragment{

    AutoScrollPoster poster;
    LinearLayout linearLayout;
    LinearLayout ll_icon;
    RecyclerView recyclerView;
    ViewPager viewPager;
    TabLayout tabLayout;
    LinearLayoutManager linearLayoutManager;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;
    NestedScrollView nestedScrollView;
    NewsAdapter newsAdapter;
    List<News> newsList = new ArrayList<>();
    Context context;

    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getAppCompatActivity().setSupportActionBar(toolbar);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        poster = (AutoScrollPoster) view.findViewById(R.id.poster);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_img);
        ll_icon = (LinearLayout) view.findViewById(R.id.ll_icon);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nsv);
        viewPager.setOffscreenPageLimit(1);
        setViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.home_seek)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.home_help)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.home_express)));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        context = getAppCompatActivity().getApplicationContext();
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED){
                    //展开状态
                    toolbar.setTitle("");
                }else if (state == State.COLLAPSED){
                    //折叠
                    toolbar.setTitle(getString(R.string.app_name));
                }else {
                    toolbar.setTitle("");
                }
            }
        });
        initBanner();
        linearLayoutManager = new LinearLayoutManager(getAppCompatActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setFocusable(false);
        newsAdapter = new NewsAdapter(context,newsList);
        newsAdapter.setFooter(false);
        recyclerView.setAdapter(newsAdapter);
        nestedScrollView.smoothScrollTo(0,20);
    }

    private void initBanner(){
        poster.needLoadAnimation(false);
        poster.setScaleType(ImageView.ScaleType.FIT_XY);
        poster.setIndicateLayout(context,poster,getBanner(),linearLayout);
        poster.startAutoScroll(5*1000);
        poster.setOnItemViewClickListener(new AutoScrollPoster.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, Object object) {
                String url = (String)object;
                if (!TextUtils.isEmpty(url)&&url.startsWith("http")) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    context.startActivity(intent);
                }
            }
        });
    }

    private List<Banner> getBanner(){
        List<Banner> list = new ArrayList<>();
        Banner banner = new Banner();
        banner.setImgUrl("http://imgsrc.baidu.com/forum/pic/item/060828381f30e924fb6171ae4c086e061c95f76c.jpg");
        list.add(banner);
        Banner banner1 = new Banner();
        banner1.setImgUrl("http://s10.sinaimg.cn/mw690/001b6pKdgy6TM1Zifr369&690");
        list.add(banner1);
        Banner banner2 = new Banner();
        banner2.setImgUrl("http://fujian.people.com.cn/NMediaFile/2014/0421/LOCAL201404210847000005010313338.jpg");
        list.add(banner2);
        return list;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            if (poster!=null){
                poster.stopScroll();
            }
        }else {
            if (poster!=null){
                poster.resumeScroll();
            }
        }
    }

    private void setViewPager(ViewPager mViewPager){
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(IconFragment.newInstance(0),getString(R.string.home_seek));
        adapter.addFragment(IconFragment.newInstance(1),getString(R.string.home_help));
        adapter.addFragment(IconFragment.newInstance(2),getString(R.string.home_express));
        mViewPager.setAdapter(adapter);
    }
}
