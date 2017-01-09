package com.wills.help.person.ui;

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
import com.wills.help.base.BaseActivity;
import com.wills.help.photo.ui.PhotoSelectorActivity;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.IntentUtils;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2017/1/9.
 */

public class InformationActivity extends BaseActivity implements View.OnClickListener{
    RelativeLayout rl_avatar,rl_nickname,rl_phone,rl_sex,rl_school,rl_address;
    ImageView iv_avatar;
    TextView tv_nickname,tv_phone,tv_sex,tv_school,tv_address;
    String[] sex = new String[]{"男","女"};
    boolean avatar = false;
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_avatar:
                Bundle bundle = new Bundle();
                bundle.putInt("action", AppConfig.AVATAR);
                IntentUtils.startActivityForResult(InformationActivity.this, PhotoSelectorActivity.class,bundle,AppConfig.AVATAR);
                break;
            case R.id.rl_nickname:
                Bundle bundle1 = new Bundle();
                bundle1.putString("nickname", "交大小助手");
                IntentUtils.startActivityForResult(InformationActivity.this, ChangeNameActivity.class,bundle1,201);
                break;
            case R.id.rl_phone:
                break;
            case R.id.rl_sex:
                sexDialog();
                break;
            case R.id.rl_school:
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
                avatar = true;
                Bitmap bitmap = data.getParcelableExtra("avatar");
                GlideUtils.getInstance().displayCircleImage(context,bitmap,iv_avatar);
            }
        }else if (requestCode == 201 && resultCode == RESULT_OK){
            tv_nickname.setText(data.getStringExtra("nickname"));
        }
    }

    private void sexDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(sex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tv_sex.setText(sex[i]);
                    }
                }).show();
    }

    @Override
    protected void backClick() {
        if (avatar){
            setResult(RESULT_OK);
        }
        super.backClick();
    }
}
