package com.wills.help.download;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.setting.model.VersionCheck;
import com.wills.help.utils.StringUtils;
import com.wills.help.utils.ToastUtils;

import rx.functions.Action1;

/**
 * com.wills.help.download
 * Created by lizhaoyong
 * 2017/3/23.
 */

public class DownloadActivity extends BaseActivity{

    public static final String DOWNLOAD = "message_download";
    private ProgressBar progressBar;
    private TextView tv_download_size,tv_download_describe;
    private VersionCheck.VersionInfo versionInfo;
    private AlertDialog downloadDialog;
    private String fileName;
    LocalBroadcastManager bManager;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DOWNLOAD)){
                Download download = intent.getParcelableExtra("download");
                progressBar.setProgress(download.getProgress());
                if (download.getProgress() == 100){
                    downloadDialog.dismiss();
                    DownloadActivity.this.finish();
                }else {
                    tv_download_size.setText(StringUtils.getFileSize(download.getCurrentFileSize())
                            +"/"+StringUtils.getFileSize(download.getTotalFileSize()));
                    tv_download_describe.setText(download.getProgress()+"");
                }
            }
        }
    };
    @Override
    protected void initViews(Bundle savedInstanceState) {
        versionInfo = (VersionCheck.VersionInfo) getIntent().getExtras().getSerializable("version");
        fileName = "jdb.apk";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.version_update))
                .setMessage(versionInfo.getContent())
                .setCancelable(false)
                .setPositiveButton(getString(R.string.download), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (versionInfo.isRule()){
                            showDownload();
                            download();
                        }else {
                            download();
                            DownloadActivity.this.finish();
                        }
                    }
                });
        if (versionInfo.isRule()){
            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
        builder.show();
    }

    private void download() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean){
                            Intent intent = new Intent(DownloadActivity.this,DownloadService.class);
                            intent.putExtra("url",versionInfo.getUrl());
                            intent.putExtra("fileName",fileName);
                            startService(intent);
                        }else {
                            ToastUtils.toast("permission is not granted");
                        }
                    }
                });

    }

    private void showDownload(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_download,null);
        progressBar = (ProgressBar)view.findViewById(R.id.pb_download);
        tv_download_size = (TextView) view.findViewById(R.id.tv_download_size);
        tv_download_describe = (TextView) view.findViewById(R.id.tv_download_describe);
        builder.setTitle(getString(R.string.download_loading))
                .setView(view)
                .setCancelable(false);
        downloadDialog = builder.create();
        if (!isFinishing()){
            downloadDialog.show();
        }
        registerReceiver();
    }

    private void registerReceiver() {
        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DOWNLOAD);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (versionInfo.isRule()){
            bManager.unregisterReceiver(broadcastReceiver);
        }
    }
}
