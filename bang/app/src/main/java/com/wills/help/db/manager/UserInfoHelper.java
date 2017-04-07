package com.wills.help.db.manager;

import com.wills.help.db.bean.UserInfo;
import com.wills.help.db.dao.UserInfoDao;

import java.util.List;

import rx.Observable;

/**
 * Created by Wills on 2017/1/14.
 */

public class UserInfoHelper implements IDBHelper<UserInfo>{

    private static UserInfoHelper userInfoHelper;
    private UserInfoDao userInfoDao;

    public UserInfoHelper() {
        userInfoDao = DBManager.getInstance().getDaoSession().getUserInfoDao();
    }

    public static UserInfoHelper getInstance(){
        if (userInfoHelper == null){
            userInfoHelper = new UserInfoHelper();
        }
        return userInfoHelper;
    }

    @Override
    public Observable<UserInfo> insertData(UserInfo userInfo) {
        return userInfoDao.rx().insertOrReplace(userInfo);
    }

    @Override
    public Observable<Iterable<UserInfo>> insertData(List<UserInfo> list) {
        return userInfoDao.rx().insertOrReplaceInTx(list);
    }

    @Override
    public Observable<UserInfo> saveData(UserInfo userInfo) {
        return userInfoDao.rx().save(userInfo);
    }

    @Override
    public Observable<List<UserInfo>> queryAll() {
        return userInfoDao.queryBuilder().rx().list();
    }

    @Override
    public Observable<Void> deleteData(UserInfo userInfo) {
        return userInfoDao.rx().delete(userInfo);
    }

    @Override
    public Observable<Void> deleteAll() {
        return userInfoDao.rx().deleteAll();
    }

    @Override
    public Observable<UserInfo> updateData(UserInfo userInfo) {
        return userInfoDao.rx().update(userInfo);
    }

    public Observable<UserInfo> queryByUserId(String userId){
        return userInfoDao.queryBuilder().where(UserInfoDao.Properties.Userid.eq(userId)).rx().unique();
    }

    public UserInfo queryById(String userId){
        return userInfoDao.queryBuilder().where(UserInfoDao.Properties.Userid.eq(userId)).unique();
    }

}
