package com.wills.help.person.ui;

import android.os.Bundle;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2016/11/17.
 */

public class IdentificationActivity extends BaseActivity{
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_identification);
        setBaseTitle(getString(R.string.identification));
    }
}
