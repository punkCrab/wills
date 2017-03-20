package com.wills.help.assist.ui;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.assist.presenter.AssistPresenterImpl;
import com.wills.help.assist.view.AssistView;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.message.EaseConstant;
import com.wills.help.message.ui.ChatActivity;
import com.wills.help.net.HttpMap;
import com.wills.help.release.model.OrderInfo;
import com.wills.help.release.model.OrderList;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.ToastUtils;

import java.util.Map;

/**
 * com.wills.help.assist.ui
 * Created by lizhaoyong
 * 2017/3/3.
 */

public class AssistInfoActivity extends BaseActivity implements View.OnClickListener , AssistView{
    ImageView iv_avatar,iv_msg;
    TextView tv_state,tv_name,tv_from,tv_send,tv_money,tv_remark;
    Button btn_submit;
    OrderInfo orderInfo;
    AssistPresenterImpl assistPresenter;
    private String from;//从哪里跳转过来 accept 接单页面过来   orderList 订单列表页面过来
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_assist_info);
        orderInfo = (OrderInfo) getIntent().getExtras().getSerializable("orderInfo");
        from = getIntent().getExtras().getString("from");
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        iv_msg = (ImageView) findViewById(R.id.iv_msg);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_from = (TextView) findViewById(R.id.tv_from);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        iv_msg.setOnClickListener(this);
        initData();
    }

    private void initData(){
        GlideUtils.getInstance().displayCircleImage(context,orderInfo.getReleaseavatar(),iv_avatar);
        tv_name.setText(orderInfo.getReleasenickname());
        Drawable drawable =null;
        if (orderInfo.getReleasesex().equals("1")){
            drawable = context.getResources().getDrawable(R.drawable.sex_girl);
        }else {
            drawable = context.getResources().getDrawable(R.drawable.sex_boy);
        }
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv_name.setCompoundDrawables(drawable,null,null,null);
        tv_state.setText(orderInfo.getOrdertypename());
        tv_from.setText(orderInfo.getSrcname()+orderInfo.getSrcdetail());
        tv_send.setText(orderInfo.getDesname()+orderInfo.getDesdetail());
        tv_money.setText(orderInfo.getMoney()+getString(R.string.yuan));
        if (from.equals("orderList")){
            setBaseTitle(getString(R.string.order_info));
            tv_remark.setText(orderInfo.getRemark());
            btn_submit.setVisibility(View.GONE);
            iv_msg.setVisibility(View.VISIBLE);
        }else if (from.equals("accept")){
            setBaseTitle(getString(R.string.accept));
            tv_remark.setText(getString(R.string.accept_remark));
            iv_msg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                showOk();
                break;
            case R.id.iv_msg:
                Bundle bundle = new Bundle();
                bundle.putString(EaseConstant.EXTRA_USER_ID,orderInfo.getReleaseusername());
                bundle.putString(EaseConstant.EXTRA_USER_AVATAR,orderInfo.getReleaseavatar());
                bundle.putString(EaseConstant.EXTRA_USER_NAME,orderInfo.getReleasenickname());
                IntentUtils.startActivity(context,ChatActivity.class,bundle);
                break;
        }
    }
    private Map<String, String> getMap() {
        HttpMap map = new HttpMap();
        map.put("acceptuserid", App.getApp().getUser().getUserid());
        map.put("orderid", orderInfo.getOrderid());
        return map.getMap();
    }
    @Override
    public void accept() {
        ToastUtils.toast(getString(R.string.accept_success));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setAssistList(OrderList orderList) {

    }

    private void showOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(orderInfo.getState())
                .setMessage(getString(R.string.accept_ok))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (assistPresenter == null){
                            assistPresenter = new AssistPresenterImpl(AssistInfoActivity.this);
                        }
                        assistPresenter.accept(getMap());
                    }
                }).show();
    }
}
