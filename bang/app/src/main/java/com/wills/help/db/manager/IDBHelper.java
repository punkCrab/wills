package com.wills.help.db.manager;

import java.util.List;

import rx.Observable;

/**
 * com.wills.help.db.manager
 * Created by lizhaoyong
 * 2016/11/8.
 */

public interface IDBHelper<T> {
    Observable<T> insertData(T t);//添加一条
    Observable<Iterable<T>> insertData(List<T> list);//添加list
    Observable<T> saveData(T t);//添加一条，如果存在覆盖
    Observable<List<T>> queryAll();//查询所以数据
    Observable<Void> deleteData(T t);//删除一条
    Observable<Void> deleteAll();//删除全部
    Observable<T> updateData(T t);//更新
}
