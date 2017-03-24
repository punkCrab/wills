package com.wills.help.setting.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.download.DownloadActivity;
import com.wills.help.setting.model.VersionCheck;
import com.wills.help.setting.presenter.VersionCheckPresenterImpl;
import com.wills.help.setting.view.VersionCheckView;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.ToastUtils;

/**
 * com.wills.help.setting.ui
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener,VersionCheckView{

    private TextView tv_feedback,tv_update;
    private VersionCheckPresenterImpl versionCheckPresenter;

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
                if (versionCheckPresenter == null){
                    versionCheckPresenter = new VersionCheckPresenterImpl(AboutActivity.this);
                }
                versionCheckPresenter.versionCheck();
                break;
        }
    }

    @Override
    public void setVersionCheck(VersionCheck.VersionInfo versionInfo) {
        if (Integer.parseInt(versionInfo.getVersion())>Integer.parseInt(AppConfig.VERSION_ID)){
            Bundle bundle = new Bundle();
            bundle.putSerializable("version",versionInfo);
            IntentUtils.startActivity(context, DownloadActivity.class,bundle);
        }else {
            ToastUtils.toast(getString(R.string.version_no));
        }
    }
}
