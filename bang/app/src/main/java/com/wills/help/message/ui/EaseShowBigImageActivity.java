/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wills.help.message.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.message.model.EaseImageCache;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.ScreenUtils;

import java.io.File;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * download and show original image
 * 
 */
public class EaseShowBigImageActivity extends BaseActivity implements PhotoViewAttacher.OnPhotoTapListener{
	private static final String TAG = "ShowBigImage"; 
	private ProgressDialog pd;
	private PhotoView image;
	private int default_res = R.drawable.default_pic;
	private String localFilePath;
	private Bitmap bitmap;
	private boolean isDownloaded;
	private Uri uri;
	private boolean full = false;
	Toolbar toolbar;

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setNoActionBarView(R.layout.ease_activity_show_big_image);
		image = (PhotoView) findViewById(R.id.image);
		default_res = getIntent().getIntExtra("default_image", R.drawable.ease_default_avatar);
		uri = getIntent().getParcelableExtra("uri");
		localFilePath = getIntent().getExtras().getString("localUrl");
		String msgId = getIntent().getExtras().getString("messageId");
		image.setOnPhotoTapListener(this);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		viewParams.setMargins(0, ScreenUtils.getStatusHeight(context),0,0);
		toolbar.setLayoutParams(viewParams);
		toolbar.setTitle(getString(R.string.attach_picture));
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		if (Build.VERSION.SDK_INT >= 21) {
			View decorView = getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
			getWindow().setNavigationBarColor(Color.TRANSPARENT);
		}
		EMLog.d(TAG, "show big msgId:" + msgId );

		//show the image if it exist in local path
		if (uri != null && new File(uri.getPath()).exists()) {
			EMLog.d(TAG, "showbigimage file exists. directly show it");
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			bitmap = EaseImageCache.getInstance().get(uri.getPath());
			if (bitmap == null) {
				GlideUtils.getInstance().displayImage(this, uri.getPath(), image);
			} else {
				image.setImageBitmap(bitmap);
			}
		} else if(msgId != null) {
			downloadImage(msgId);
		}else {
			image.setImageResource(default_res);
		}

		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * download image
	 * 
	 */
	@SuppressLint("NewApi")
	private void downloadImage(final String msgId) {
        EMLog.e(TAG, "download with messageId: " + msgId);
		String str1 = getResources().getString(R.string.Download_the_pictures);
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage(str1);
		pd.show();
		File temp = new File(localFilePath);
		final String tempPath = temp.getParent() + "/temp_" + temp.getName();
		final EMCallBack callback = new EMCallBack() {
			public void onSuccess() {
			    EMLog.e(TAG, "onSuccess" );
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
                        new File(tempPath).renameTo(new File(localFilePath));

                        DisplayMetrics metrics = new DisplayMetrics();
						getWindowManager().getDefaultDisplay().getMetrics(metrics);
						int screenWidth = metrics.widthPixels;
						int screenHeight = metrics.heightPixels;

						bitmap = ImageUtils.decodeScaleImage(localFilePath, screenWidth, screenHeight);
						if (bitmap == null) {
							image.setImageResource(default_res);
						} else {
							image.setImageBitmap(bitmap);
							EaseImageCache.getInstance().put(localFilePath, bitmap);
							isDownloaded = true;
						}
						if (isFinishing() || isDestroyed()) {
						    return;
						}
						if (pd != null) {
							pd.dismiss();
						}
					}
				});
			}

			public void onError(int error, String msg) {
				EMLog.e(TAG, "offline file transfer error:" + msg);
				File file = new File(tempPath);
				if (file.exists()&&file.isFile()) {
					file.delete();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (EaseShowBigImageActivity.this.isFinishing() || EaseShowBigImageActivity.this.isDestroyed()) {
						    return;
						}
                        image.setImageResource(default_res);
                        pd.dismiss();
					}
				});
			}

			public void onProgress(final int progress, String status) {
				EMLog.d(TAG, "Progress: " + progress);
				final String str2 = getResources().getString(R.string.Download_the_pictures_new);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
                        if (EaseShowBigImageActivity.this.isFinishing() || EaseShowBigImageActivity.this.isDestroyed()) {
                            return;
                        }
						pd.setMessage(str2 + progress + "%");
					}
				});
			}
		};
		
		EMMessage msg = EMClient.getInstance().chatManager().getMessage(msgId);
		msg.setMessageStatusCallback(callback);

		EMLog.e(TAG, "downloadAttachement");
		EMClient.getInstance().chatManager().downloadAttachment(msg);
	}

	@Override
	public void onBackPressed() {
		if (isDownloaded)
			setResult(RESULT_OK);
		finish();
	}

	@Override
	public void onPhotoTap(View view, float v, float v1) {
		full(!full);
	}

	private void full(boolean enable) {
		if (enable) {
			full = true;
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			getWindow().setAttributes(lp);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			setAnimator(0);
		} else {
			full = false;
			WindowManager.LayoutParams attr = getWindow().getAttributes();
			attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().setAttributes(attr);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			setAnimator(1);
		}
	}

	private void setAnimator(final int type) {
		ObjectAnimator animator = null;
		int distance = findViewById(R.id.toolbar).getHeight() + ScreenUtils.getStatusHeight(context);
		if (type == 0) {
			animator = ObjectAnimator.ofFloat(findViewById(R.id.toolbar), "translationY", -distance);
			animator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					getSupportActionBar().hide();
				}
			});
		} else {
			animator = ObjectAnimator.ofFloat(findViewById(R.id.toolbar), "translationY", -distance, 0);
			animator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					super.onAnimationStart(animation);
					getSupportActionBar().show();
				}
			});
		}
		animator.setDuration(200);
		animator.setInterpolator(new LinearInterpolator());
		animator.start();
	}
}
