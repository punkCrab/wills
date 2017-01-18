package com.wills.help.login.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.base.MainActivity;
import com.wills.help.login.presenter.ConfigPresenterImpl;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.SharedPreferencesUtils;
import com.wills.help.utils.StringUtils;

/**
 * Created by lizhaoyong on 2016/3/29.
 */
public class FlashActivity extends BaseActivity {
    ImageView iv_flash;
    private ConfigPresenterImpl configPresenter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_flash);
        iv_flash = (ImageView) findViewById(R.id.iv_flash);
        initEvents();
    }

    private void initEvents() {
        configPresenter = new ConfigPresenterImpl();
        configPresenter.getPoint();
        configPresenter.getOrderType();
        if (StringUtils.isNullOrEmpty((String) SharedPreferencesUtils.getInstance().get(AppConfig.SP_USER,"")))
            App.getApp().setIsLogin(false);
        else
            App.getApp().setIsLogin(true);
        MobclickAgent.openActivityDurationTrack(false);
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_flash,"alpha",0.1f,1.0f);
        animator.setDuration(2000);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                IntentUtils.startFinishActivity(context,MainActivity.class);
            }
        });
    }
}
