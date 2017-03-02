package com.wills.help.wxapi;


import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.ToastUtils;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setBaseView(R.layout.pay_result);
		setTitle(getString(R.string.pay_result));
		api = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode == 0){
				ToastUtils.toast(getString(R.string.pay_success));
				//成功
			}else {
				ToastUtils.toast(getString(R.string.pay_fail));
				//失败
			}
			finish();
		}
	}
}