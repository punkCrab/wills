package com.wills.help.db.bean;

import com.wills.help.utils.StringUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Wills on 2017/1/14.
 */

@Entity
public class UserInfo {
    @Id
    private String userid;
    private String username;
    private String phone_num;
    private String avatar;
    private String sex;
    private String nickname;
    private String realname;
    private String school_num;
    private String classname;
    private String usertype;
    private String typename;
    private String usergroup;
    private String authid;
    private String createtime;
    private String school;
    private String aliaccount;
    private String paypwd;
    @Generated(hash = 965761020)
    public UserInfo(String userid, String username, String phone_num, String avatar,
            String sex, String nickname, String realname, String school_num,
            String classname, String usertype, String typename, String usergroup,
            String authid, String createtime, String school, String aliaccount,
            String paypwd) {
        this.userid = userid;
        this.username = username;
        this.phone_num = phone_num;
        this.avatar = avatar;
        this.sex = sex;
        this.nickname = nickname;
        this.realname = realname;
        this.school_num = school_num;
        this.classname = classname;
        this.usertype = usertype;
        this.typename = typename;
        this.usergroup = usergroup;
        this.authid = authid;
        this.createtime = createtime;
        this.school = school;
        this.aliaccount = aliaccount;
        this.paypwd = paypwd;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public String getUserid() {
        return this.userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPhone_num() {
        return this.phone_num;
    }
    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getNickname() {
        return StringUtils.isNullOrEmpty(this.nickname)?this.username:this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getRealname() {
        return this.realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getSchool_num() {
        return this.school_num;
    }
    public void setSchool_num(String school_num) {
        this.school_num = school_num;
    }
    public String getClassname() {
        return this.classname;
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public String getUsertype() {
        return this.usertype;
    }
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    public String getTypename() {
        return this.typename;
    }
    public void setTypename(String typename) {
        this.typename = typename;
    }
    public String getUsergroup() {
        return this.usergroup;
    }
    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }
    public String getAuthid() {
        return this.authid;
    }
    public void setAuthid(String authid) {
        this.authid = authid;
    }
    public String getCreatetime() {
        return this.createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getSchool() {
        return this.school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getAliaccount() {
        return this.aliaccount;
    }
    public void setAliaccount(String aliaccount) {
        this.aliaccount = aliaccount;
    }
    public String getPaypwd() {
        return this.paypwd;
    }
    public void setPaypwd(String paypwd) {
        this.paypwd = paypwd;
    }
    
}
