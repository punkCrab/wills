package com.wills.help.message.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.base.BaseActivity;
import com.wills.help.db.bean.Contact;
import com.wills.help.message.ContactsView;
import com.wills.help.message.EaseConstant;
import com.wills.help.message.presenter.ContactsPresenterImpl;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.IntentUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.wills.help.message.ui
 * Created by lizhaoyong
 * 2017/1/5.
 */

public class UserDetailsActivity extends BaseActivity implements View.OnClickListener ,ContactsView {
    private ImageView iv_avatar;
    private TextView tv_name;
    private Button btn_send;
    private String username;
    private ContactsPresenterImpl contactsPresenter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_userdetails);
        setBaseTitle(getString(R.string.user_details));
        Bundle bundle = getIntent().getExtras();
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_name = (TextView) findViewById(R.id.tv_name);
        username = bundle.getString("name");
        tv_name.setText(username);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        contactsPresenter = new ContactsPresenterImpl(this);
        contactsPresenter.getContacts(getMap());
    }

    private Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        map.put("usernames",username);
        return map;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                Bundle bundle = new Bundle();
                bundle.putString(EaseConstant.EXTRA_USER_ID,username);
                IntentUtils.startActivity(context,ChatActivity.class,bundle);
                finish();
                break;
        }
    }

    @Override
    public void setContacts(List<Contact> contactList) {
        if (contactList!=null&&contactList.size()>0){
            GlideUtils.getInstance().displayCircleImage(context,contactList.get(0).getAvatar(),iv_avatar);
            tv_name.setText(contactList.get(0).getNickname());
            Drawable drawable =null;
            if (contactList.get(0).getSex().equals("1")){
                drawable = context.getResources().getDrawable(R.drawable.sex_girl);
            }else if (contactList.get(0).getSex().equals("2")){
                drawable = context.getResources().getDrawable(R.drawable.sex_boy);
            }
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tv_name.setCompoundDrawables(drawable,null,null,null);
        }
    }
}
