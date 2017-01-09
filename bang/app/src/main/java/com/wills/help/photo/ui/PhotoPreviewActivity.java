package com.wills.help.photo.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.photo.adapter.PreviewAdapter;
import com.wills.help.photo.model.PhotoHandler;
import com.wills.help.photo.model.PhotoModel;
import com.wills.help.photo.widget.HackyViewPager;
import com.wills.help.utils.ScreenUtils;

import java.util.List;


/**
 * com.wills.help.photo.ui
 * Created by lizhaoyong
 * 2016/11/22.
 */

public class PhotoPreviewActivity extends BaseActivity implements PhotoSelectorActivity.OnLocalReccentListener , ViewPager.OnPageChangeListener{

    private List<PhotoModel> photos;
    private int current;
    PhotoHandler photoHandler;
    private HackyViewPager pager;
    RelativeLayout relativeLayout;
    TextView textView;
    CheckBox checkBox;
    Toolbar toolbar;
    PreviewAdapter adapter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setNoActionBarView(R.layout.photo_preview);
        pager = (HackyViewPager) findViewById(R.id.pager);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_choose);
        textView = (TextView) findViewById(R.id.tv_choose);
        checkBox = (CheckBox) findViewById(R.id.cb_choose);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        viewParams.setMargins(0, ScreenUtils.getStatusHeight(context),0,0);
        toolbar.setLayoutParams(viewParams);
        toolbar.setTitle("");
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
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photos.get(current).isChecked()){
                    checkBox.setChecked(false);
                    for (int i = 0;i<PhotoSelectorActivity.selected.size();i++){
                        if (PhotoSelectorActivity.selected.get(i).getOriginalPath().equals(photos.get(current).getOriginalPath())){
                            PhotoSelectorActivity.selected.remove(i);
                            break;
                        }
                    }
                    photos.get(current).setChecked(false);
                }else {
                    checkBox.setChecked(true);
                    photos.get(current).setChecked(true);
                    PhotoSelectorActivity.selected.add(photos.get(current));
                }
            }
        });
        photoHandler = new PhotoHandler(context);
        initData(getIntent().getExtras());
    }

    private void initData(Bundle extras){
        if (extras == null){
            return;
        }

        if (extras.containsKey("photos")){
            photos = (List<PhotoModel>) extras.getSerializable("photos");
            current = extras.getInt("position" , 0);
            setPhotos();
            checkBox.setChecked(true);
        }else if (extras.containsKey("album")){
            String albumName = extras.getString("album");
            current = extras.getInt("position");
            if (!TextUtils.isEmpty(albumName)&& albumName.equals(PhotoSelectorActivity.RECCENT_PHOTO)){
                photoHandler.getReccent( this);
            }else {
                photoHandler.getAlbum(albumName,this);
            }
        }
    }

    private void updateTitle(){
        getSupportActionBar().setTitle((current + 1) + "/" + photos.size());
    }

    private void setPhotos(){
        updateTitle();
        FragmentManager fragmentmanager = getSupportFragmentManager();
        adapter = new PreviewAdapter(fragmentmanager,photos);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);
        pager.setCurrentItem(current);
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
                setResult(RESULT_OK);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onPhotoLoaded(List<PhotoModel> photos) {
        this.photos = photos;
        if (PhotoSelectorActivity.selected!=null&&PhotoSelectorActivity.selected.size()>0){
            if (current == 0){
                for (PhotoModel model:PhotoSelectorActivity.selected){
                    if (model.getOriginalPath().equals(photos.get(current).getOriginalPath())){
                        checkBox.setChecked(true);
                        break;
                    }
                }
            }
        }
        setPhotos();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        current = position;
        for (PhotoModel model:PhotoSelectorActivity.selected){
            if (model.getOriginalPath().equals(photos.get(position).getOriginalPath())){
                photos.get(position).setChecked(true);
                break;
            }
        }
        if (photos.get(position).isChecked()){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }
        updateTitle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
