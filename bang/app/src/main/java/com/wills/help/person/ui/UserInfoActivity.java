package com.wills.help.person.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.base.BaseActivity;
import com.wills.help.db.manager.UserInfoHelper;
import com.wills.help.net.HttpManager;
import com.wills.help.person.model.Avatar;
import com.wills.help.person.presenter.UserInfoPresenterImpl;
import com.wills.help.person.view.UserInfoView;
import com.wills.help.photo.model.CameraUtils;
import com.wills.help.photo.ui.PhotoSelectorActivity;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.ToastUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2017/1/9.
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener , UserInfoView{
    RelativeLayout rl_avatar,rl_nickname,rl_phone,rl_sex,rl_school,rl_address;
    ImageView iv_avatar;
    TextView tv_nickname,tv_phone,tv_sex,tv_school,tv_address;
    String[] sex = new String[]{"女","男"};
    boolean isChanged = false;
    private UserInfoPresenterImpl userInfoPresenter;
    private int sexIndex =0;
    private Bitmap bitmap;
    private File file;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_person);
        setBaseTitle(getString(R.string.title_user_profile));
        rl_avatar = (RelativeLayout) findViewById(R.id.rl_avatar);
        rl_nickname = (RelativeLayout) findViewById(R.id.rl_nickname);
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_school = (RelativeLayout) findViewById(R.id.rl_school);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_school = (TextView) findViewById(R.id.tv_school);
        tv_address = (TextView) findViewById(R.id.tv_address);
        rl_avatar.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_school.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        initData();
    }

    private void initData(){
        userInfoPresenter = new UserInfoPresenterImpl(this);
        GlideUtils.getInstance().displayCircleImageWithSignature(context, App.getApp().getUser().getAvatar(),iv_avatar);
        tv_nickname.setText(App.getApp().getUser().getNickname());
        tv_phone.setText(App.getApp().getUser().getPhone_num());
        int sexId = Integer.parseInt(App.getApp().getUser().getSex());
        if (sexId==0){
            tv_sex.setText("");
        }else {
            tv_sex.setText(sex[sexId-1]);
        }
        tv_school.setText(App.getApp().getUser().getSchool());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_avatar:
                selectPicFromLocal();
                break;
            case R.id.rl_nickname:
                Bundle bundle1 = new Bundle();
                bundle1.putString("name", App.getApp().getUser().getNickname());
                bundle1.putInt("action",1);
                IntentUtils.startActivityForResult(UserInfoActivity.this, ChangeNameActivity.class,bundle1,201);
                break;
            case R.id.rl_phone:
                break;
            case R.id.rl_sex:
                sexDialog();
                break;
            case R.id.rl_school:
                Bundle bundle2 = new Bundle();
                bundle2.putString("name", App.getApp().getUser().getSchool());
                bundle2.putInt("action",2);
                IntentUtils.startActivityForResult(UserInfoActivity.this, ChangeNameActivity.class,bundle2,202);
                break;
            case R.id.tv_address:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.AVATAR && resultCode == RESULT_OK){
            if (data != null){
                isChanged = true;
                bitmap = data.getParcelableExtra("avatar");
                file = CameraUtils.saveImage(bitmap);
                userInfoPresenter.setAvatar(HttpManager.getPart("avatar" ,file),App.getApp().getUser().getUserid());
            }
        }else if (requestCode == 201 && resultCode == RESULT_OK){
            tv_nickname.setText(data.getStringExtra("name"));
            isChanged = true;
        }else if (requestCode == 202 && resultCode == RESULT_OK){
            tv_school.setText(data.getStringExtra("name"));
            isChanged = true;
        }
    }

    private void sexDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(sex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sexIndex = i;
                        userInfoPresenter.setUserInfo(getMap("sex",i+1+""));
                    }
                }).show();
    }

    private Map<String ,String> getMap(String key,String value){
        Map<String , String> map = new HashMap<>();
        map.put("userid", App.getApp().getUser().getUserid());
        map.put(key, value);
        return map;
    }

    @Override
    protected void backClick() {
        if (isChanged){
            setResult(RESULT_OK);
        }
        super.backClick();
    }

    @Override
    public void setUserInfo() {
        tv_sex.setText(sex[sexIndex]);
        App.getApp().getUser().setSex(sexIndex+1+"");
        UserInfoHelper.getInstance().updateData(App.getApp().getUser()).subscribe();
    }

    @Override
    public void setAvatar(Avatar.AvatarUrl avatar) {
        GlideUtils.getInstance().displayCircleImage(context,bitmap,iv_avatar);
        App.getApp().getUser().setAvatar(avatar.getAvatar());
        UserInfoHelper.getInstance().updateData(App.getApp().getUser()).subscribe();
        file.delete();
    }

    protected void selectPicFromLocal() {
        PermissionGen.with(this)
                .addRequestCode(120)
                .permissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .request();
    }
    @PermissionSuccess(requestCode = 120)
    public void picSuccess(){
        Bundle bundle = new Bundle();
        bundle.putInt("action", AppConfig.AVATAR);
        IntentUtils.startActivityForResult(UserInfoActivity.this, PhotoSelectorActivity.class,bundle,AppConfig.AVATAR);
    }
    @PermissionFail(requestCode = 120)
    public void picFail(){
        ToastUtils.toast("permission is not granted");
    }
}
