package com.wills.help.base;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.wills.help.cache.UserCache;
import com.wills.help.cache.UserObserver;
import com.wills.help.login.model.User;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.SharedPreferencesUtils;

import java.util.Observable;
import java.util.Observer;

/**
 * com.wills.help
 * Created by lizhaoyong
 * 2016/11/7.
 */

public class App extends MultiDexApplication {
    private static App app;
    private static User.UserInfo user;
    private UserObserver userObserver;
    private UserCache userCache;
    private static boolean isLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();
        app = (App) getApplicationContext();
        user = getUser();
        getUserObserver().addObserver(new ObserverUser());
    }

    public static App getApp(){
        return app;
    }

    public User.UserInfo getUser() {
        if (user == null){
            String username = (String) SharedPreferencesUtils.getInstance().get(AppConfig.SP_USER,"");
            if (!TextUtils.isEmpty(username)){
                if (userCache == null){
                    userCache = new UserCache(app);
                }
                user = userCache.get(username);
            }
        }
        return user;
    }

    public void setUser(User.UserInfo user) {
        App.user = user;
        if (userCache == null){
            userCache = new UserCache(app);
        }
        userCache.put(user.getUserid(),user);
    }

    public void removeUser(){
        if (userCache == null){
            userCache = new UserCache(app);
        }
        userCache.remove(user.getUserid());
    }

    public UserObserver getUserObserver() {
        if (userObserver == null){
            userObserver = new UserObserver();
        }
        return userObserver;
    }

    public void setIsLogin(boolean isLogin){
        App.isLogin = isLogin;
    }

    public boolean getIsLogin(){
        return isLogin;
    }

    private class ObserverUser implements Observer{

        @Override
        public void update(Observable observable, Object o) {
            setUser((User.UserInfo) o);
        }
    }
}
