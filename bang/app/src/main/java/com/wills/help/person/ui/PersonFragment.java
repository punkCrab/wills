package com.wills.help.person.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.listener.AppBarStateChangeListener;
import com.wills.help.login.ui.LoginActivity;
import com.wills.help.login.ui.SettingActivity;
import com.wills.help.photo.model.PhotoModel;
import com.wills.help.photo.ui.PhotoSelectorActivity;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.IntentUtils;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class PersonFragment extends BaseFragment implements View.OnClickListener {
    Context context;
    CollapsingToolbarLayout collapsingToolbar;
    AppBarLayout appBarLayout;
    ImageView imageView;
    TextView tv_name, tv_school, tv_release_check, tv_release, tv_release_progress, tv_release_complete, tv_release_evaluation,
             tv_assist_check, tv_assist, tv_assist_progress, tv_assist_complete, tv_assist_evaluation,
             tv_identification, tv_public, tv_wallet;
    Toolbar toolbar;
    String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b" +
            "4000_4000&sec=1479201416&di=6a38bbdd6162963a2d848f4becf73fa9&src=http://www.qqzhi.com/uploadpic/2014-05-29/160458328.jpg";

    public static PersonFragment newInstance() {

        Bundle args = new Bundle();

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_person, null);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        getAppCompatActivity().setSupportActionBar(toolbar);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        imageView = (ImageView) view.findViewById(R.id.iv_avatar);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_school = (TextView) view.findViewById(R.id.tv_school);
        tv_release_check = (TextView) view.findViewById(R.id.tv_release_check);
        tv_release = (TextView) view.findViewById(R.id.tv_release);
        tv_release_progress = (TextView) view.findViewById(R.id.tv_release_progress);
        tv_release_complete = (TextView) view.findViewById(R.id.tv_release_complete);
        tv_release_evaluation = (TextView) view.findViewById(R.id.tv_release_evaluation);
        tv_assist_check = (TextView) view.findViewById(R.id.tv_assist_check);
        tv_assist = (TextView) view.findViewById(R.id.tv_assist);
        tv_assist_progress = (TextView) view.findViewById(R.id.tv_assist_progress);
        tv_assist_complete = (TextView) view.findViewById(R.id.tv_assist_complete);
        tv_assist_evaluation = (TextView) view.findViewById(R.id.tv_assist_evaluation);
        tv_identification = (TextView) view.findViewById(R.id.tv_identification);
        tv_public = (TextView) view.findViewById(R.id.tv_public);
        tv_wallet = (TextView) view.findViewById(R.id.tv_wallet);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        context = getAppCompatActivity().getApplicationContext();
        GlideUtils.getInstance().displayCircleImage(context, url, imageView);
        tv_release_check.setOnClickListener(this);
        tv_release.setOnClickListener(this);
        tv_release_progress.setOnClickListener(this);
        tv_release_complete.setOnClickListener(this);
        tv_release_evaluation.setOnClickListener(this);
        tv_assist_check.setOnClickListener(this);
        tv_assist.setOnClickListener(this);
        tv_assist_progress.setOnClickListener(this);
        tv_assist_complete.setOnClickListener(this);
        tv_assist_evaluation.setOnClickListener(this);
        tv_identification.setOnClickListener(this);
        tv_public.setOnClickListener(this);
        tv_wallet.setOnClickListener(this);
        tv_name.setText("交大小助手");
        tv_school.setText("北京交通大学");
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED){
                    //展开状态
                    toolbar.setTitle("");
                }else if (state == State.COLLAPSED){
                    //折叠
                    toolbar.setTitle(getString(R.string.tab_person));
                }else {
                    toolbar.setTitle("");
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("action", AppConfig.AVATAR);
                IntentUtils.startActivityForResult(getAppCompatActivity(), PhotoSelectorActivity.class,bundle,AppConfig.AVATAR);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK){
            if (data != null && data.getExtras() != null){
                @SuppressWarnings("unchecked")
                List<PhotoModel> photos = (List<PhotoModel>) data.getExtras().getSerializable("photos");
                if (photos == null || photos.isEmpty())
                    return;
                StringBuffer sb = new StringBuffer();
                for (PhotoModel photo : photos) {
                    sb.append(photo.getOriginalPath() + "\r\n");
                }
                tv_school.setText(sb.toString());
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getAppCompatActivity().getMenuInflater().inflate(R.menu.menu_base, menu);
        menu.getItem(0).setTitle("设置");
        menu.getItem(0).setIcon(R.drawable.setting);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_right:
                IntentUtils.startActivity(getAppCompatActivity(), SettingActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_release_check:
                startOrderView(tv_release_check.getText().toString());
                break;
            case R.id.tv_release:
                startOrderView(tv_release.getText().toString());
                break;
            case R.id.tv_release_progress:
                startOrderView(tv_release_progress.getText().toString());
                break;
            case R.id.tv_release_complete:
                startOrderView(tv_release_complete.getText().toString());
                break;
            case R.id.tv_release_evaluation:
                startOrderView(tv_release_evaluation.getText().toString());
                break;
            case R.id.tv_assist_check:
                startOrderView(tv_assist_check.getText().toString());
                break;
            case R.id.tv_assist:
                startOrderView(tv_assist.getText().toString());
                break;
            case R.id.tv_assist_progress:
                startOrderView(tv_assist_progress.getText().toString());
                break;
            case R.id.tv_assist_complete:
                startOrderView(tv_assist_complete.getText().toString());
                break;
            case R.id.tv_assist_evaluation:
                startOrderView(tv_assist_evaluation.getText().toString());
                break;
            case R.id.tv_identification:
                IntentUtils.startActivity(getAppCompatActivity(),IdentificationActivity.class);
                break;
            case R.id.tv_public:
                IntentUtils.startActivity(getAppCompatActivity(),LoginActivity.class);
                break;
            case R.id.tv_wallet:
                IntentUtils.startActivity(getAppCompatActivity(),WalletActivity.class);
                break;
            default:
                break;
        }
    }

    private void startOrderView(String title){
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        IntentUtils.startActivity(getAppCompatActivity(),OrderListActivity.class,bundle);
    }

}
