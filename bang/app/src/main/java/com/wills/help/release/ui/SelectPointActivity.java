package com.wills.help.release.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.base.BaseListAdapter;
import com.wills.help.db.bean.PointInfo;
import com.wills.help.db.manager.PointInfoHelper;
import com.wills.help.release.adapter.AutoCompleteAdapter;
import com.wills.help.release.adapter.PointAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2017/3/6.
 */

public class SelectPointActivity extends BaseActivity implements BaseListAdapter.BaseItemClickListener{

    private AutoCompleteTextView atv_point;
    private RecyclerView recyclerView;
    private PointAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<PointInfo> pointInfoList;
    private AutoCompleteAdapter autoCompleteAdapter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_select);
        setBaseTitle(getString(R.string.select_point));
        atv_point = (AutoCompleteTextView) findViewById(R.id.atv_point);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        atv_point.setDropDownVerticalOffset(30);
        initData();
        atv_point.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PointInfo info = (PointInfo) adapterView.getItemAtPosition(i);
                atv_point.setText(info.getPosname());
                Intent intent = new Intent();
                intent.putExtra("point",info);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        atv_point.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0){
                    recyclerView.setVisibility(View.GONE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void initData(){
        PointInfoHelper.getInstance().queryAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PointInfo>>() {
                    @Override
                    public void onCompleted() {
                        autoCompleteAdapter = new AutoCompleteAdapter(context,pointInfoList);
                        atv_point.setAdapter(autoCompleteAdapter);
                        recyclerView.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        adapter = new PointAdapter(context,pointInfoList);
                        adapter.setFooter(false);
                        adapter.setBaseItemClickListener(SelectPointActivity.this);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PointInfo> pointInfos) {
                        if (pointInfoList == null){
                            pointInfoList = new ArrayList<PointInfo>();
                        }
                        pointInfoList.addAll(pointInfos);
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("point",pointInfoList.get(position));
        setResult(RESULT_OK,intent);
        finish();
    }
}
