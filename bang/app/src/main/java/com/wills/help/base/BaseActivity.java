package com.wills.help.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.umeng.analytics.MobclickAgent;
import com.wills.help.message.controller.EaseUI;
import com.wills.help.utils.AppManager;
import com.wills.help.utils.StringUtils;

/**
 * com.wills.help.base
 * Created by lizhaoyong
 * 2016/11/7.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected BaseLayout baseLayout;
    protected Context context;
    private String title;//用于Umeng统计
    private InputMethodManager inputManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        AppManager.getAppManager().addActivity(this);
        if(!isTaskRoot()){
            Intent intent = getIntent();
            String action = intent.getAction();
            if(intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)){
                finish();
                return;
            }
        }
        inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        initViews(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClassTitle());
        MobclickAgent.onResume(context);
        if (getClass().getSimpleName().equals("MessageActivity")
                ||getClass().getSimpleName().equals("ChatActivity")){
            EaseUI.getInstance().getNotifier().reset();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClassTitle());
        MobclickAgent.onPause(context);
    }

    public void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        hideKeyboard();
        AppManager.getAppManager().removeActivity(this);
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
        return !StringUtils.isNullOrEmpty(title) ? title : getClass().getSimpleName();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            backClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
