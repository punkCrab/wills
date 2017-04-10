package com.wills.help.assist.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.db.bean.OrderTypeInfo;
import com.wills.help.db.bean.PointInfo;
import com.wills.help.db.manager.OrderTypeInfoHelper;
import com.wills.help.db.manager.PointInfoHelper;
import com.wills.help.net.HttpMap;
import com.wills.help.person.ui.IdentificationActivity;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.release.model.OrderList;
import com.wills.help.utils.IntentUtils;
import com.wills.help.widget.DropDownMenu;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public class AssistListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseListAdapter.LoadMoreListener, AssistView, BaseListAdapter.BaseItemClickListener ,AssistAdapter.ButtonClickListener{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    AssistAdapter assistAdapter;
    LinearLayoutManager linearLayoutManager;
    List<OrderInfo> assistList = new ArrayList<>();
    private int page = 1;
    private int count = 0;
    private AssistPresenterImpl assistPresenter;
    private String srcId,blockId;
    private String desId="0",typeorder="0";

//    private String headers[] = {"求助内容", "求助定位", "送达地址"};
    private String headers[] = {"求助内容", "送达地址"};
    private ListView listView1, listView3;
    private List<View> viewList = new ArrayList<>();
    private OrderTypeListAdapter adapter1;
//    private PointListAdapter adapter2;
    private PointListAdapter adapter3;
    private DropDownMenu dropDownMenu;
    private List<PointInfo> pointInfoList2, pointInfoList3;
    private List<OrderTypeInfo> orderTypeInfoList;
    private OrderInfo orderInfo;
    private boolean isFirst = true;//第一次进来初始化送法地址，只会不请求
    private View view;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_assist_list);
        setBaseTitle(getString(R.string.tab_assist));
        srcId = getIntent().getExtras().getString("srcid");
        blockId = getIntent().getExtras().getString("blockid");
        dropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
        setData();
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
                        pointInfoList3 = new ArrayList<PointInfo>();
                        PointInfo pointInfo = new PointInfo();
                        pointInfo.setBlockid("0");
                        pointInfo.setPosid("0");
                        pointInfo.setPosname(getString(R.string.person_all_order));
                        pointInfoList3.add(pointInfo);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<OrderTypeInfo>>() {
                    @Override
                    public void onCompleted() {
                        for (OrderInfo info : assistList){
                            for (PointInfo pointInfo : pointInfoList3){
                                if (!pointInfo.getPosid().equals(info.getDesid())){
                                    pointInfoList3.add(PointInfoHelper.getInstance().queryById(info.getDesid()));
                                    break;
                                }
                            }
                        }
                        setDropDownMenu();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<OrderTypeInfo> orderTypeInfos) {

                    }
                });
    }

    private void setDropDownMenu(){
        listView1 = new ListView(context);
        listView1.setDividerHeight(0);
        adapter1 = new OrderTypeListAdapter(context, orderTypeInfoList);
        listView1.setAdapter(adapter1);
//        listView2 = new ListView(context);
//        listView2.setDividerHeight(0);
//        adapter2 = new PointListAdapter(context, pointInfoList2);
//        listView2.setAdapter(adapter2);
        listView3 = new ListView(context);
        listView3.setDividerHeight(0);
        adapter3 = new PointListAdapter(context, pointInfoList3);
        listView3.setAdapter(adapter3);
        viewList.add(listView1);
//        viewList.add(listView2);
        viewList.add(listView3);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownMenu.setTabText(orderTypeInfoList.get(i).getOrdertype());
                typeorder = orderTypeInfoList.get(i).getTypeid();
                dropDownMenu.closeMenu();
                onRefresh();
            }
        });
//        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                dropDownMenu.setTabText(pointInfoList2.get(i).getPosname());
//                srcId = pointInfoList2.get(i).getPosid();
//                dropDownMenu.closeMenu();
//                onRefresh();
//            }
//        });
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownMenu.setTabText(pointInfoList3.get(i).getPosname());
                desId = pointInfoList3.get(i).getPosid();
                dropDownMenu.closeMenu();
                onRefresh();
            }
        });

        dropDownMenu.setDropDownMenu(Arrays.asList(headers), viewList, view);
    }

    private void setData(){
        view = LayoutInflater.from(context).inflate(R.layout.page_list, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorPrimaryLight, R.color.colorAccent);
        assistPresenter = new AssistPresenterImpl(this);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(context, 5));
        assistAdapter = new AssistAdapter(context, assistList, recyclerView,linearLayoutManager);
        assistAdapter.setButtonClickListener(this);
        assistAdapter.setBaseItemClickListener(this);
        recyclerView.setAdapter(assistAdapter);
        assistAdapter.setLoadMoreListener(this);
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
        assistPresenter.getAssistList(getMap());
    }

    /**
     * @return
     */
    private Map<String, String> getMap() {
        Map<String,String> map = new HashMap<>();
        map.put("blockid", blockId);
        map.put("srcid", srcId);
        if (!desId.equals("0")){
            map.put("desid", desId);
        }
        if (typeorder.equals("0")){
            map.put("typeorder", typeorder);
        }
        map.put("page", page + "");
        return map;
    }

    @Override
    public void loadMore() {
        if (count > assistList.size()) {
            assistPresenter.getAssistList(getMap());
        } else {
            assistAdapter.setLoadMore(assistAdapter.EMPTY);
        }

    }

    @Override
    public void accept() {
        showSuccess();
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

        if (isFirst){
            initData();
            isFirst = false;
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

    @Override
    public void buttonClick(int state, OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        if (state ==3){
            if (App.getApp().getUser().getUsertype().equals("1")){
                showOk();
            }else {
                showId();
            }
        }
    }

    private void showId(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.accept_id))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.approve), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        IntentUtils.startActivity(context,IdentificationActivity.class);
                    }
                }).show();
    }

    private void showOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.accept_ok))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.accept_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (assistPresenter == null){
                            assistPresenter = new AssistPresenterImpl(AssistListActivity.this);
                        }
                        assistPresenter.accept(getAcceptMap(orderInfo));
                    }
                }).show();
    }

    private void showSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您已成功接单从" + orderInfo.getSrcname() + orderInfo.getSrcdetail() + "送往"
                + orderInfo.getDesname() + orderInfo.getDesdetail() + "的" + orderInfo.getOrdertypename() + "请求，请您尽快动身。")
                .setPositiveButton(getString(R.string.accept_success), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onRefresh();
                    }
                }).show();
    }

    private Map<String, String> getAcceptMap(OrderInfo orderInfo) {
        HttpMap map = new HttpMap();
        map.put("acceptuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderInfo.getOrderid());
        return map.getMap();
    }
}
