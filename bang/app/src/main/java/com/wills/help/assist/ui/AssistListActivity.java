package com.wills.help.assist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wills.help.R;
import com.wills.help.assist.adapter.AssistAdapter;
import com.wills.help.assist.adapter.OrderTypeListAdapter;
import com.wills.help.assist.adapter.PointListAdapter;
import com.wills.help.assist.presenter.AssistPresenterImpl;
import com.wills.help.assist.view.AssistView;
import com.wills.help.base.BaseActivity;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.db.bean.OrderTypeInfo;
import com.wills.help.db.bean.PointInfo;
import com.wills.help.db.manager.OrderTypeInfoHelper;
import com.wills.help.db.manager.PointInfoHelper;
import com.wills.help.listener.BaseListLoadMoreListener;
import com.wills.help.net.HttpMap;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.release.model.OrderList;
import com.wills.help.utils.IntentUtils;
import com.wills.help.widget.DropDownMenu;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.assist.ui
 * Created by lizhaoyong
 * 2016/11/16.
 */

public class AssistListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseListLoadMoreListener.LoadMoreListener, AssistView, BaseListAdapter.BaseItemClickListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    AssistAdapter assistAdapter;
    LinearLayoutManager linearLayoutManager;
    List<OrderInfo> assistList = new ArrayList<>();
    private int page = 1;
    private int count = 0;
    private AssistPresenterImpl assistPresenter;
    private int srcId;

    private String headers[] = {"求助内容", "求助定位", "送达地址"};
    private ListView listView1, listView2, listView3;
    private List<View> viewList = new ArrayList<>();
    private OrderTypeListAdapter adapter1;
    private PointListAdapter adapter2, adapter3;
    private DropDownMenu dropDownMenu;
    private List<PointInfo> pointInfoList2, pointInfoList3;
    private List<OrderTypeInfo> orderTypeInfoList;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_assist_list);
        setBaseTitle(getString(R.string.tab_assist));
        srcId = getIntent().getExtras().getInt("srcid");
        dropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
        initData();
    }

    private void initData() {
        OrderTypeInfoHelper.getInstance().queryAll()
                .doOnNext(new Action1<List<OrderTypeInfo>>() {
                    @Override
                    public void call(List<OrderTypeInfo> orderTypeInfos) {
                        orderTypeInfoList = new ArrayList<OrderTypeInfo>();
                        OrderTypeInfo orderTypeInfo = new OrderTypeInfo();
                        orderTypeInfo.setTypeid("0");
                        orderTypeInfo.setOrdertype(getString(R.string.person_all_order));
                        orderTypeInfoList.add(orderTypeInfo);
                        orderTypeInfoList.addAll(orderTypeInfos);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<OrderTypeInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<OrderTypeInfo> orderTypeInfos) {
                        PointInfoHelper.getInstance().queryAll()
                                .doOnNext(new Action1<List<PointInfo>>() {
                                    @Override
                                    public void call(List<PointInfo> pointInfos) {
                                        pointInfoList3 = new ArrayList<PointInfo>();
                                        PointInfo pointInfo = new PointInfo();
                                        pointInfo.setBlockid("0");
                                        pointInfo.setPosid("0");
                                        pointInfo.setPosname(getString(R.string.person_all_order));
                                        pointInfoList3.add(pointInfo);
                                        pointInfoList3.addAll(pointInfos);
                                    }
                                }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<List<PointInfo>>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(List<PointInfo> pointInfos) {
                                        PointInfoHelper.getInstance().queryByUserBlockId("1")
                                                .doOnNext(new Action1<List<PointInfo>>() {
                                                    @Override
                                                    public void call(List<PointInfo> pointInfos) {
                                                        pointInfoList2 = new ArrayList<PointInfo>();
                                                        PointInfo pointInfo = new PointInfo();
                                                        pointInfo.setBlockid("0");
                                                        pointInfo.setPosid("0");
                                                        pointInfo.setPosname(getString(R.string.person_all_order));
                                                        pointInfoList2.add(pointInfo);
                                                        pointInfoList2.addAll(pointInfos);
                                                    }
                                                }).subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Subscriber<List<PointInfo>>() {
                                                    @Override
                                                    public void onCompleted() {
                                                        setData();
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {

                                                    }

                                                    @Override
                                                    public void onNext(List<PointInfo> pointInfos) {

                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private void setData(){
        listView1 = new ListView(context);
        listView1.setDividerHeight(0);
        adapter1 = new OrderTypeListAdapter(context, orderTypeInfoList);
        listView1.setAdapter(adapter1);
        listView2 = new ListView(context);
        listView2.setDividerHeight(0);
        adapter2 = new PointListAdapter(context, pointInfoList2);
        listView2.setAdapter(adapter2);
        listView3 = new ListView(context);
        listView3.setDividerHeight(0);
        adapter3 = new PointListAdapter(context, pointInfoList3);
        listView3.setAdapter(adapter3);
        viewList.add(listView1);
        viewList.add(listView2);
        viewList.add(listView3);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownMenu.setTabText(orderTypeInfoList.get(i).getOrdertype());
                dropDownMenu.closeMenu();
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownMenu.setTabText(pointInfoList2.get(i).getPosname());
                dropDownMenu.closeMenu();
            }
        });
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownMenu.setTabText(pointInfoList3.get(i).getPosname());
                dropDownMenu.closeMenu();
            }
        });

        View view = LayoutInflater.from(context).inflate(R.layout.page_list, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorPrimaryLight, R.color.colorAccent);

        dropDownMenu.setDropDownMenu(Arrays.asList(headers), viewList, view);

        assistPresenter = new AssistPresenterImpl(this);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(context, 5));
        assistAdapter = new AssistAdapter(context, assistList);
        assistAdapter.setBaseItemClickListener(this);
        recyclerView.setAdapter(assistAdapter);
        BaseListLoadMoreListener listLoadMore = new BaseListLoadMoreListener(linearLayoutManager, assistAdapter);
        recyclerView.addOnScrollListener(listLoadMore);
        listLoadMore.setLoadMoreListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (assistList != null) {
            assistList.clear();
            page = 1;
        }
        assistPresenter.getAssistList(getMap(0, 0));
    }

    /**
     * @param type     0获取列表 1接单
     * @param position
     * @return
     */
    private Map<String, String> getMap(int type, int position) {
        HttpMap map = new HttpMap();
        map.put("srcid", srcId + "");
        map.put("page", page + "");
        return map.getMap();
    }

    @Override
    public void loadMore() {
        if (count > assistList.size()) {
            assistPresenter.getAssistList(getMap(0, 0));
        } else {
            assistAdapter.setLoadMore(assistAdapter.EMPTY);
        }

    }

    @Override
    public void accept() {

    }

    @Override
    public void setAssistList(OrderList orderList) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        count = orderList.getCount();
        if (assistList == null) {
            assistList = new ArrayList<>();
        }
        if (orderList.getData().size() > 0) {
            assistAdapter.setLoadMore(assistAdapter.SUCCESS);
            assistList.addAll(orderList.getData());
            assistAdapter.setList(assistList);
            page++;
        } else {
            assistAdapter.setEmpty();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 501 && resultCode == RESULT_OK) {
            onRefresh();
        }
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderInfo", assistList.get(position));
        bundle.putString("from", "accept");
        IntentUtils.startActivityForResult(AssistListActivity.this, AssistInfoActivity.class, bundle, 501);
    }

}
