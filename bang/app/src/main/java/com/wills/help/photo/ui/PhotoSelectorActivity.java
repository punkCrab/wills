package com.wills.help.photo.ui;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.photo.adapter.AlbumAdapter;
import com.wills.help.photo.adapter.PhotoSelectorAdapter;
import com.wills.help.photo.model.AlbumModel;
import com.wills.help.photo.model.CameraUtils;
import com.wills.help.photo.model.PhotoHandler;
import com.wills.help.photo.model.PhotoModel;
import com.wills.help.photo.widget.PhotoItem;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.ScreenUtils;
import com.wills.help.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class PhotoSelectorActivity extends BaseActivity implements PhotoItem.onItemClickListener, PhotoItem.onPhotoItemCheckedListener,
		OnItemClickListener, OnClickListener {

	public static final String RECCENT_PHOTO = "最近照片";
	public static int MAX = 9;
	private GridView gvPhotos;
	private ListView lvAblum;
	private TextView tvAlbum, tvPreview , tv_line_ar;
	private PhotoHandler photoSelectorDomain;
	private PhotoSelectorAdapter photoAdapter;
	private AlbumAdapter albumAdapter;
	private RelativeLayout layoutAlbum , layoutBar;
	private View view;
	public static ArrayList<PhotoModel> selected;
	private int action = 0;
	private File cameraPath;

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setBaseView(R.layout.photo_selector);
		setBaseTitle(RECCENT_PHOTO);
		action = getIntent().getExtras().getInt("action" , 0);
		MAX = getIntent().getExtras().getInt("size",9);
		photoSelectorDomain = new PhotoHandler(getApplicationContext());
		selected = new ArrayList<PhotoModel>();
		gvPhotos = (GridView) findViewById(R.id.gv_photos_ar);
		lvAblum = (ListView) findViewById(R.id.lv_ablum_ar);
		tvAlbum = (TextView) findViewById(R.id.tv_album_ar);
		tvPreview = (TextView) findViewById(R.id.tv_preview_ar);
		tv_line_ar = (TextView) findViewById(R.id.tv_line_ar);
		layoutAlbum = (RelativeLayout) findViewById(R.id.layout_album_ar);
		layoutBar = (RelativeLayout) findViewById(R.id.layout_toolbar_ar);
		view = findViewById(R.id.v_ablum_ar);
		tvAlbum.setOnClickListener(this);
		tvPreview.setOnClickListener(this);
		if (action == AppConfig.AVATAR){
			tvPreview.setVisibility(View.INVISIBLE);
			tv_line_ar.setVisibility(View.INVISIBLE);
		}
		view.setOnClickListener(this);
		photoAdapter = new PhotoSelectorAdapter(getApplicationContext(), new ArrayList<PhotoModel>(),selected ,ScreenUtils.getScreenWidth(this),action, this, this, this);
		gvPhotos.setAdapter(photoAdapter);
		albumAdapter = new AlbumAdapter(getApplicationContext(), new ArrayList<AlbumModel>());
		lvAblum.setAdapter(albumAdapter);
		lvAblum.setOnItemClickListener(this);
		photoSelectorDomain.getReccent(reccentListener);
		photoSelectorDomain.updateAlbum(albumListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_base,menu);
		menu.getItem(0).setTitle(getString(R.string.ok));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.action_right:
				ok();
				break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.tv_album_ar:
				album();
				break;
			case R.id.tv_preview_ar:
				priview();
				break;
			case R.id.tv_camera_vc:
				catchPicture();
				break;
			case R.id.v_ablum_ar:
				hideAlbum();
				break;
		}
	}

	private void catchPicture() {
		RxPermissions rxPermissions = new RxPermissions(this);
		rxPermissions.request(Manifest.permission.CAMERA)
				.subscribe(new Action1<Boolean>() {
					@Override
					public void call(Boolean aBoolean) {
						if (aBoolean){
							Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							cameraPath = CameraUtils.getPath();
							intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraPath));
							intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
							IntentUtils.startActivityForResult(PhotoSelectorActivity.this, intent, AppConfig.CAMERA);
						}else {
							ToastUtils.toast("permission is not granted");
						}
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AppConfig.CAMERA && resultCode == RESULT_OK) {
			CameraUtils.insertCamera(context,cameraPath);
			PhotoModel photoModel = new PhotoModel(cameraPath.getPath());
			photoModel.setChecked(true);
			photoSelectorDomain.getReccent(reccentListener);
			if (action == AppConfig.PHOTO){
				selected.add(photoModel);
				Bundle bundle = new Bundle();
				bundle.putSerializable("photos", selected);
				IntentUtils.startActivityForResult(this, PhotoPreviewActivity.class, bundle,AppConfig.PREV);
			}else if (action == AppConfig.AVATAR){
				startPhotoZoom(photoModel.getOriginalPath());
			}
		}else if (requestCode == AppConfig.AVATAR && resultCode == RESULT_OK){
			//裁剪处理完上传
			Bitmap bm = data.getParcelableExtra("data");
			Intent intent = new Intent();
			intent.putExtra("avatar",bm);
			setResult(RESULT_OK,intent);
			finish();
		}else if (requestCode == AppConfig.PREV&&resultCode==RESULT_OK){
			ok();
		}
	}

	private void ok() {
		if (selected.isEmpty()) {
			setResult(RESULT_CANCELED);
		} else {
			Intent data = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("photos", selected);
			data.putExtras(bundle);
			setResult(RESULT_OK, data);
		}
		finish();
	}

	private void priview() {
		Bundle bundle = new Bundle();
		bundle.putSerializable("photos", selected);
		IntentUtils.startActivityForResult(this, PhotoPreviewActivity.class, bundle,AppConfig.PREV);
	}

	private void album() {
		if (layoutAlbum.getVisibility() == View.GONE) {
			popAlbum();
		} else {
			hideAlbum();
		}
	}

	private void popAlbum() {
		setAnimator(1);
	}

	private void hideAlbum() {
		setAnimator(0);
	}

	private void setAnimator(int type){
		ObjectAnimator animator = null;
		int from = (int) layoutBar.getY();
		if (type == 0){
			animator = ObjectAnimator.ofFloat(layoutAlbum, "translationY", 0 , from);
			animator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					layoutAlbum.setVisibility(View.GONE);
				}
			});
		}else {
			animator = ObjectAnimator.ofFloat(layoutAlbum, "translationY", from ,0);
			animator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					super.onAnimationStart(animation);
					layoutAlbum.setVisibility(View.VISIBLE);
				}
			});
		}
		animator.setDuration(200);
		animator.setInterpolator(new LinearInterpolator());
		animator.start();
	}

	private void reset() {
		selected.clear();
		tvPreview.setText("预览");
		tvPreview.setEnabled(false);
	}

	@Override
	public void onItemClick(int position) {
		if (action==AppConfig.AVATAR){
			startPhotoZoom(((PhotoModel)photoAdapter.getItem(position)).getOriginalPath());
		}else if (action==AppConfig.PHOTO){
			if (MAX == 1){
				selected.add((PhotoModel)photoAdapter.getItem(position));
				ok();
			}else {
				priviewAlbum(position);
			}
		}
	}

	private void priviewAlbum(int position){
		Bundle bundle = new Bundle();
		if (tvAlbum.getText().toString().equals(RECCENT_PHOTO))
			bundle.putInt("position", position - 1);
		else
			bundle.putInt("position", position);
		bundle.putString("album", tvAlbum.getText().toString());
		IntentUtils.startActivityForResult(this, PhotoPreviewActivity.class, bundle,AppConfig.PREV);
	}

	private void startPhotoZoom(String string){
		Uri uri = Uri.parse("file://" + string);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent , AppConfig.AVATAR);
	}

	@Override
	public void onCheckedChanged(PhotoModel photoModel, CompoundButton buttonView, boolean isChecked) {
		photoModel.setChecked(isChecked);
		if (isChecked) {
			selected.add(photoModel);
		} else {
			selected.remove(photoModel);
		}
		if (selected.isEmpty()) {
			tvPreview.setEnabled(false);
			tvPreview.setText("预览");
		}else {
			tvPreview.setText("预览(" + selected.size() + ")");
			tvPreview.setEnabled(true);
		}
	}

	@Override
	public void onBackPressed() {
		if (layoutAlbum.getVisibility() == View.VISIBLE) {
			hideAlbum();
		} else
			super.onBackPressed();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (selected.isEmpty()) {
			tvPreview.setEnabled(false);
			tvPreview.setText("预览");
		}else {
			tvPreview.setEnabled(true);
			tvPreview.setText("预览(" + selected.size() + ")");
		}
		photoAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		AlbumModel current = (AlbumModel) parent.getItemAtPosition(position);
		for (int i = 0; i < parent.getCount(); i++) {
			AlbumModel album = (AlbumModel) parent.getItemAtPosition(i);
			if (i == position)
				album.setCheck(true);
			else
				album.setCheck(false);
		}
		albumAdapter.notifyDataSetChanged();
		hideAlbum();
		tvAlbum.setText(current.getName());
		setBaseTitle(current.getName());

		if (current.getName().equals(RECCENT_PHOTO))
			photoSelectorDomain.getReccent(reccentListener);
		else
			photoSelectorDomain.getAlbum(current.getName(), reccentListener);
	}

	public interface OnLocalReccentListener {
		public void onPhotoLoaded(List<PhotoModel> photos);
	}

	public interface OnLocalAlbumListener {
		public void onAlbumLoaded(List<AlbumModel> albums);
	}

	private OnLocalAlbumListener albumListener = new OnLocalAlbumListener() {
		@Override
		public void onAlbumLoaded(List<AlbumModel> albums) {
			albumAdapter.update(albums);
		}
	};

	private OnLocalReccentListener reccentListener = new OnLocalReccentListener() {
		@Override
		public void onPhotoLoaded(List<PhotoModel> photos) {
			if (tvAlbum.getText().equals(RECCENT_PHOTO))
				photos.add(0, new PhotoModel());
			photoAdapter.update(photos);
			gvPhotos.smoothScrollToPosition(0);
//			reset();
		}
	};
}
