package com.wills.help.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * com.wills.help.db.bean
 * Created by lizhaoyong
 * 2016/11/8.
 */
@Entity
public class Contact {
    @Id
    private long id;
    private String username;
    private String truename;
    private String avatar;
    @Generated(hash = 471636615)
    public Contact(long id, String username, String truename, String avatar) {
        this.id = id;
        this.username = username;
        this.truename = truename;
        this.avatar = avatar;
    }
    @Generated(hash = 672515148)
    public Contact() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTruename() {
        return this.truename;
    }
    public void setTruename(String truename) {
        this.truename = truename;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
