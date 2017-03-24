package com.wills.help.person.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.listener.AppBarStateChangeListener;
import com.wills.help.net.HttpMap;
import com.wills.help.person.model.Wallet;
import com.wills.help.person.presenter.WalletPresenterImpl;
import com.wills.help.person.view.WalletView;
import com.wills.help.setting.ui.PayPasswordActivity;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.StringUtils;
import com.wills.help.widget.RiseNumberTextView;

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
    private float amount;
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
        if (StringUtils.isNullOrEmpty(App.getApp().getUser().getPaypwd())){
            showPayPwd();
        }
        walletPresenter = new WalletPresenterImpl(this);
        walletPresenter.getMoney(getMap());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallet,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_history:
                IntentUtils.startActivity(context,BillActivity.class);
                break;
            case R.id.action_approve:
                IntentUtils.startActivity(context,ApproveActivity.class);
                break;
        }
        return true;
    }

    private Map<String ,String> getMap(){
        HttpMap map = new HttpMap();
        map.put("userid", App.getApp().getUser().getUserid());
        return map.getMap();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_withdraw:
                if (StringUtils.isNullOrEmpty(App.getApp().getUser().getPaypwd())){
                    showId();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putFloat("amount",amount);
                    IntentUtils.startActivityForResult(WalletActivity.this,WithdrawActivity.class,bundle,801);
                }
                break;
            case R.id.ll_block:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 801 &&resultCode == RESULT_OK){
            walletPresenter.getMoney(getMap());
        }
    }

    @Override
    public void setMoney(Wallet.Money money) {
        float amount = Float.parseFloat(money.getMoney());
        this.amount = amount;
        tv_amount.withNumber(amount);
        tv_amount.start();
    }

    private void showPayPwd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.wallet_warn))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.setting_set_pay_pwd), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        IntentUtils.startActivity(WalletActivity.this, PayPasswordActivity.class);
                    }
                }).show();
    }

    private void showId() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.wallet_id))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.pay_approve), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        IntentUtils.startActivity(WalletActivity.this, ApproveActivity.class);
                    }
                }).show();
    }
}
