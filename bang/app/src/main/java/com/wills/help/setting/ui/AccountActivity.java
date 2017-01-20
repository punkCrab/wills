package com.wills.help.setting.ui;

import android.os.Bundle;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;

/**
 * com.wills.help.setting.ui
 * Created by lizhaoyong
 * 2017/1/20.
 */

public class AccountActivity extends BaseActivity{
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_account);
        setTitle(getString(R.string.setting_account));
    }
}
