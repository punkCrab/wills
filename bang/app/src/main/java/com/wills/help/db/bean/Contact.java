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
    private String username;
    private String nickname;
    private String sex;
    private String avatar;
    @Generated(hash = 829585389)
    public Contact(String username, String nickname, String sex, String avatar) {
        this.username = username;
        this.nickname = nickname;
        this.sex = sex;
        this.avatar = avatar;
    }
    @Generated(hash = 672515148)
    public Contact() {
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
