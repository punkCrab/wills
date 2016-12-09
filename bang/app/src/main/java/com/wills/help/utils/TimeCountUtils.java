package com.wills.help.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import com.wills.help.R;

/**
 * com.wills.help.utils
 * Created by lizhaoyong
 * 2016/12/8.
 */

public class TimeCountUtils extends CountDownTimer{
    private Context context;
    private Button button;

    public TimeCountUtils(Context context, long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.button = button;
    }

    @Override
    public void onTick(long l) {
        button.setClickable(false);
        button.setText(l/1000+"秒后可重新发送");//设置倒计时时间
        button.setBackgroundResource(R.color.button_gray);
        Spannable span = new SpannableString(button.getText().toString());
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
        button.setText(span);
    }

    @Override
    public void onFinish() {
        button.setText("重新获取验证码");
        button.setClickable(true);//重新获得点击
        button.setBackgroundResource(R.drawable.btn_selector);
    }
}
