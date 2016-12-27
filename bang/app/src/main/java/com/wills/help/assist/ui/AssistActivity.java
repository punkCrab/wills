package com.wills.help.assist.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.IntentUtils;

/**
 * Created by Wills on 2016/12/23.
 */

public class AssistActivity extends BaseActivity{

    private ImageView imageView;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_assist);
        setBaseTitle(getString(R.string.tab_assist));
        imageView = (ImageView) findViewById(R.id.image);
        int imgId = getIntent().getExtras().getInt("mapId",0);
        imageView.setImageResource(imgId);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.startActivity(context,AssistListActivity.class);
            }
        });
;    }
}
