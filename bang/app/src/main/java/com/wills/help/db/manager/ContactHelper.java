package com.wills.help.db.manager;

import com.wills.help.db.bean.Contact;
import com.wills.help.db.dao.ContactDao;

import java.util.List;

import rx.Observable;

/**
 * com.wills.help.db.manager
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class ContactHelper implements IDBHelper<Contact>{

    private static ContactHelper contactHelper;
    private ContactDao contactDao;

    public ContactHelper() {
        contactDao = DBManager.getInstance().getDaoSession().getContactDao();
    }

    public static ContactHelper getInstance(){
        if (contactHelper == null){
            contactHelper = new ContactHelper();
        }
        return contactHelper;
    }


    @Override
    public Observable<Contact> insertData(Contact contact) {
        return contactDao.rx().insert(contact);
    }

    @Override
    public Observable<Iterable<Contact>> insertData(List<Contact> list) {
        return contactDao.rx().insertInTx(list);
    }

    @Override
    public Observable<Contact> saveData(Contact contact) {
        return contactDao.rx().save(contact);
    }

    @Override
    public Observable<List<Contact>> queryAll() {
        return contactDao.queryBuilder().rx().list();
    }

    @Override
    public Observable<Void> deleteData(Contact contact) {
        return contactDao.rx().delete(contact);
    }

    @Override
    public Observable<Void> deleteAll() {
        return contactDao.rx().deleteAll();
    }

    @Override
    public Observable<Contact> updateData(Contact contact) {
        return contactDao.rx().update(contact);
    }
}
