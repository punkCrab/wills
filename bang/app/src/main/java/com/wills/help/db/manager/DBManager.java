package com.wills.help.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wills.help.base.App;
import com.wills.help.db.dao.DaoMaster;
import com.wills.help.db.dao.DaoSession;

/**
 * com.wills.help.db.manager
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class DBManager {
    private static final String DATABASE_NAME = "WILLS";
    private static DBManager dbManager;
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private Context context;

    public DBManager() {
        this.context = context;
        DBHelper helper = new DBHelper(App.getApp(),DATABASE_NAME,null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DBManager getInstance(){
        if (dbManager == null){
            synchronized (DBManager.class){
                dbManager = new DBManager();
            }
        }
        return dbManager;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
