package com.wills.help.setting.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.setting.ui
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_feedback,tv_update;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_about);
        setBaseTitle(getString(R.string.setting_about));
        tv_feedback = (TextView) findViewById(R.id.tv_feedback);
        tv_update = (TextView) findViewById(R.id.tv_update);
        tv_feedback.setOnClickListener(this);
        tv_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_feedback:
                IntentUtils.startActivity(context,FeedbackActivity.class);
                break;
            case R.id.tv_update:
                break;
        }
    }
}
