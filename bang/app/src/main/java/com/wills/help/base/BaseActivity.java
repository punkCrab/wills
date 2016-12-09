package com.wills.help.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.wills.help.utils.AppManager;

/**
 * com.wills.help.base
 * Created by lizhaoyong
 * 2016/11/7.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected BaseLayout baseLayout;
    protected Context context;
    private String title;//用于Umeng统计
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        AppManager.getAppManager().addActivity(this);
        initViews(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(title);
        MobclickAgent.onResume(context);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(title);
        MobclickAgent.onPause(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    protected void setBaseView(int layoutResId){
        baseLayout = new BaseLayout(this,layoutResId,true);
        setContentView(baseLayout);
    }

    protected void setBaseView(View view){
        baseLayout = new BaseLayout(this,view,true);
        setContentView(baseLayout);
    }

    protected void setNoActionBarView(int layoutResId){
        baseLayout = new BaseLayout(this,layoutResId,false);
        setContentView(baseLayout);
    }

    protected void setNoActionBarView(View view){
        baseLayout = new BaseLayout(this,view,false);
        setContentView(baseLayout);
    }

    protected void setBaseTitle(String title){
        this.title = title;
        baseLayout.setBaseTitle(title);
        setSupportActionBar(baseLayout.getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baseLayout.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClick();
            }
        });
    }

    private String getClassTitle(){
        return !TextUtils.isEmpty(title) ? title : getClass().getSimpleName();
    }

    protected void backClick(){
        this.finish();
    }

    protected Toolbar getToolbar(){
        return baseLayout.getToolbar();
    }

    protected void UI_Start(){
        baseLayout.loadStart();
    }

    protected void UI_Failure(View.OnClickListener reloadListener){
        baseLayout.loadFailure();
        baseLayout.getReloadView().setOnClickListener(reloadListener);
    }

    protected void UI_Success(){
        baseLayout.loadSuccess();
    }

    protected void UI_Empty(String content,int id){
        baseLayout.loadDataEmpty(content,id);
    }
    /** 初始化视图 **/
    protected abstract void initViews(Bundle savedInstanceState);
}
