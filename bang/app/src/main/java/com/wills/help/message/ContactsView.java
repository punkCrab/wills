package com.wills.help.message;

import com.wills.help.db.bean.Contact;

import java.util.List;

/**
 * com.wills.help.message
 * Created by lizhaoyong
 * 2017/1/17.
 */

public interface ContactsView {
    void setContacts(List<Contact> contactList);
}
