package com.wills.help.message.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.message.EaseConstant;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.message.ui
 * Created by lizhaoyong
 * 2017/1/5.
 */

public class UserDetailsActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_avatar;
    private TextView tv_name;
    private Button btn_send;
    private String username;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_userdetails);
        setBaseTitle(getString(R.string.user_details));
        Bundle bundle = getIntent().getExtras();
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_name = (TextView) findViewById(R.id.tv_name);
        username = bundle.getString("name");
        tv_name.setText(username);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                Bundle bundle = new Bundle();
                bundle.putString(EaseConstant.EXTRA_USER_ID,username);
                IntentUtils.startActivity(context,ChatActivity.class,bundle);
                finish();
                break;
        }
    }
}
