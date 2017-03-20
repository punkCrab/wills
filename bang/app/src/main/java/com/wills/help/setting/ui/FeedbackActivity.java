package com.wills.help.setting.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.net.HttpMap;
import com.wills.help.setting.presenter.FeedbackPresenterImpl;
import com.wills.help.setting.view.FeedbackView;
import com.wills.help.utils.ToastUtils;

import java.util.Map;

/**
 * com.wills.help.setting.ui
 * Created by lizhaoyong
 * 2017/3/20.
 */

public class FeedbackActivity extends BaseActivity implements FeedbackView{
    EditText et_feedback;
    FeedbackPresenterImpl feedbackPresenter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_feedback);
        setBaseTitle(getString(R.string.setting_about_feedback));
        et_feedback = (EditText) findViewById(R.id.et_feedback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base,menu);
        menu.getItem(0).setTitle(getString(R.string.button_send));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_right:
                if (feedbackPresenter == null){
                    feedbackPresenter = new FeedbackPresenterImpl(this);
                }
                feedbackPresenter.feedback(getMap());
                break;
        }
        return true;
    }

    private Map<String ,String> getMap(){
        HttpMap map = new HttpMap();
        map.put("userid", App.getApp().getUser().getUserid());
        map.put("content", et_feedback.getText().toString());
        return map.getMap();
    }

    @Override
    public void setFeedback() {
        ToastUtils.toast(getString(R.string.setting_about_feedback_success));
        finish();
    }
}
