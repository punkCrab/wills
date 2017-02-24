package com.wills.help.message.presenter;

import com.wills.help.message.ContactsView;
import com.wills.help.message.model.Contacts;
import com.wills.help.message.model.ContactsModel;
import com.wills.help.net.ApiSubscriber;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.message.presenter
 * Created by lizhaoyong
 * 2017/1/17.
 */

public class ContactsPresenterImpl implements ContactsPresenter{

    private ContactsModel contactsModel;
    private ContactsView contactsView;

    public ContactsPresenterImpl(ContactsView contactsView) {
        this.contactsView = contactsView;
        contactsModel = new ContactsModel();
    }

    @Override
    public void getContacts(Map<String, String> map) {
        contactsModel.getContacts(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Contacts>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Contacts contacts) {
                        contactsView.setContacts(contacts.getData());
                    }
                });
    }
}
