package com.wills.help.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wills.help.db.dao.DaoMaster;

/**
 * com.wills.help.db.manager
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class DBHelper extends DaoMaster.OpenHelper{

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
