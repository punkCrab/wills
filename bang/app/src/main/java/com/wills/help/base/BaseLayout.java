package com.wills.help.base;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wills.help.R;

/**
 * com.wills.help.base
 * Created by lizhaoyong
 * 2016/11/7.
 */

public class BaseLayout extends RelativeLayout{
    private View barView,loadView,contentView;
    private Toolbar toolbar;
    private TextView tv_tip;
    private ProgressBar progressBar;
    public LinearLayout ll_clickreload,ll_tip;
    private ImageView iv_tip;

    public BaseLayout(Context context , int layoutResourceId ,boolean bar) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (bar){
            initToolbarView(layoutInflater);
        }
        contentView = layoutInflater.inflate(layoutResourceId, null);
        RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        viewParams.addRule(RelativeLayout.BELOW, R.id.toolbar);
        addView(contentView, viewParams);
        initLoadView(layoutInflater);
    }

    public BaseLayout(Context context , View view ,boolean bar) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (bar){
            initToolbarView(layoutInflater);
        }
        RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        viewParams.addRule(RelativeLayout.BELOW, R.id.toolbar);
        addView(view, viewParams);
        initLoadView(layoutInflater);
    }

    private void initToolbarView(LayoutInflater layoutInflater){
        barView = layoutInflater.inflate(R.layout.toolbar, null);
        RelativeLayout.LayoutParams barParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        barParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        addView(barView,barParams);
    }


    private void initToolbar(){
        toolbar = (Toolbar) barView.findViewById(R.id.toolbar);
    }

    private void initLoadView(LayoutInflater layoutInflater){
        loadView = layoutInflater.inflate(R.layout.process_page, null);
        RelativeLayout.LayoutParams lodaParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lodaParams.addRule(RelativeLayout.BELOW, R.id.toolbar);
        addView(loadView, lodaParams);
        ll_tip = (LinearLayout) loadView.findViewById(R.id.ll_tip);
        tv_tip = (TextView) loadView.findViewById(R.id.tv_tip);
        iv_tip = (ImageView) loadView.findViewById(R.id.iv_tip);
        ll_clickreload = (LinearLayout) loadView.findViewById(R.id.ll_clickreload);
        progressBar = (ProgressBar) loadView.findViewById(R.id.pb);
    }

    public void setBaseTitle(String title){
        initToolbar();
        toolbar.setTitle(title);
    }

    public Toolbar getToolbar(){
        if (toolbar!=null){
            return toolbar;
        }
        return (Toolbar) barView.findViewById(R.id.toolbar);
    }

    public void loadStart() {
        contentView.setVisibility(View.GONE);
        loadView.setVisibility(View.VISIBLE);
        ll_tip.setVisibility(View.GONE);
        ll_clickreload.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void loadFailure() {
        contentView.setVisibility(View.GONE);
        loadView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        ll_tip.setVisibility(View.GONE);
        ll_clickreload.setVisibility(View.VISIBLE);
    }

    public void loadSuccess(){
        contentView.setVisibility(View.VISIBLE);
        loadView.setVisibility(View.GONE);
    }

    public void loadDataEmpty(String str,int drawable){
        contentView.setVisibility(View.GONE);
        loadView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        ll_tip.setVisibility(View.VISIBLE);
        if(drawable!=0){
            iv_tip.setBackgroundResource(drawable);
        }
        tv_tip.setText(str);
        ll_clickreload.setVisibility(View.GONE);
    }

    public View getReloadView(){
        if (ll_clickreload!=null){
            return ll_clickreload;
        }
        return loadView.findViewById(R.id.ll_clickreload);
    }
}
