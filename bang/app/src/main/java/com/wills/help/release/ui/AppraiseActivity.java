package com.wills.help.release.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.release.adapter.AppraiseLabelAdapter;
import com.wills.help.release.model.Appraise;
import com.wills.help.release.presenter.AppraisePresenterImpl;
import com.wills.help.release.view.AppraiseView;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.ToastUtils;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class AppraiseActivity extends BaseActivity implements View.OnClickListener ,AppraiseView , AppraiseLabelAdapter.LabelItemOnClickListener{
    private LinearLayout ll_star;
    private RecyclerView recyclerView;
    private Button button;
    private EditText editText;
    private int star = 0;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private AppraiseLabelAdapter adapter;
    private List<Appraise.Label> labels;
    private List<String> labelIds;
    private AppraisePresenterImpl appraisePresenter;
    private String orderId;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_evaluation);
        setBaseTitle(getString(R.string.release_state_evaluation));
        orderId = getIntent().getExtras().getString("orderId");
        ll_star = (LinearLayout) findViewById(R.id.ll_star);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.btn_submit);
        editText = (EditText) findViewById(R.id.et_evaluation);
        button.setOnClickListener(this);
        initData();
    }

    private void initStar() {
        for (int i = 0; i < 5; i++) {
            ImageView img = new ImageView(context);
            img.setImageResource(R.drawable.star_s);
            img.setPadding(20, 0, 0, 0);
            img.setId(i);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    star = view.getId()+1;
                    for (int j = 0;j<=view.getId();j++){
                        ((ImageView)ll_star.getChildAt(j)).setImageResource(R.drawable.star_n);
                    }
                    for (int a =4;a>view.getId();a--){
                        ((ImageView)ll_star.getChildAt(a)).setImageResource(R.drawable.star_s);
                    }
                }
            });
            ll_star.addView(img);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                appraisePresenter.appraise(getMap());
                break;
        }
    }

    private void initData(){
        initStar();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(context,8));
        appraisePresenter = new AppraisePresenterImpl(this);
        appraisePresenter.getAppraiseLabel();
    }

    @Override
    public void setAppraiseLabel(List<Appraise.Label> labelList) {
        if (labels == null){
            labels = new ArrayList<>();
        }
        labels.addAll(labelList);
        adapter = new AppraiseLabelAdapter(context,labels);
        adapter.setLabelItemOnClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void appraise() {
        ToastUtils.toast("评价成功");
        finish();
    }

    private Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("orderid", orderId);
        map.put("releaseuserid", App.getApp().getUser().getUserid());
        map.put("appraisecontent", StringUtils.isNullOrEmpty(editText.getText().toString())?"":editText.getText().toString());
        map.put("appraiselevel", String.valueOf(star));
        map.put("appraiselabelid", getLabelIds());
        return map;
    }

    @Override
    public void onLabelItemClick(int position, boolean isSelect) {
        if (labelIds == null){
            labelIds = new ArrayList<>();
        }
        if (isSelect){
            labelIds.add(labels.get(position).getAppraiselabelid());
        }else {
            labelIds.remove(labels.get(position).getAppraiselabelid());
        }
    }

    private String getLabelIds(){
        String ids = "";
        if (labelIds!=null&&labelIds.size()>0){
            for (String s:labelIds){
                ids+=s+",";
            }
            return ids.substring(0,ids.length()-1);
        }
        return ids;
    }
}
