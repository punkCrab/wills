package com.wills.help.login.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.IntentUtils;

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
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_flash,"alpha",0.1f,1.0f);
        animator.setDuration(2000);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                IntentUtils.startFinishActivity(context,LoginActivity.class);
            }
        });
    }
}
