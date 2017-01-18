package com.wills.help.message.ui;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.util.FileUtils;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EaseShowNormalFileActivity extends BaseActivity {
	private ProgressBar progressBar;
	private File file;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.ease_activity_show_file);
        setBaseTitle(getString(R.string.attach_file));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final EMFileMessageBody messageBody = getIntent().getParcelableExtra("msgbody");
        file = new File(messageBody.getLocalUrl());
        //set head map
        final Map<String, String> maps = new HashMap<String, String>();
        if (!StringUtils.isNullOrEmpty(messageBody.getSecret())) {
            maps.put("share-secret", messageBody.getSecret());
        }

        //download file
        EMClient.getInstance().chatManager().downloadFile(messageBody.getRemoteUrl(), messageBody.getLocalUrl(), maps,
                new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                FileUtils.openFile(file, EaseShowNormalFileActivity.this);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onProgress(final int progress,String status) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progress);
                            }
                        });
                    }

                    @Override
                    public void onError(int error, final String msg) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(file != null && file.exists()&&file.isFile())
                                    file.delete();
                                String str4 = getResources().getString(R.string.Failed_to_download_file);
                                Toast.makeText(EaseShowNormalFileActivity.this, str4+msg, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
    }
}
