package com.wills.help.download;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;

import com.wills.help.R;
import com.wills.help.utils.StringUtils;

import java.io.File;

import rx.Subscriber;

/**
 * com.wills.help.download
 * Created by lizhaoyong
 * 2017/3/23.
 */

public class DownloadService extends IntentService{

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    private String url;
    private String fileName;
    private File outputFile;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra("url");
        fileName = intent.getStringExtra("fileName");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        download();
    }

    private void download(){
        outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName);
        if (outputFile.exists()){
            installApk(outputFile);
        }else {

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(this.getString(R.string.download))
                    .setContentText(this.getString(R.string.download_loading))
                    .setAutoCancel(true);
            notificationManager.notify(0,notificationBuilder.build());

            DownloadProgressListener listener = new DownloadProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    Download download = new Download();
                    download.setTotalFileSize(contentLength);
                    download.setCurrentFileSize(bytesRead);
                    int progress = (int) ((bytesRead * 100) / contentLength);
                    download.setProgress(progress);
                    sendNotification(download);
                }
            };
            DownloadManager.getInstance(listener).download(url, outputFile, new Subscriber() {
                @Override
                public void onCompleted() {
                    downloadCompleted();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    downloadCompleted();
                }

                @Override
                public void onNext(Object o) {

                }
            });
        }
    }

    private void downloadCompleted(){
        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText(this.getString(R.string.download_success));
        notificationManager.notify(0, notificationBuilder.build());

        installApk(outputFile);
    }

    private void sendNotification(Download download) {
        sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(
                StringUtils.getFileSize(download.getCurrentFileSize()) + "/" +
                        StringUtils.getFileSize(download.getTotalFileSize()));
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download) {
        Intent intent = new Intent(DownloadActivity.DOWNLOAD);
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

    private void installApk(File file) {
        if(file.exists()) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT>=24){
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(this, "com.wills.help.fileprovider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            }else {
                String type = "application/vnd.android.package-archive";
                intent.setDataAndType(Uri.fromFile(file), type);
            }
            startActivity(intent);
        }
    }
}
