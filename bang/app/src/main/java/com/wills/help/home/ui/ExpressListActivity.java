package com.wills.help.home.ui;

import android.os.Bundle;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;

/**
 * com.wills.help.home.ui
 * Created by lizhaoyong
 * 2016/12/26.
 */

public class ExpressListActivity extends BaseActivity{
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.page_list);
        setBaseTitle(getString(R.string.express_my));
    }
}
