package com.wills.help.photo.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wills.help.R;
import com.wills.help.photo.model.PhotoModel;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.GlideUtils;

import java.util.Random;

public class PhotoItem extends LinearLayout implements OnCheckedChangeListener, OnClickListener {

	private ImageView ivPhoto;
	private CheckBox cbPhoto;
	private onPhotoItemCheckedListener listener;
	private PhotoModel photo;
	private boolean isCheckAll;
	private onItemClickListener l;
	private int position;
	private Context context;
	private int action;
	private PhotoItem(Context context) {
		super(context);
	}

	public PhotoItem(Context context,int action, onPhotoItemCheckedListener listener) {
		this(context);
		this.context = context;
		this.action = action;
		LayoutInflater.from(context).inflate(R.layout.photo_item, this, true);
		this.listener = listener;

		setOnClickListener(this);
		ivPhoto = (ImageView) findViewById(R.id.iv_photo_lpsi);
		cbPhoto = (CheckBox) findViewById(R.id.cb_photo_lpsi);
		if (action == AppConfig.AVATAR){
			cbPhoto.setVisibility(View.INVISIBLE);
		}else if (action == AppConfig.PHOTO){
			cbPhoto.setOnCheckedChangeListener(this);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (!isCheckAll) {
			listener.onCheckedChanged(photo, buttonView, isChecked);
		}
		if (isChecked) {
			setDrawingable();
			ivPhoto.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		} else {
			ivPhoto.clearColorFilter();
		}
		photo.setChecked(isChecked);
	}

	public void setImageDrawable(final PhotoModel photo) {
		this.photo = photo;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				GlideUtils.getInstance().displayImage(context ,"file://" + photo.getOriginalPath(), ivPhoto);
			}
		}, new Random().nextInt(10));
	}

	private void setDrawingable() {
		ivPhoto.setDrawingCacheEnabled(true);
		ivPhoto.buildDrawingCache();
	}

	@Override
	public void setSelected(boolean selected) {
		if (photo == null) {
			return;
		}
		isCheckAll = true;
		cbPhoto.setChecked(selected);
		isCheckAll = false;
	}

	public void setOnClickListener(onItemClickListener l, int position) {
		this.l = l;
		this.position = position;
	}

	@Override
	public void onClick(View v) {
		if (l != null)
			l.onItemClick(position);
	}

	public static interface onPhotoItemCheckedListener {
		public void onCheckedChanged(PhotoModel photoModel, CompoundButton buttonView, boolean isChecked);
	}

	public interface onItemClickListener {
		public void onItemClick(int position);
	}

}
