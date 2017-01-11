package com.wills.help.cache;

import android.content.Context;

import com.wills.help.base.App;
import com.wills.help.login.model.User;
import com.wills.help.utils.AppConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * com.wills.help.cache
 * Created by lizhaoyong
 * 2016/12/14.
 */

public class UserCache extends AbstractCache<String , User.UserInfo>{

    public UserCache(Context context) {
        super(context);

    }

    @Override
    public String getFile(String s) {
        return String.valueOf(s.hashCode());
    }

    @Override
    public void writeCache(ObjectOutputStream outputStream, User.UserInfo value) throws IOException {
        outputStream.writeInt(AppConfig.CACHE_VERSION);
//        outputStream.writeLong(System.currentTimeMillis());
//        outputStream.writeLong(AppConfig.CACHE_TIME);
        outputStream.writeObject(value);
    }

    @Override
    public User.UserInfo readCache(String s) throws IOException {
        File file = getCache(s);
        if (!file.exists()){
            return null;
        }
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        int version = inputStream.readInt();
        if (AppConfig.CACHE_VERSION!=version){
            App.getApp().exitApp();//取消登录状态
            return null;
        }
//        long writeTime = inputStream.readLong();
//        long expiredTime = inputStream.readLong();
//        if (System.currentTimeMillis() - writeTime > expiredTime){
//            App.getApp().exitApp();//取消登录状态
//            return null;
//        }
        try {
            return (User.UserInfo) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
