package com.wills.help.person.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.listener.AppBarStateChangeListener;
import com.wills.help.person.model.Wallet;
import com.wills.help.person.presenter.WalletPresenterImpl;
import com.wills.help.person.view.WalletView;
import com.wills.help.widget.RiseNumberTextView;

import java.util.HashMap;
import java.util.Map;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2016/11/17.
 */

public class WalletActivity extends BaseActivity implements View.OnClickListener ,WalletView{
    CollapsingToolbarLayout collapsingToolbar;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    LinearLayout ll_withdraw,ll_block;
    RiseNumberTextView tv_amount;
    TextView tv_block;
    private WalletPresenterImpl walletPresenter;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setNoActionBarView(R.layout.activity_wallet);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_withdraw = (LinearLayout) findViewById(R.id.ll_withdraw);
        ll_block = (LinearLayout) findViewById(R.id.ll_block);
        tv_amount = (RiseNumberTextView) findViewById(R.id.tv_amount);
        tv_block = (TextView) findViewById(R.id.tv_block);
        ll_withdraw.setOnClickListener(this);
        ll_block.setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED){
                    //展开状态
                    toolbar.setTitle("");
                }else if (state == State.COLLAPSED){
                    //折叠
                    toolbar.setTitle(getString(R.string.wallet));
                }else {
                    toolbar.setTitle("");
                }
            }
        });
        walletPresenter = new WalletPresenterImpl(this);
        walletPresenter.getMoney(getMap());
    }

    private Map<String ,String> getMap(){
        Map<String , String> map = new HashMap<>();
        map.put("userid", App.getApp().getUser().getUserid());
        return map;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_withdraw:

                break;
            case R.id.ll_block:

                break;
        }
    }

    @Override
    public void setMoney(Wallet.Money money) {
        float amount = Float.parseFloat(money.getMoney());
        tv_amount.withNumber(amount);
        tv_amount.start();
    }
}
