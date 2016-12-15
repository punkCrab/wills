package com.wills.help.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.wills.help.R;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.SharedPreferencesUtils;

/**
 * Created by lizhaoyong on 2016/3/29.
 */
public class FlashActivity extends BaseActivity {
    ImageView iv_flash;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_flash);
        iv_flash = (ImageView) findViewById(R.id.iv_flash);
        initEvents();
    }

    private void initEvents() {
        if (TextUtils.isEmpty((String) SharedPreferencesUtils.getInstance().get(AppConfig.SP_USER,"")))
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
