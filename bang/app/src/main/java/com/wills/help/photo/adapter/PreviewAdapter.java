package com.wills.help.photo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wills.help.photo.model.PhotoModel;
import com.wills.help.photo.ui.ImageFragment;

import java.util.List;

/**
 * com.wills.help.photo.adapter
 * Created by lizhaoyong
 * 2016/11/22.
 */

public class PreviewAdapter extends FragmentStatePagerAdapter{

    private List<PhotoModel> photos;

    public PreviewAdapter(FragmentManager fm, List<PhotoModel> photos) {
        super(fm);
        this.photos = photos;
    }

    @Override
    public Fragment getItem(int position) {
        String url = photos.get(position).getOriginalPath();
        ImageFragment imageFragment = ImageFragment.newInstance(url);
        return imageFragment;
    }

    @Override
    public int getCount() {
        if (photos == null) {
            return 0;
        } else {
            return photos.size();
        }
    }
}
