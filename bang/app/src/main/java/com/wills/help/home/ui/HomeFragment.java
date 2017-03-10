package com.wills.help.home.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseFragment;
import com.wills.help.base.WebViewActivity;
import com.wills.help.home.adapter.NewsAdapter;
import com.wills.help.home.model.Banner;
import com.wills.help.home.model.News;
import com.wills.help.home.presenter.HomePresenterImpl;
import com.wills.help.home.view.HomeView;
import com.wills.help.listener.AppBarStateChangeListener;
import com.wills.help.release.adapter.PagerAdapter;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.ScreenUtils;
import com.wills.help.utils.StringUtils;
import com.wills.help.widget.MySwipeRefreshLayout;
import com.wills.help.widget.banner.AutoScrollPoster;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class HomeFragment extends BaseFragment implements HomeView ,SwipeRefreshLayout.OnRefreshListener{

    AutoScrollPoster poster;
    LinearLayout linearLayout;
    LinearLayout ll_icon;
    RecyclerView recyclerView;
    MySwipeRefreshLayout swipeRefreshLayout;
    ViewPager viewPager;
    TabLayout tabLayout;
    LinearLayoutManager linearLayoutManager;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;
    NestedScrollView nestedScrollView;
    NewsAdapter newsAdapter;
    List<News.NewsInfo> newsList;
    Context context;
    private CoordinatorLayout cl_root;
    private HomePresenterImpl homePresenter;
    private List<Banner.BannerInfo> bannerList;
    private ExpressListener expressListener;

    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        swipeRefreshLayout = (MySwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setNestedScrollingEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary, R.color.colorPrimaryLight,R.color.colorAccent);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getAppCompatActivity().setSupportActionBar(toolbar);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        if (ScreenUtils.getToolbarHeight() == 0){
            cl_root = (CoordinatorLayout) view.findViewById(R.id.cl_root);
            cl_root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int toolbarHeight = toolbar.getHeight();
                    if (toolbarHeight != 0){
                        cl_root.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        ScreenUtils.setToolbarHeight(toolbarHeight);
                    }
                }
            });
        }
        poster = (AutoScrollPoster) view.findViewById(R.id.poster);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_img);
        ll_icon = (LinearLayout) view.findViewById(R.id.ll_icon);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nsv);
        viewPager.setOffscreenPageLimit(2);
        setViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.home_express)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.home_seek)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.home_help)));
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
                    toolbar.setLogo(null);
                    swipeRefreshLayout.setEnabled(true);
                }else if (state == State.COLLAPSED){
                    //折叠
                    toolbar.setTitle("");
                    toolbar.setLogo(R.drawable.title);
                    swipeRefreshLayout.setEnabled(false);
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }else {
                    toolbar.setTitle("");
                    toolbar.setLogo(null);
                    swipeRefreshLayout.setEnabled(false);
                    if (!swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
        linearLayoutManager = new LinearLayoutManager(getAppCompatActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setFocusable(false);
        nestedScrollView.smoothScrollTo(0,20);
        homePresenter = new HomePresenterImpl(this);
        homePresenter.getBanner();
        homePresenter.getNews();
        swipeRefreshLayout.setRefreshing(true);
    }

    private void initBanner(){
        swipeRefreshLayout.setRefreshing(false);
        poster.needLoadAnimation(false);
        poster.setScaleType(ImageView.ScaleType.FIT_XY);
        poster.setIndicateLayout(context,poster,bannerList,linearLayout);
        poster.startAutoScroll(5*1000);
        poster.setOnItemViewClickListener(new AutoScrollPoster.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, Object object) {
                String url = (String)object;
                if (!StringUtils.isNullOrEmpty(url)&&url.startsWith("http")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url",url);
                    IntentUtils.startActivity(getAppCompatActivity(), WebViewActivity.class,bundle);
                }
            }
        });
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
            if (expressListener!=null){
                if (App.getApp().getIsLogin()){
                    expressListener.expressRefresh();
                }else {
                    expressListener.expressClear();
                }
            }
        }
    }

    private void setViewPager(ViewPager mViewPager){
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(ExpressFragment.newInstance(),getString(R.string.home_express));
        adapter.addFragment(IconFragment.newInstance(0),getString(R.string.home_seek));
        adapter.addFragment(IconFragment.newInstance(1),getString(R.string.home_help));
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void setBanner(List<com.wills.help.home.model.Banner.BannerInfo> banner) {
        bannerList = new ArrayList<>();
        bannerList.addAll(banner);
        initBanner();
    }

    @Override
    public void setNews(List<News.NewsInfo> news) {
        if (newsList == null){
            newsList = new ArrayList<>();
            newsAdapter = new NewsAdapter(getAppCompatActivity(),newsList);
            newsAdapter.setFooter(false);
            recyclerView.setAdapter(newsAdapter);
        }
        if (news!=null&&news.size()>0){
            newsList.addAll(news);
            newsAdapter.setList(newsList);
        }

    }

    @Override
    public void onRefresh() {
        if (homePresenter == null){
            homePresenter = new HomePresenterImpl(this);
        }
        homePresenter.getBanner();
        newsList = new ArrayList<>();
        homePresenter.getNews();
        if (expressListener!=null&& App.getApp().getIsLogin()){
            expressListener.expressRefresh();
        }
    }

    public void setExpressListener(ExpressListener expressListener) {
        this.expressListener = expressListener;
    }

    public interface ExpressListener{
        void expressRefresh();
        void expressClear();
    }

}
