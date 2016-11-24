package com.wills.help.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * com.wills.help
 * Created by lizhaoyong
 * 2016/11/7.
 */

public class App extends MultiDexApplication {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = (App) getApplicationContext();
    }

    public static Context getApp(){
        return app;
    }

}
