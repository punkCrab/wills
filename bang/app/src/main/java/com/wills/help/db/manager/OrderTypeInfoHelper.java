package com.wills.help.db.manager;

import com.wills.help.db.bean.OrderTypeInfo;
import com.wills.help.db.dao.OrderTypeInfoDao;

import java.util.List;

import rx.Observable;

/**
 * com.wills.help.db.manager
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class OrderTypeInfoHelper implements IDBHelper<OrderTypeInfo>{

    private static OrderTypeInfoHelper orderTypeInfoHelper;
    private OrderTypeInfoDao orderTypeInfoDao;

    public OrderTypeInfoHelper() {
        orderTypeInfoDao = DBManager.getInstance().getDaoSession().getOrderTypeInfoDao();
    }

    public static OrderTypeInfoHelper getInstance(){
        if (orderTypeInfoHelper == null){
            orderTypeInfoHelper = new OrderTypeInfoHelper();
        }
        return orderTypeInfoHelper;
    }

    @Override
    public Observable<OrderTypeInfo> insertData(OrderTypeInfo orderTypeInfo) {
        return orderTypeInfoDao.rx().insertOrReplace(orderTypeInfo);
    }

    @Override
    public Observable<Iterable<OrderTypeInfo>> insertData(List<OrderTypeInfo> list) {
        return orderTypeInfoDao.rx().insertOrReplaceInTx(list);
    }

    @Override
    public Observable<OrderTypeInfo> saveData(OrderTypeInfo orderTypeInfo) {
        return orderTypeInfoDao.rx().save(orderTypeInfo);
    }

    @Override
    public Observable<List<OrderTypeInfo>> queryAll() {
        return orderTypeInfoDao.queryBuilder().where(OrderTypeInfoDao.Properties.Showing.eq(1)).rx().list();
    }

    @Override
    public Observable<Void> deleteData(OrderTypeInfo orderTypeInfo) {
        return orderTypeInfoDao.rx().delete(orderTypeInfo);
    }

    @Override
    public Observable<Void> deleteAll() {
        return orderTypeInfoDao.rx().deleteAll();
    }

    @Override
    public Observable<OrderTypeInfo> updateData(OrderTypeInfo orderTypeInfo) {
        return orderTypeInfoDao.rx().update(orderTypeInfo);
    }

    public Observable<OrderTypeInfo> queryById(String id){
        return orderTypeInfoDao.queryBuilder().where(OrderTypeInfoDao.Properties.Typeid.eq(id)).rx().unique();
    }

}
