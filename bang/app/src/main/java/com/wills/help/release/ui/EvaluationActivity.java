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
import com.wills.help.base.BaseActivity;
import com.wills.help.release.adapter.EvaluationLabelAdapter;
import com.wills.help.release.model.Evaluation;
import com.wills.help.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class EvaluationActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_star;
    private RecyclerView recyclerView;
    private Button button;
    private EditText editText;
    private int star = 0;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private EvaluationLabelAdapter adapter;
    private List<Evaluation> evaluationList;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_evaluation);
        setBaseTitle(getString(R.string.release_state_evaluation));
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

    }

    private void initData(){
        initStar();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(context,8));
        evaluationList = getEvaluationList();
        adapter = new EvaluationLabelAdapter(context,evaluationList);
        recyclerView.setAdapter(adapter);
    }

    private List<Evaluation> getEvaluationList(){
        List<Evaluation> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            Evaluation evaluation = new Evaluation();
            evaluation.setId(i);
            evaluation.setName("可爱"+(i*i));
            list.add(evaluation);
        }
        return list;
    }
}
