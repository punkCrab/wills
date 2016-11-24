package com.wills.help.photo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.wills.help.photo.model.AlbumModel;
import com.wills.help.photo.widget.AlbumItem;

import java.util.ArrayList;


public class AlbumAdapter extends MBaseAdapter<AlbumModel> {

	public AlbumAdapter(Context context, ArrayList<AlbumModel> models) {
		super(context, models);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AlbumItem albumItem = null;
		if (convertView == null) {
			albumItem = new AlbumItem(context);
			convertView = albumItem;
		} else
			albumItem = (AlbumItem) convertView;
		albumItem.update(models.get(position));
		return convertView;
	}

}
