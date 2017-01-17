package com.wills.help.message.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.wills.help.base.App;
import com.wills.help.db.bean.Contact;
import com.wills.help.db.manager.ContactHelper;
import com.wills.help.message.domain.EaseUser;
import com.wills.help.utils.GlideUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EaseUserUtils {
    

    public static void setUserAvatar(final Context context, String username, final ImageView imageView){
        if (username.equals(App.getApp().getUser().getUsername())){
            GlideUtils.getInstance().displayImage(context,App.getApp().getUser().getAvatar(),imageView);
        }else {
            ContactHelper.getInstance().queryByUsername(username).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Contact>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Contact contact) {
                            if (contact!=null){
                                GlideUtils.getInstance().displayImage(context,contact.getAvatar(),imageView);
                            }
                        }
                    });
        }
    }

    public static void setAvatar2NickName(final Context context, String username, final ImageView imageView, final TextView textView){
        if (username.equals(App.getApp().getUser().getUsername())){
            GlideUtils.getInstance().displayImage(context,App.getApp().getUser().getAvatar(),imageView);
            textView.setText(App.getApp().getUser().getNickname());
        }else {
            ContactHelper.getInstance().queryByUsername(username).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Contact>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Contact contact) {
                            if (contact!=null){
                                GlideUtils.getInstance().displayImage(context,contact.getAvatar(),imageView);
                                textView.setText(contact.getNickname());
                            }
                        }
                    });
        }
    }

    public static EaseUser getEaseUser(String username){
        EaseUser easeUser = new EaseUser(username);
        Contact contact = ContactHelper.getInstance().queryByUser(username);
        if (contact!=null){
            easeUser.setAvatar(contact.getAvatar());
            easeUser.setNickname(contact.getNickname());
            easeUser.setNick(contact.getNickname());
        }
        return easeUser;
    }

}
