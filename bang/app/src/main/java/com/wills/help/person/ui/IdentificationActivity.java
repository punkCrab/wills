package com.wills.help.person.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.photo.model.PhotoModel;
import com.wills.help.photo.ui.PhotoSelectorActivity;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.IntentUtils;

import java.util.ArrayList;

/**
 * com.wills.help.person.ui
 * Created by lizhaoyong
 * 2016/11/17.
 */

public class IdentificationActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name, et_sno;
    private TextView tv_grade, tv_specialty;
    private ImageView iv_photo;
    private Button btn_upload, btn_submit;
    String[] grade = new String[]{"本科一年级","本科二年级","本科三年级","本科四年级","研究生一年级","研究生二年级","研究生三年级"};
    String[] specialty = new String[]{"机械工程","金融","计算机科学与技术","马克思","会计","物联网","通信","医学","环境","水利","土木","美术"};

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_identification);
        setBaseTitle(getString(R.string.identification));
        et_name = (EditText) findViewById(R.id.et_name);
        et_sno = (EditText) findViewById(R.id.et_sno);
        tv_grade = (TextView) findViewById(R.id.tv_grade);
        tv_specialty = (TextView) findViewById(R.id.tv_specialty);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tv_grade.setOnClickListener(this);
        tv_specialty.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_grade:
                showAlert(grade,tv_grade);
                break;
            case R.id.tv_specialty:
                showAlert(specialty,tv_specialty);
                break;
            case R.id.btn_upload:
                Bundle bundle = new Bundle();
                bundle.putInt("action", AppConfig.PHOTO);
                bundle.putInt("size", 1);
                IntentUtils.startActivityForResult(IdentificationActivity.this, PhotoSelectorActivity.class,bundle,AppConfig.PHOTO);
                break;
            case R.id.btn_submit:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.PHOTO&&resultCode == RESULT_OK){
            ArrayList<PhotoModel> arrayList = new ArrayList<>();
            Bundle bundle = data.getExtras();
            arrayList = (ArrayList<PhotoModel>) bundle.getSerializable("photos");
            if (arrayList!=null&&arrayList.size()>0){
                GlideUtils.getInstance().displayImage(context,arrayList.get(0).getOriginalPath(),iv_photo);
                btn_upload.setText(getString(R.string.reupload));
            }
        }
    }

    private void showAlert(final String[] strings , final TextView textView){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(strings[i]);
            }
        }).show();
    }
}
